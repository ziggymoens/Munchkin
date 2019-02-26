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
public class MonsterException extends RuntimeException{

    public MonsterException() {
    }

    public MonsterException(String message) {
        super(message);
    }

    public MonsterException(String message, Throwable cause) {
        super(message, cause);
    }

    public MonsterException(Throwable cause) {
        super(cause);
    }

    public MonsterException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
