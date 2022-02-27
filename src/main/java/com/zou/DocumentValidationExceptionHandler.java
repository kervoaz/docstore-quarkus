package com.zou;

import com.zou.type.ErrorMessage;
import com.zou.type.exception.DocumentValidationException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 * @author HO.CKERVOAZOU
 */
@Provider
public class DocumentValidationExceptionHandler implements ExceptionMapper<DocumentValidationException> {

    @Override
    public Response toResponse(DocumentValidationException e) {
        return Response.status(Response.Status.BAD_REQUEST).
                entity(new ErrorMessage(e.getMessage(), false)).build();

    }
}
