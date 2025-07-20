package app.servarium.core.usecase;

import app.servarium.core.exception.DeviceNotFoundException;
import app.servarium.domain.model.ConnectionKey;
import app.servarium.domain.port.input.GenerateConnectionKeyPort;
import app.servarium.domain.port.output.ConnectionKeyRepository;
import app.servarium.domain.port.output.DeviceRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class GenerateConnectionKeyUseCase implements GenerateConnectionKeyPort {
    private final ConnectionKeyRepository connectionKeyRepository;
    private final DeviceRepository deviceRepository;

    @Override
    @Transactional
    public String execute(UUID deviceId) {
        log.debug("Generating connection key - Device: {}", deviceId);
        assertDeviceExists(deviceId);

        var newKey = ConnectionKey.generateFor(deviceId);
        connectionKeyRepository.save(newKey);

        log.info("Successful generation of connection key - Device: {}", deviceId);
        return newKey.getValue();
    }

    private void assertDeviceExists(UUID deviceId) {
        if (!deviceRepository.existsById(deviceId)) {
            log.warn("Device {} not found", deviceId);
            throw new DeviceNotFoundException(deviceId);
        }
    }
}