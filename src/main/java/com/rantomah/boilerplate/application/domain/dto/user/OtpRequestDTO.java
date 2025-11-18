package com.rantomah.boilerplate.application.domain.dto.user;

import com.rantomah.boilerplate.application.domain.constant.OtpUsage;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class OtpRequestDTO {

    @NotNull private OtpUsage usage;

    @NotBlank @NotNull private String key;
}
