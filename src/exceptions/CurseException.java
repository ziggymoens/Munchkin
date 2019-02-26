/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package exceptions;

/**
 *
 * @author ziggy
 */
public class CurseException extends RuntimeException{

    public CurseException() {
    }

    public CurseException(String message) {
        super(message);
    }

    public CurseException(String message, Throwable cause) {
        super(message, cause);
    }

    public CurseException(Throwable cause) {
        super(cause);
    }

    public CurseException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
    
}
