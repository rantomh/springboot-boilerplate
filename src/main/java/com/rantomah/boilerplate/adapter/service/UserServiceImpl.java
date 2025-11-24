package com.rantomah.boilerplate.adapter.service;

import com.rantomah.boilerplate.adapter.entity.User;
import com.rantomah.boilerplate.application.domain.constant.OtpUsage;
import com.rantomah.boilerplate.application.domain.dto.user.OtpRequestDTO;
import com.rantomah.boilerplate.application.domain.dto.user.PasswordCreationDTO;
import com.rantomah.boilerplate.application.domain.dto.user.PasswordResetDTO;
import com.rantomah.boilerplate.application.domain.dto.user.PasswordUpdateDTO;
import com.rantomah.boilerplate.application.domain.dto.user.UserDTO;
import com.rantomah.boilerplate.application.domain.model.OTP;
import com.rantomah.boilerplate.application.usecase.UserService;
import com.rantomah.boilerplate.core.exception.GenericException;
import com.rantomah.boilerplate.core.exception.InvalidCredentialsException;
import com.rantomah.boilerplate.core.exception.UserNotFoundException;
import com.rantomah.boilerplate.infrastructure.broker.notification.NotificationEvent;
import com.rantomah.boilerplate.infrastructure.repository.user.UserRepository;
import com.rantomah.boilerplate.support.mapper.user.UserMapper;
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
    public UserDTO createPassword(PasswordCreationDTO request) {
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
    public UserDTO changePassword(PasswordUpdateDTO request) {
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
    public UserDTO resetPassword(PasswordResetDTO request) {
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
