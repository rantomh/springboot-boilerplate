package com.rantomah.boilerplate.adapters.input;

import com.rantomah.boilerplate.application.domain.dto.auth.RefreshTokenRequestDTO;
import com.rantomah.boilerplate.application.domain.dto.auth.UserLoginRequestDTO;
import com.rantomah.boilerplate.application.domain.dto.auth.UserLoginResponseDTO;
import com.rantomah.boilerplate.application.domain.dto.user.UserActivationDTO;
import com.rantomah.boilerplate.application.ports.input.AuthInputPort;
import com.rantomah.boilerplate.application.usecases.AuthService;
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
    public ResponseEntity<UserLoginResponseDTO> login(
            @RequestBody @Valid UserLoginRequestDTO request) {
        return success(authService.login(request));
    }

    @PostMapping("/refresh")
    @Override
    public ResponseEntity<UserLoginResponseDTO> refresh(
            @RequestBody @Valid RefreshTokenRequestDTO request) {
        return success(authService.refresh(request));
    }

    @PostMapping("/activate")
    @Override
    public ResponseEntity<UserLoginResponseDTO> activate(
            @RequestBody @Valid UserActivationDTO request) {
        return created(authService.activate(request));
    }
}
