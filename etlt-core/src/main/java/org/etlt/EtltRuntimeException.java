package org.etlt;

public class EtltRuntimeException extends RuntimeException{

    public EtltRuntimeException(String message){
        super(message);
    }

    public EtltRuntimeException(String message, Throwable cause){
        super(message, cause);
    }
}
