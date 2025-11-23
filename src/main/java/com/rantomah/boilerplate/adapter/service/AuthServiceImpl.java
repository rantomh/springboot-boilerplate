package com.rantomah.boilerplate.adapter.service;

import com.rantomah.boilerplate.adapter.helper.TokenHelper;
import com.rantomah.boilerplate.application.domain.constant.OtpUsage;
import com.rantomah.boilerplate.application.domain.dto.auth.LoginRequestDTO;
import com.rantomah.boilerplate.application.domain.dto.auth.LoginResponseDTO;
import com.rantomah.boilerplate.application.domain.dto.auth.RefreshTokenRequestDTO;
import com.rantomah.boilerplate.application.domain.dto.user.UserActivationDTO;
import com.rantomah.boilerplate.application.domain.entity.OTP;
import com.rantomah.boilerplate.application.domain.entity.RefreshToken;
import com.rantomah.boilerplate.application.domain.entity.User;
import com.rantomah.boilerplate.application.usecase.AuthService;
import com.rantomah.boilerplate.core.exception.AuthenticationException;
import com.rantomah.boilerplate.core.exception.InvalidCredentialsException;
import com.rantomah.boilerplate.core.exception.UserDisabledException;
import com.rantomah.boilerplate.core.exception.UserNotFoundException;
import com.rantomah.boilerplate.infrastructure.repository.OtpRepository;
import com.rantomah.boilerplate.infrastructure.repository.RefreshTokenRepository;
import com.rantomah.boilerplate.infrastructure.repository.user.UserRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl extends BaseService implements AuthService {

    private final UserRepository userRepository;
    private final TokenHelper tokenService;
    private final RefreshTokenRepository refreshTokenRepository;
    private final OtpRepository otpRepository;

    @Transactional
    @Override
    public LoginResponseDTO login(LoginRequestDTO request) {
        String username = request.getUsername().toLowerCase();
        Optional<User> userResult = userRepository.findByUsername(username);
        if (userResult.isEmpty()) {
            throw new UserNotFoundException();
        }
        User user = userResult.get();
        validateUser(user);
        if (user.getPassword() == null) {
            throw new UserDisabledException();
        }
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new InvalidCredentialsException();
        }
        return tokenService.tokenWithRefresh(user);
    }

    @Transactional
    @Override
    public LoginResponseDTO refresh(RefreshTokenRequestDTO request) {
        String token = request.getRefreshToken();
        RefreshToken refreshToken =
                refreshTokenRepository
                        .findByToken(token)
                        .orElseThrow(InvalidCredentialsException::new);
        if (refreshToken.isExpired()) {
            refreshTokenRepository.delete(refreshToken);
            throw new InvalidCredentialsException();
        }
        User user = refreshToken.getUser();
        validateUser(user);
        return tokenService.token(user);
    }

    @Transactional
    @Override
    public LoginResponseDTO activate(UserActivationDTO request) {
        String email = request.getEmail().trim().toLowerCase();
        OTP otp =
                otpRepository
                        .findByCodeAndCleAndUsage(
                                request.getCode(), email, OtpUsage.USER_ACTIVATION)
                        .orElseThrow(AuthenticationException::new);
        if (otp.isUsed() || otp.isExpired()) {
            throw new AuthenticationException();
        }
        User user =
                userRepository.findByUsername(otp.getCle()).orElseThrow(UserNotFoundException::new);
        user.setEnabled(true);
        otp.setUsed(true);
        userRepository.save(user);
        validateUser(user);
        return tokenService.tokenWithRefresh(user);
    }
}
