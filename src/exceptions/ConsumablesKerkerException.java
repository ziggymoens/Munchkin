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
public class ConsumablesKerkerException extends RuntimeException{

    public ConsumablesKerkerException() {
    }

    public ConsumablesKerkerException(String message) {
        super(message);
    }

    public ConsumablesKerkerException(String message, Throwable cause) {
        super(message, cause);
    }

    public ConsumablesKerkerException(Throwable cause) {
        super(cause);
    }

    public ConsumablesKerkerException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
    
}
