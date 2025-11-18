package com.rantomah.boilerplate.application.usecases;

import com.rantomah.boilerplate.application.domain.dto.user.ChangePasswordDTO;
import com.rantomah.boilerplate.application.domain.dto.user.CreatePasswordDTO;
import com.rantomah.boilerplate.application.domain.dto.user.OtpRequestDTO;
import com.rantomah.boilerplate.application.domain.dto.user.ResetPasswordDTO;
import com.rantomah.boilerplate.application.domain.dto.user.UserDTO;

public interface UserService {

    UserDTO getInfo();

    UserDTO createPassword(CreatePasswordDTO request);

    UserDTO changePassword(ChangePasswordDTO request);

    UserDTO resetPassword(ResetPasswordDTO request);

    void requestOtp(OtpRequestDTO request);
}
