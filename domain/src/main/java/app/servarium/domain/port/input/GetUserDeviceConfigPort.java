package app.servarium.domain.port.input;

import app.servarium.domain.shared.result.DeviceConfigData;

import java.util.UUID;

public interface GetUserDeviceConfigPort {
    DeviceConfigData execute(UUID deviceId, long userId);
}