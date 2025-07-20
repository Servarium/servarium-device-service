package app.servarium.core.usecase;

import app.servarium.core.exception.DeviceNotFoundException;
import app.servarium.core.exception.DeviceNotLinkedToUserException;
import app.servarium.domain.model.Device;
import app.servarium.domain.port.input.GetUserDeviceConfigPort;
import app.servarium.domain.port.output.DeviceRepository;
import app.servarium.domain.port.output.UserDeviceRepository;
import app.servarium.domain.shared.result.DeviceConfigData;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class GetUserDeviceConfigUseCase implements GetUserDeviceConfigPort {
    private final DeviceRepository deviceRepository;
    private final UserDeviceRepository userDeviceRepository;

    @Override
    @Transactional(readOnly = true)
    public DeviceConfigData execute(UUID deviceId, long userId) {
        log.debug("Getting hardware config - Device: {}, User: {}", deviceId, userId);

        assertDeviceLinkedToUser(deviceId, userId);
        Device device = findDeviceOrThrow(deviceId);

        log.debug("Successful getting config - Device: {}, User: {}", deviceId, userId);
        return new DeviceConfigData(device.getSoftwareConfig(), device.getHardwareConfig());
    }

    private void assertDeviceLinkedToUser(UUID deviceId, long userId) {
        if (!userDeviceRepository.isDeviceLinkedToUser(deviceId, userId)) {
            log.warn("Not linked - Device: {}, User: {}", deviceId, userId);
            throw new DeviceNotLinkedToUserException(deviceId, userId);
        }
    }

    private Device findDeviceOrThrow(UUID deviceId) {
        return deviceRepository.getById(deviceId)
                .orElseThrow(() -> {
                    log.warn("Device {} not found", deviceId);
                    return new DeviceNotFoundException(deviceId);
                });
    }
}