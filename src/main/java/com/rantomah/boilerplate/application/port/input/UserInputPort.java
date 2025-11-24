package com.rantomah.boilerplate.application.port.input;

import com.rantomah.boilerplate.application.domain.dto.user.OtpRequestDTO;
import com.rantomah.boilerplate.application.domain.dto.user.PasswordCreationDTO;
import com.rantomah.boilerplate.application.domain.dto.user.PasswordResetDTO;
import com.rantomah.boilerplate.application.domain.dto.user.PasswordUpdateDTO;
import com.rantomah.boilerplate.application.domain.dto.user.UserDTO;
import org.springframework.http.ResponseEntity;

public interface UserInputPort {

    ResponseEntity<UserDTO> getInfo();

    ResponseEntity<UserDTO> createPassword(PasswordCreationDTO request);

    ResponseEntity<UserDTO> changePassword(PasswordUpdateDTO request);

    ResponseEntity<UserDTO> resetPassword(PasswordResetDTO request);

    ResponseEntity<Void> requestOtp(OtpRequestDTO request);
}
