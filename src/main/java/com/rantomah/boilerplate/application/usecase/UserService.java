package com.rantomah.boilerplate.application.usecase;

import com.rantomah.boilerplate.application.domain.dto.user.OtpRequestDTO;
import com.rantomah.boilerplate.application.domain.dto.user.PasswordCreationDTO;
import com.rantomah.boilerplate.application.domain.dto.user.PasswordResetDTO;
import com.rantomah.boilerplate.application.domain.dto.user.PasswordUpdateDTO;
import com.rantomah.boilerplate.application.domain.dto.user.UserDTO;

public interface UserService {

    UserDTO getInfo();

    UserDTO createPassword(PasswordCreationDTO request);

    UserDTO changePassword(PasswordUpdateDTO request);

    UserDTO resetPassword(PasswordResetDTO request);

    void requestOtp(OtpRequestDTO request);
}
