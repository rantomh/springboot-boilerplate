package com.rantomah.boilerplate.application.ports.input;

import com.rantomah.boilerplate.application.domain.dto.user.ChangePasswordDTO;
import com.rantomah.boilerplate.application.domain.dto.user.CreatePasswordDTO;
import com.rantomah.boilerplate.application.domain.dto.user.OtpRequestDTO;
import com.rantomah.boilerplate.application.domain.dto.user.ResetPasswordDTO;
import com.rantomah.boilerplate.application.domain.dto.user.UserDTO;
import org.springframework.http.ResponseEntity;

public interface UserInputPort {

    ResponseEntity<UserDTO> getInfo();

    ResponseEntity<UserDTO> createPassword(CreatePasswordDTO request);

    ResponseEntity<UserDTO> changePassword(ChangePasswordDTO request);

    ResponseEntity<UserDTO> resetPassword(ResetPasswordDTO request);

    ResponseEntity<Void> requestOtp(OtpRequestDTO request);
}
