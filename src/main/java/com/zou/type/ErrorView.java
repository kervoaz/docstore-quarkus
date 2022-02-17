package com.zou.type;

/**
 * @author HO.CKERVOAZOU
 */
public class ErrorView {
    private String message;
    private String id;

    public ErrorView(Exception exception) {
        this.message = exception.getMessage();
    }

    public ErrorView(Exception exception, EcmDocument ecmDocument) {
        this.message = exception.getMessage();
        this.id = ecmDocument.getId();
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
