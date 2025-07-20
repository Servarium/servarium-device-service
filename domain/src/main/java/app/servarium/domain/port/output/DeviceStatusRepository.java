package app.servarium.domain.port.output;

import java.util.Collection;
import java.util.Map;
import java.util.UUID;

public interface DeviceStatusRepository {
    Map<UUID, Boolean> getActiveStatusesByDeviceIds(Collection<UUID> deviceIds);
}
