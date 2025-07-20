package app.servarium.domain.port.output;

import app.servarium.domain.model.Device;
import app.servarium.domain.model.value.HardwareConfig;
import app.servarium.domain.model.value.SoftwareConfig;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public interface DeviceRepository {
    boolean existsById(UUID deviceId);

    void save(Device device);

    Optional<HardwareConfig> getHardwareConfigByDeviceId(UUID deviceId);

    Map<UUID, SoftwareConfig> getSoftwareConfigsByDeviceIds(List<UUID> deviceIds);

    Optional<Device> getById(UUID deviceId);
}
