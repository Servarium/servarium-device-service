package app.servarium.core.usecase;

import app.servarium.core.exception.DeviceAlreadyExistsException;
import app.servarium.domain.model.Device;
import app.servarium.domain.port.input.RegisterDevicePort;
import app.servarium.domain.port.output.DeviceRepository;
import app.servarium.domain.shared.params.DevicePayload;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class RegisterDeviceUseCase implements RegisterDevicePort {
    private final DeviceRepository deviceRepository;

    @Override
    @Transactional
    public void execute(DevicePayload payload) {
        final UUID deviceId = payload.deviceId();
        log.info("Registration attempt - Device: {}", deviceId);

        assertDeviceNotExists(deviceId);
        deviceRepository.save(Device.of(deviceId, payload.softwareConfig(), payload.hardwareConfig()));

        log.info("Successful registration - Device: {}", deviceId);
    }

    private void assertDeviceNotExists(UUID deviceId) {
        if(deviceRepository.existsById(deviceId)) {
            log.warn("Already exists - Device: {}", deviceId);
            throw new DeviceAlreadyExistsException(deviceId);
        }
    }
}