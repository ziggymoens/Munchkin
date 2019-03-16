package exceptions.database;

public class KaartDatabaseException extends RuntimeException {
    public KaartDatabaseException() {
        this("KaartDatabaseException: persistentie.mappers.KaartMapperDb");
    }

    public KaartDatabaseException(String message) {
        super(message);
    }

    public KaartDatabaseException(String message, Throwable cause) {
        super(message, cause);
    }

    public KaartDatabaseException(Throwable cause) {
        super(cause);
    }

    public KaartDatabaseException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
