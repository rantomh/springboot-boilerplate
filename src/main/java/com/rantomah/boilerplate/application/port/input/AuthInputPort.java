package com.rantomah.boilerplate.application.port.input;

import com.rantomah.boilerplate.application.domain.dto.auth.LoginRequestDTO;
import com.rantomah.boilerplate.application.domain.dto.auth.LoginResponseDTO;
import com.rantomah.boilerplate.application.domain.dto.auth.RefreshTokenRequestDTO;
import com.rantomah.boilerplate.application.domain.dto.user.UserActivationDTO;
import org.springframework.http.ResponseEntity;

public interface AuthInputPort {

    ResponseEntity<LoginResponseDTO> login(LoginRequestDTO request);

    ResponseEntity<LoginResponseDTO> refresh(RefreshTokenRequestDTO request);

    ResponseEntity<LoginResponseDTO> activate(UserActivationDTO request);
}
