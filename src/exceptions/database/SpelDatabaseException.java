package exceptions.database;

public class SpelDatabaseException extends RuntimeException{
    public SpelDatabaseException() {
        super("SpelDbException, persistentie.mappers.spelMapperDb");
    }

    public SpelDatabaseException(String message) {
        super(message);
    }

    public SpelDatabaseException(String message, Throwable cause) {
        super(message, cause);
    }

    public SpelDatabaseException(Throwable cause) {
        super(cause);
    }

    public SpelDatabaseException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
