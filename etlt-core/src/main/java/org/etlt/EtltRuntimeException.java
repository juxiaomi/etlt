package org.etlt;

import java.io.IOException;
import java.sql.SQLException;

public class EtltRuntimeException extends RuntimeException{

    public EtltRuntimeException(String message){
        super(message);
    }

    public EtltRuntimeException(String message, Throwable cause){
        super(message, cause);
    }


    public EtltRuntimeException(Exception e) {
        super(e);
    }
}
