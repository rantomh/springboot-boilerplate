package com.rantomah.boilerplate.infrastructure.service;

import com.rantomah.boilerplate.application.domain.constant.OtpUsage;
import com.rantomah.boilerplate.application.domain.dto.user.ChangePasswordDTO;
import com.rantomah.boilerplate.application.domain.dto.user.CreatePasswordDTO;
import com.rantomah.boilerplate.application.domain.dto.user.OtpRequestDTO;
import com.rantomah.boilerplate.application.domain.dto.user.ResetPasswordDTO;
import com.rantomah.boilerplate.application.domain.dto.user.UserDTO;
import com.rantomah.boilerplate.application.domain.entities.OTP;
import com.rantomah.boilerplate.application.domain.entities.User;
import com.rantomah.boilerplate.application.usecases.UserService;
import com.rantomah.boilerplate.core.exception.GenericException;
import com.rantomah.boilerplate.core.exception.InvalidCredentialsException;
import com.rantomah.boilerplate.core.exception.UserNotFoundException;
import com.rantomah.boilerplate.infrastructure.listener.event.NotificationEvent;
import com.rantomah.boilerplate.infrastructure.mapper.user.UserMapper;
import com.rantomah.boilerplate.infrastructure.repository.UserRepository;
import java.util.Map;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl extends BaseService implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    private static final String EMAIL_KEY = "email";
    private static final String CODE_KEY = "code";

    @Override
    @Transactional
    public UserDTO createPassword(CreatePasswordDTO request) {
        UUID userId = getCurrentUserId();
        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        validateUser(user);
        if (user.getPassword() != null) {
            throw new GenericException("A password is already set for this user");
        }
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        userRepository.save(user);
        return userMapper.toDto(user);
    }

    @Override
    @Transactional(readOnly = true)
    public UserDTO getInfo() {
        UUID userId = getCurrentUserId();
        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        validateUser(user);
        return userMapper.toDto(user);
    }

    @Override
    @Transactional
    public UserDTO changePassword(ChangePasswordDTO request) {
        UUID userId = getCurrentUserId();
        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        validateUser(user);
        if (!passwordEncoder.matches(request.getOldPassword(), user.getPassword())) {
            throw new InvalidCredentialsException();
        }
        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        userRepository.save(user);
        return userMapper.toDto(user);
    }

    @Override
    public UserDTO resetPassword(ResetPasswordDTO request) {
        String username = request.getKey().toLowerCase();
        User user = userRepository.findByUsername(username).orElseThrow(UserNotFoundException::new);
        validateUser(user);
        otpHelper.processByCodeAndKeyAndUsage(
                request.getOtp(), user.getUsername(), OtpUsage.PASSWORD_RESET);
        if (user.getPassword() == null) {
            throw new GenericException("No password is set for this user");
        }
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        return userMapper.toDto(userRepository.save(user));
    }

    @Override
    @Transactional
    public void requestOtp(OtpRequestDTO request) {
        String username = request.getKey().toLowerCase();
        User user = userRepository.findByUsername(username).orElseThrow(UserNotFoundException::new);
        validateUser(user);
        OTP otp = otpHelper.createByKeyAndUsage(username, request.getUsage());
        publisher.publishEvent(
                NotificationEvent.builder()
                        .to(username)
                        .type(EMAIL_KEY)
                        .template("auth/otp-request")
                        .data(Map.of(EMAIL_KEY, username, CODE_KEY, otp.getCode()))
                        .build());
    }
}
