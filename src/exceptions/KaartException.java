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
public class KaartException extends RuntimeException{

    public KaartException() {
    }

    public KaartException(String message) {
        super(message);
    }

    public KaartException(String message, Throwable cause) {
        super(message, cause);
    }

    public KaartException(Throwable cause) {
        super(cause);
    }

    public KaartException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
    
}
