package app.servarium.core.exception;

import java.util.UUID;

public class DeviceAlreadyLinkedToUserException extends RuntimeException {
    public DeviceAlreadyLinkedToUserException(UUID deviceId, long userId) {
        super("Device with id=%s is already linked to user with id=%s".formatted(deviceId.toString(), userId));
    }
}
