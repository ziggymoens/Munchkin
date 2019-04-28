package exceptions.database;

public class InternetException extends RuntimeException {
    public InternetException() {
        this("exception.connection");
    }

    public InternetException(String message) {
        super(message);
    }

    public InternetException(String message, Throwable cause) {
        super(message, cause);
    }

    public InternetException(Throwable cause) {
        super(cause);
    }

    public InternetException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
