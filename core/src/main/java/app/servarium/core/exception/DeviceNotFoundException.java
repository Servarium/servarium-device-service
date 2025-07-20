package app.servarium.core.exception;

import java.util.UUID;

public class DeviceNotFoundException extends RuntimeException {
    public DeviceNotFoundException(UUID  deviceId) {
        super("Device with id=%s not found".formatted(deviceId.toString()));
    }
}
