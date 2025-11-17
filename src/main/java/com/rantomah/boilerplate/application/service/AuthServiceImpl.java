package com.rantomah.boilerplate.application.service;

import com.rantomah.boilerplate.application.dto.auth.RefreshTokenRequestDTO;
import com.rantomah.boilerplate.application.dto.auth.UserLoginRequestDTO;
import com.rantomah.boilerplate.application.dto.auth.UserLoginResponseDTO;
import com.rantomah.boilerplate.application.dto.user.UserActivationDTO;
import com.rantomah.boilerplate.core.BaseService;
import com.rantomah.boilerplate.core.exception.AuthenticationException;
import com.rantomah.boilerplate.core.exception.InvalidCredentialsException;
import com.rantomah.boilerplate.core.exception.UserDisabledException;
import com.rantomah.boilerplate.core.exception.UserNotFoundException;
import com.rantomah.boilerplate.domain.constant.OtpUsage;
import com.rantomah.boilerplate.domain.model.OTP;
import com.rantomah.boilerplate.domain.model.RefreshToken;
import com.rantomah.boilerplate.domain.model.User;
import com.rantomah.boilerplate.domain.usecases.AuthUC;
import com.rantomah.boilerplate.core.helper.TokenHelper;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.rantomah.boilerplate.adapters.out.repository.OtpRepository;
import com.rantomah.boilerplate.adapters.out.repository.RefreshTokenRepository;
import com.rantomah.boilerplate.adapters.out.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl extends BaseService implements AuthUC {

    private final UserRepository userRepository;
    private final TokenHelper tokenService;
    private final RefreshTokenRepository refreshTokenRepository;
    private final OtpRepository otpRepository;

    @Transactional
    @Override
    public UserLoginResponseDTO login(UserLoginRequestDTO request) {
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
    public UserLoginResponseDTO refresh(RefreshTokenRequestDTO request) {
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
    public UserLoginResponseDTO activate(UserActivationDTO request) {
        String email = request.getEmail().trim().toLowerCase();
        OTP otp =
                otpRepository
                        .findByCodeAndKeyAndUsage(
                                request.getCode(), email, OtpUsage.USER_ACTIVATION)
                        .orElseThrow(AuthenticationException::new);
        if (otp.isUsed() || otp.isExpired()) {
            throw new AuthenticationException();
        }
        User user =
                userRepository.findByUsername(otp.getKey()).orElseThrow(UserNotFoundException::new);
        user.setEnabled(true);
        otp.setUsed(true);
        userRepository.save(user);
        validateUser(user);
        return tokenService.tokenWithRefresh(user);
    }
}
