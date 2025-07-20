package app.servarium.domain.port.input;

import app.servarium.domain.shared.params.UpdateUserDeviceParams;

public interface UpdateUserDevicePort {
    void execute(UpdateUserDeviceParams params);
}
