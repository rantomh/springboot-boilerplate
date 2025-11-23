package com.rantomah.boilerplate.adapter.controller;

import com.rantomah.boilerplate.application.domain.dto.user.OtpRequestDTO;
import com.rantomah.boilerplate.application.domain.dto.user.PasswordCreationDTO;
import com.rantomah.boilerplate.application.domain.dto.user.PasswordResetDTO;
import com.rantomah.boilerplate.application.domain.dto.user.PasswordUpdateDTO;
import com.rantomah.boilerplate.application.domain.dto.user.UserDTO;
import com.rantomah.boilerplate.application.port.input.UserInputPort;
import com.rantomah.boilerplate.application.usecase.UserService;
import com.rantomah.boilerplate.core.BaseController;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class UserController extends BaseController implements UserInputPort {

    private final UserService userService;

    @GetMapping("/user/me")
    @Override
    public ResponseEntity<UserDTO> getInfo() {
        return success(userService.getInfo());
    }

    @PostMapping("/user/password")
    @Override
    public ResponseEntity<UserDTO> createPassword(@RequestBody @Valid PasswordCreationDTO request) {
        return created(userService.createPassword(request));
    }

    @PutMapping("/user/password")
    @Override
    public ResponseEntity<UserDTO> changePassword(@RequestBody @Valid PasswordUpdateDTO request) {
        return success(userService.changePassword(request));
    }

    @PutMapping("/user/password/reset")
    @Override
    public ResponseEntity<UserDTO> resetPassword(@RequestBody @Valid PasswordResetDTO request) {
        return success(userService.resetPassword(request));
    }

    @PostMapping("/user/otp/request")
    @Override
    public ResponseEntity<Void> requestOtp(@RequestBody @Valid OtpRequestDTO request) {
        userService.requestOtp(request);
        return noContent();
    }
}
