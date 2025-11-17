package com.rantomah.boilerplate.domain.usecases;

import com.rantomah.boilerplate.application.dto.user.ChangePasswordDTO;
import com.rantomah.boilerplate.application.dto.user.CreatePasswordDTO;
import com.rantomah.boilerplate.application.dto.user.OtpRequestDTO;
import com.rantomah.boilerplate.application.dto.user.ResetPasswordDTO;
import com.rantomah.boilerplate.application.dto.user.UserDTO;

public interface UserUC {

    UserDTO getInfo();

    UserDTO createPassword(CreatePasswordDTO request);

    UserDTO changePassword(ChangePasswordDTO request);

    UserDTO resetPassword(ResetPasswordDTO request);

    void requestOtp(OtpRequestDTO request);
}
