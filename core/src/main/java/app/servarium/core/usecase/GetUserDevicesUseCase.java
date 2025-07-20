package app.servarium.core.usecase;

import app.servarium.domain.model.UserDevice;
import app.servarium.domain.model.value.SoftwareConfig;
import app.servarium.domain.port.input.GetUserDevicesPort;
import app.servarium.domain.port.output.DeviceRepository;
import app.servarium.domain.port.output.DeviceStatusRepository;
import app.servarium.domain.port.output.UserDeviceRepository;
import app.servarium.domain.shared.result.UserDeviceItemData;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class GetUserDevicesUseCase implements GetUserDevicesPort {
    private final UserDeviceRepository userDeviceRepository;
    private final DeviceRepository deviceRepository;
    private final DeviceStatusRepository deviceStatusRepository;

    @Override
    @Transactional(readOnly = true)
    public List<UserDeviceItemData> execute(long userId) {
        log.debug("Fetching devices - User: {}", userId);

        List<UserDevice> userDevices = userDeviceRepository.getAllByUserId(userId);

        if (userDevices.isEmpty()) {
            log.info("No devices found - User: {}", userId);
            return Collections.emptyList();
        }

        List<UUID> deviceIds = extractDeviceIds(userDevices);
        Map<UUID, SoftwareConfig> softwareConfigs = deviceRepository.getSoftwareConfigsByDeviceIds(deviceIds);
        Map<UUID, Boolean> activeStatuses = deviceStatusRepository.getActiveStatusesByDeviceIds(deviceIds);

        var result = userDevices.stream()
                .map(e -> {
                    UUID deviceId = e.getDeviceId();
                    return UserDeviceItemData.from(e, softwareConfigs.get(deviceId), activeStatuses.get(deviceId));
                })
                .toList();

        log.info("Found {} devices - User: {}", userId, result.size());
        return result;
    }

    private List<UUID> extractDeviceIds(List<UserDevice> userDevices) {
        return userDevices.stream()
                .map(UserDevice::getDeviceId)
                .toList();
    }
}