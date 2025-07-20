package app.servarium.domain.port.input;

import java.util.UUID;

public interface UnlinkDeviceFromUserPort {
    void execute(UUID deviceId, long userId);
}
