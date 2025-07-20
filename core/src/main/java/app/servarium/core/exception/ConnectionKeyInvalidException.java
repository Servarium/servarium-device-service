package app.servarium.core.exception;

public class ConnectionKeyInvalidException extends RuntimeException {
    public ConnectionKeyInvalidException(String keyValue) {
        super("Connection key %s is invalid".formatted(keyValue));
    }
}
