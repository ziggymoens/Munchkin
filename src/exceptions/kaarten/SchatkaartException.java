/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package exceptions.kaarten;

/**
 *
 * @author ziggy
 */
public class SchatkaartException extends RuntimeException{

    public SchatkaartException() {
    }

    public SchatkaartException(String message) {
        super(message);
    }

    public SchatkaartException(String message, Throwable cause) {
        super(message, cause);
    }

    public SchatkaartException(Throwable cause) {
        super(cause);
    }

    public SchatkaartException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
    
}