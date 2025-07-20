package app.servarium.core.usecase;

import app.servarium.core.exception.DeviceNotFoundException;
import app.servarium.domain.model.Device;
import app.servarium.domain.port.input.UpdateDeviceConfigPort;
import app.servarium.domain.port.output.DeviceRepository;
import app.servarium.domain.shared.params.DevicePayload;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class UpdateDeviceConfigUseCase implements UpdateDeviceConfigPort {
    private final DeviceRepository deviceRepository;

    @Override
    @Transactional
    public void execute(DevicePayload payload) {
        final UUID deviceId = payload.deviceId();
        log.debug("Updating device config - Device: {}", deviceId);

        Device device = findDeviceOrThrow(deviceId)
                .updateSoftwareConfig(payload.softwareConfig())
                .updateHardwareConfig(payload.hardwareConfig());

        deviceRepository.save(device);

        log.info("Successfully updated device config - Device: {}", deviceId);
    }

    private Device findDeviceOrThrow(UUID deviceId) {
        return deviceRepository.getById(deviceId)
                .orElseThrow(() -> {
                    log.warn("Device {} not found", deviceId);
                    return new DeviceNotFoundException(deviceId);
                });
    }
}