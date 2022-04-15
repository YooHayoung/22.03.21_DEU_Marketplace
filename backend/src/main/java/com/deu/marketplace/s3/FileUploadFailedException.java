package com.deu.marketplace.s3;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MultipartException;

@Slf4j
public class FileUploadFailedException extends Throwable {
    @ExceptionHandler(MultipartException.class)
    protected ResponseEntity<?> handleEmptyFileException(
            MultipartException e) {
        log.info("handleMaxUploadSizeExceededException", e);

        return new ResponseEntity<>("result", HttpStatus.BAD_REQUEST);
    }
}
