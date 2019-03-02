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
public class RaceException extends RuntimeException{

    public RaceException() {
        super("Race Exception, domein.kaarten.kerkerkaarten");
    }

    public RaceException(String message) {
        super(message);
    }

    public RaceException(String message, Throwable cause) {
        super(message, cause);
    }

    public RaceException(Throwable cause) {
        super(cause);
    }

    public RaceException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
    
}
