package app.servarium.core.usecase;

import app.servarium.core.exception.DeviceNotLinkedToUserException;
import app.servarium.domain.port.input.UnlinkDeviceFromUserPort;
import app.servarium.domain.port.output.UserDeviceRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class UnlinkDeviceFromUserUseCase implements UnlinkDeviceFromUserPort {
    private final UserDeviceRepository userDeviceRepository;

    @Override
    @Transactional
    public void execute(UUID deviceId, long userId) {
        log.debug("Unlinking device attempt - User: {}, Device: {}", userId, deviceId);

        assertDeviceLinkedToUser(deviceId, userId);
        userDeviceRepository.delete(deviceId, userId);

        log.info("Successfully unlinked device - User: {}, Device: {}", userId, deviceId);
    }

    private void assertDeviceLinkedToUser(UUID deviceId, long userId) {
        if (!userDeviceRepository.isDeviceLinkedToUser(deviceId, userId)) {
            log.warn("Not linked - Device: {}, User: {}", deviceId, userId);
            throw new DeviceNotLinkedToUserException(deviceId, userId);
        }
    }
}