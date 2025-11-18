package com.rantomah.boilerplate.application.usecases;

import com.rantomah.boilerplate.application.domain.dto.auth.RefreshTokenRequestDTO;
import com.rantomah.boilerplate.application.domain.dto.auth.UserLoginRequestDTO;
import com.rantomah.boilerplate.application.domain.dto.auth.UserLoginResponseDTO;
import com.rantomah.boilerplate.application.domain.dto.user.UserActivationDTO;

public interface AuthService {

    UserLoginResponseDTO login(UserLoginRequestDTO request);

    UserLoginResponseDTO refresh(RefreshTokenRequestDTO request);

    UserLoginResponseDTO activate(UserActivationDTO request);
}
