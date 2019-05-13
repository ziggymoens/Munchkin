/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package exceptions.kaarten.kerkerkaarten;

/**
 * @author ziggy
 */
@SuppressWarnings("unused")

public class ConsumablesKerkerException extends RuntimeException {
    /**
     * Algemene Exception constructor
     */
    public ConsumablesKerkerException() {
        super("Consumables Kerker Exception, domein.kaarten.kerkerkaarten");
    }

    /**
     * Constructor voor exception met extra boodschap
     *
     * @param message de te vertalen boodschap van de error
     */
    public ConsumablesKerkerException(String message) {
        super(message);
    }

    /**
     * Constructor voor Exception met boodschap en cause
     *
     * @param message de te vertalen boodschap
     * @param cause   de oorzaak van de exception
     */
    public ConsumablesKerkerException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructor voor Exception met cause
     *
     * @param cause de oorzaak van de speler
     */
    public ConsumablesKerkerException(Throwable cause) {
        super(cause);
    }

    /**
     * Constructor voor Exception met message, cause, enableSuppression & writable stacktrace
     *
     * @param message            boodschap van de message
     * @param cause              de oorzaak van de exception
     * @param enableSuppression  true of false
     * @param writableStackTrace true or false
     */
    public ConsumablesKerkerException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
