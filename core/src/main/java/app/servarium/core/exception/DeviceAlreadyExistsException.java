package app.servarium.core.exception;

import java.util.UUID;

public class DeviceAlreadyExistsException extends RuntimeException {
    public DeviceAlreadyExistsException(UUID deviceId) {
        super("Device with id=%s already exists".formatted(deviceId.toString()));
    }
}
