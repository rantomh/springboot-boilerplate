package com.rantomah.boilerplate.core;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import java.time.Instant;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiErrorDto {

    private String code;

    @JsonIgnore private int httpStatus;

    private int status;

    private String message;

    private String path;

    private final Instant timestamp = Instant.now();
}
