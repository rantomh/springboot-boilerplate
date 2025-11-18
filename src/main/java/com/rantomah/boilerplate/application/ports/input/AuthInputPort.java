package com.rantomah.boilerplate.application.ports.input;

import com.rantomah.boilerplate.application.domain.dto.auth.RefreshTokenRequestDTO;
import com.rantomah.boilerplate.application.domain.dto.auth.UserLoginRequestDTO;
import com.rantomah.boilerplate.application.domain.dto.auth.UserLoginResponseDTO;
import com.rantomah.boilerplate.application.domain.dto.user.UserActivationDTO;
import org.springframework.http.ResponseEntity;

public interface AuthInputPort {

    ResponseEntity<UserLoginResponseDTO> login(UserLoginRequestDTO request);

    ResponseEntity<UserLoginResponseDTO> refresh(RefreshTokenRequestDTO request);

    ResponseEntity<UserLoginResponseDTO> activate(UserActivationDTO request);
}
