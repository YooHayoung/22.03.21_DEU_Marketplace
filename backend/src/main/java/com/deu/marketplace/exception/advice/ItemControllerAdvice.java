package com.deu.marketplace.exception.advice;

import com.deu.marketplace.common.response.ErrorResponse;
import com.deu.marketplace.exception.MemberAndWriterNotSameException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.NoSuchElementException;

@Slf4j
@RestControllerAdvice(annotations = RestController.class)
public class ItemControllerAdvice {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler
    public ErrorResponse noSuchElementExHandle(NoSuchElementException e) {
        return new ErrorResponse("BAD", "No Such Element");
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler
    public ErrorResponse memberAndWriterNotSameExHandle(MemberAndWriterNotSameException e) {
        return new ErrorResponse("UNAUTHORIZED", "권한이 없습니다.");
    }
}
