package com.rantomah.boilerplate.application.usecase;

import com.rantomah.boilerplate.application.domain.dto.auth.LoginRequestDTO;
import com.rantomah.boilerplate.application.domain.dto.auth.LoginResponseDTO;
import com.rantomah.boilerplate.application.domain.dto.auth.RefreshTokenRequestDTO;
import com.rantomah.boilerplate.application.domain.dto.user.UserActivationDTO;

public interface AuthService {

    LoginResponseDTO login(LoginRequestDTO request);

    LoginResponseDTO refresh(RefreshTokenRequestDTO request);

    LoginResponseDTO activate(UserActivationDTO request);
}
