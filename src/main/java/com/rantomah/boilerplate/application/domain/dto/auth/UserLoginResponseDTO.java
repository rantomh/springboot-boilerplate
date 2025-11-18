package com.rantomah.boilerplate.application.domain.dto.auth;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.rantomah.boilerplate.core.config.oauth2.RealmScope;
import com.rantomah.boilerplate.core.config.oauth2.TokenType;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserLoginResponseDTO {

    @JsonProperty("access_token")
    private String accessToken;

    @JsonProperty("token_type")
    private TokenType tokenType;

    @JsonProperty("refresh_token")
    private String refreshToken;

    private RealmScope scope;

    @JsonProperty("expires_in")
    private Long expiresIn;

    @Builder.Default
    @JsonProperty("not_before_policy")
    private Long notBeforePolicy = 0L;
}
