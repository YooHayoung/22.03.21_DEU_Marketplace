package com.deu.marketplace.web.oauth.controller;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
public class GetTokenDto {
    private String accessToken;
    private String refreshToken;
    private String tokenType;
    private int expiresIn;
    private String error;
    private String errorDescription;

    @JsonCreator
    public GetTokenDto(@JsonProperty("access_token") String accessToken,
                       @JsonProperty("refresh_token") String refreshToken,
                       @JsonProperty("token_type") String tokenType,
                       @JsonProperty("expires_in") int expiresIn,
                       @JsonProperty("error") String error,
                       @JsonProperty("error_description") String errorDescription) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.tokenType = tokenType;
        this.expiresIn = expiresIn;
        this.error = error;
        this.errorDescription = errorDescription;
    }
}
