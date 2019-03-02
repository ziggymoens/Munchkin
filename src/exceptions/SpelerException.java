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
public class SpelerException extends RuntimeException{

    public SpelerException() {
        super("Algemene Speler Exception");
    }

    public SpelerException(String message) {
        super(message);
    }

    public SpelerException(String message, Throwable cause) {
        super(message, cause);
    }

    public SpelerException(Throwable cause) {
        super(cause);
    }

    public SpelerException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
    
}
