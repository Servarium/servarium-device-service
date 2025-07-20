package app.servarium.core.exception;

import java.util.UUID;

public class DeviceNotLinkedToUserException extends RuntimeException {
    public DeviceNotLinkedToUserException(UUID deviceId, long userId) {
        super("Device with id=%s is not linked to user with id=%s".formatted(deviceId.toString(), userId));
    }
}
