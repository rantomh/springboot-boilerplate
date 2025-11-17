package com.rantomah.boilerplate.domain.usecases;

import com.rantomah.boilerplate.application.dto.auth.RefreshTokenRequestDTO;
import com.rantomah.boilerplate.application.dto.auth.UserLoginRequestDTO;
import com.rantomah.boilerplate.application.dto.auth.UserLoginResponseDTO;
import com.rantomah.boilerplate.application.dto.user.UserActivationDTO;

public interface AuthUC {

    UserLoginResponseDTO login(UserLoginRequestDTO request);

    UserLoginResponseDTO refresh(RefreshTokenRequestDTO request);

    UserLoginResponseDTO activate(UserActivationDTO request);
}
