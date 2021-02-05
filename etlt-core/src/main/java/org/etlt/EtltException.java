package org.etlt;

public class EtltException extends RuntimeException{

    public EtltException(String message){
        super(message);
    }

    public EtltException(String message, Throwable cause){
        super(message, cause);
    }
}
