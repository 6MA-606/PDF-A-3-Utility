package com.example.exceptions;

public class XmlConversionException extends Exception {

    public XmlConversionException(String message) {
        super(message);
    }

    public XmlConversionException(String message, Throwable cause) {
        super(message, cause);
    }

    public XmlConversionException(Throwable cause) {
        super(cause);
    }
    
}
