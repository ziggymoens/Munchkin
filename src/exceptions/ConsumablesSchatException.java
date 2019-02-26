/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package exceptions;

/**
 *
 * @author kilian
 */
public class ConsumablesSchatException extends RuntimeException {

    public ConsumablesSchatException() {
    }
    
    public ConsumablesSchatException(String message){
        super(message);
    }
    
    public ConsumablesSchatException(String message, Throwable cause){
        super(message, cause);
    }
    
    public ConsumablesSchatException(Throwable cause){
        super(cause);
    }
    
    public ConsumablesSchatException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace){
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
