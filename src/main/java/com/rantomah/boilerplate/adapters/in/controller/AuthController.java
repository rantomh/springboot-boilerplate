package com.rantomah.boilerplate.adapters.in.controller;

import com.rantomah.boilerplate.application.dto.auth.RefreshTokenRequestDTO;
import com.rantomah.boilerplate.application.dto.auth.UserLoginRequestDTO;
import com.rantomah.boilerplate.application.dto.auth.UserLoginResponseDTO;
import com.rantomah.boilerplate.application.dto.user.UserActivationDTO;
import com.rantomah.boilerplate.core.BaseController;
import com.rantomah.boilerplate.domain.usecases.AuthUC;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController extends BaseController {

    private final AuthUC authService;

    @PostMapping("/login")
    public ResponseEntity<UserLoginResponseDTO> login(
            @RequestBody @Valid UserLoginRequestDTO request) {
        return success(authService.login(request));
    }

    @PostMapping("/refresh")
    public ResponseEntity<UserLoginResponseDTO> refresh(
            @RequestBody @Valid RefreshTokenRequestDTO request) {
        return success(authService.refresh(request));
    }

    @PostMapping("/activate")
    public ResponseEntity<UserLoginResponseDTO> activate(
            @RequestBody @Valid UserActivationDTO request) {
        return created(authService.activate(request));
    }
}
