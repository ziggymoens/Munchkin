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
public class BadStuffException extends RuntimeException{

    public BadStuffException() {
    }

    public BadStuffException(String message) {
        super(message);
    }

    public BadStuffException(String message, Throwable cause) {
        super(message, cause);
    }

    public BadStuffException(Throwable cause) {
        super(cause);
    }

    public BadStuffException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }    
}
