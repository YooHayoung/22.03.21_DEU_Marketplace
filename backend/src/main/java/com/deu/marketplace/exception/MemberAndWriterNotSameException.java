package com.deu.marketplace.exception;

public class MemberAndWriterNotSameException extends RuntimeException {

    public MemberAndWriterNotSameException() {
        super();
    }

    public MemberAndWriterNotSameException(String message) {
        super(message);
    }

    public MemberAndWriterNotSameException(String message, Throwable cause) {
        super(message, cause);
    }

    public MemberAndWriterNotSameException(Throwable cause) {
        super(cause);
    }
}
