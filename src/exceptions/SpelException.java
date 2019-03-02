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
public class SpelException extends RuntimeException{

    public SpelException() {
        super("Algemene Spel Exception");
    }

    public SpelException(String message) {
        super(message);
    }

    public SpelException(String message, Throwable cause) {
        super(message, cause);
    }

    public SpelException(Throwable cause) {
        super(cause);
    }

    public SpelException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
    
}
