package org.etlt;

public class EtltException extends Exception{

    public EtltException(String message){
        super(message);
    }

    public EtltException(String message, Throwable cause){
        super(message, cause);
    }
}
