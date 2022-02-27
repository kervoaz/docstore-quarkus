package com.zou.type.exception;

/**
 * @author HO.CKERVOAZOU
 */
public class UserInputValidationException extends RuntimeException {

    public UserInputValidationException(String message) {
        super("Missing mandatory input parameter:" + System.getProperty("line.separator") + message);
    }
}
