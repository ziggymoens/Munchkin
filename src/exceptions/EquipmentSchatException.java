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
public class EquipmentSchatException extends RuntimeException{

    public EquipmentSchatException() {
    }

    public EquipmentSchatException(String message) {
        super(message);
    }

    public EquipmentSchatException(String message, Throwable cause) {
        super(message, cause);
    }

    public EquipmentSchatException(Throwable cause) {
        super(cause);
    }

    public EquipmentSchatException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
    
}
