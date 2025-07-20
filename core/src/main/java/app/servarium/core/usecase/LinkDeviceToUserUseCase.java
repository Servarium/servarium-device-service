package app.servarium.core.usecase;

import app.servarium.core.exception.ConnectionKeyInvalidException;
import app.servarium.core.exception.DeviceAlreadyLinkedToUserException;
import app.servarium.core.exception.DeviceNotFoundException;
import app.servarium.domain.model.ConnectionKey;
import app.servarium.domain.model.UserDevice;
import app.servarium.domain.port.input.LinkDeviceToUserPort;
import app.servarium.domain.port.output.ConnectionKeyRepository;
import app.servarium.domain.port.output.DeviceRepository;
import app.servarium.domain.port.output.UserDeviceRepository;
import app.servarium.domain.shared.params.LinkDeviceParams;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class LinkDeviceToUserUseCase implements LinkDeviceToUserPort {
    private final UserDeviceRepository userDeviceRepository;
    private final DeviceRepository deviceRepository;
    private final ConnectionKeyRepository connectionKeyRepository;

    @Override
    @Transactional
    public void execute(LinkDeviceParams params) {
        final long userId = params.userId();
        final String keyValue = params.keyValue();
        log.debug("Linking device attempt - User: {}, ConnectionKey: {}", userId, keyValue);

        ConnectionKey connectionKey = findConnectionKey(keyValue);
        UUID deviceId = connectionKey.getDeviceId();

        assertDeviceExists(deviceId);
        assertDeviceNotLinkedToUser(deviceId, userId);

        userDeviceRepository.save(new UserDevice(userId, deviceId, params.aliasName()));
        log.info("Successful linking - User: {}, Device: {}", userId, deviceId);
    }

    private ConnectionKey findConnectionKey(String keyValue) {
        return connectionKeyRepository.getByKeyValue(keyValue)
                .orElseThrow(() -> {
                    log.warn("Connection key {} is invalid", keyValue);
                    return new ConnectionKeyInvalidException(keyValue);
                });
    }

    private void assertDeviceExists(UUID deviceId) {
        if (!deviceRepository.existsById(deviceId)) {
            log.warn("Device {} not found", deviceId);
            throw new DeviceNotFoundException(deviceId);
        }
    }

    private void assertDeviceNotLinkedToUser(UUID deviceId, long userId) {
        if (userDeviceRepository.isDeviceLinkedToUser(deviceId, userId)) {
            log.warn("Already linked - Device: {}, User: {}", deviceId, userId);
            throw new DeviceAlreadyLinkedToUserException(deviceId, userId);
        }
    }
}