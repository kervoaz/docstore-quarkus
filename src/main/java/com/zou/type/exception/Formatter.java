package com.zou.type.exception;

import javax.validation.ConstraintViolation;
import java.util.Set;

/**
 * @author HO.CKERVOAZOU
 */
public class Formatter<T> {
    public String formatValidation(Set<ConstraintViolation<T>> violations) {
        StringBuilder result = new StringBuilder("Missing mandatory input parameter:");
        result.append(System.getProperty("line.separator"));
        for (ConstraintViolation<T> violation : violations) {
            result.append(violation.getPropertyPath() + " " + violation.getMessage());
            result.append(System.getProperty("line.separator"));
        }
        return result.toString();
    }
}
