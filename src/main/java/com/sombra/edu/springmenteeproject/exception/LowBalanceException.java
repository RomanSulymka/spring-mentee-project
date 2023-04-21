package com.sombra.edu.springmenteeproject.exception;

public class LowBalanceException extends RuntimeException {

    public LowBalanceException(String message) {
        super(message);
    }

    public LowBalanceException(String message, Throwable cause) {
        super(message, cause);
    }
}

