package com.rantomah.boilerplate.adapters.in.controller;

import com.rantomah.boilerplate.application.dto.user.ChangePasswordDTO;
import com.rantomah.boilerplate.application.dto.user.CreatePasswordDTO;
import com.rantomah.boilerplate.application.dto.user.OtpRequestDTO;
import com.rantomah.boilerplate.application.dto.user.ResetPasswordDTO;
import com.rantomah.boilerplate.application.dto.user.UserDTO;
import com.rantomah.boilerplate.core.BaseController;
import com.rantomah.boilerplate.domain.usecases.UserUC;
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
public class UserController extends BaseController {

    private final UserUC userService;

    @GetMapping("/user/me")
    public ResponseEntity<UserDTO> getInfo() {
        return success(userService.getInfo());
    }

    @PostMapping("/user/password")
    public ResponseEntity<UserDTO> createPassword(@RequestBody @Valid CreatePasswordDTO request) {
        return created(userService.createPassword(request));
    }

    @PutMapping("/user/password")
    public ResponseEntity<UserDTO> changePassword(@RequestBody @Valid ChangePasswordDTO request) {
        return success(userService.changePassword(request));
    }

    @PutMapping("/user/password/reset")
    public ResponseEntity<UserDTO> resetPassword(@RequestBody @Valid ResetPasswordDTO request) {
        return success(userService.resetPassword(request));
    }

    @PostMapping("/user/otp/request")
    public ResponseEntity<Void> requestOtp(@RequestBody @Valid OtpRequestDTO request) {
        userService.requestOtp(request);
        return noContent();
    }
}
