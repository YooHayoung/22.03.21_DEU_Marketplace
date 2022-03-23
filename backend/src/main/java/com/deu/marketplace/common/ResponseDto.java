package com.deu.marketplace.common;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class ResponseDto<T> {
    private String error;
    private String message;
    private List<T> data;

    @Builder
    public ResponseDto(String error, String message, List<T> data) {
        this.error = error;
        this.message = message;
        this.data = data;
    }
}
