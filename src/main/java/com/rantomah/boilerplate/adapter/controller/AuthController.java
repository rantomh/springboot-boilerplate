package com.rantomah.boilerplate.adapter.controller;

import com.rantomah.boilerplate.application.domain.dto.auth.LoginRequestDTO;
import com.rantomah.boilerplate.application.domain.dto.auth.LoginResponseDTO;
import com.rantomah.boilerplate.application.domain.dto.auth.RefreshTokenRequestDTO;
import com.rantomah.boilerplate.application.domain.dto.user.UserActivationDTO;
import com.rantomah.boilerplate.application.port.input.AuthInputPort;
import com.rantomah.boilerplate.application.usecase.AuthService;
import com.rantomah.boilerplate.core.BaseController;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController extends BaseController implements AuthInputPort {

    private final AuthService authService;

    @PostMapping("/login")
    @Override
    public ResponseEntity<LoginResponseDTO> login(@RequestBody @Valid LoginRequestDTO request) {
        return success(authService.login(request));
    }

    @PostMapping("/refresh")
    @Override
    public ResponseEntity<LoginResponseDTO> refresh(
            @RequestBody @Valid RefreshTokenRequestDTO request) {
        return success(authService.refresh(request));
    }

    @PostMapping("/activate")
    @Override
    public ResponseEntity<LoginResponseDTO> activate(
            @RequestBody @Valid UserActivationDTO request) {
        return created(authService.activate(request));
    }
}
