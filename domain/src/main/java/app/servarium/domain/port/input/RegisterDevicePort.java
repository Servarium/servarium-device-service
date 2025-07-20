package app.servarium.domain.port.input;

import app.servarium.domain.shared.params.DevicePayload;

public interface RegisterDevicePort {
    void execute(DevicePayload params);
}
