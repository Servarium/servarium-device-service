package app.servarium.core.usecase;

import app.servarium.core.exception.DeviceNotLinkedToUserException;
import app.servarium.domain.model.UserDevice;
import app.servarium.domain.port.input.UpdateUserDevicePort;
import app.servarium.domain.port.output.UserDeviceRepository;
import app.servarium.domain.shared.params.UpdateUserDeviceParams;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class UpdateUserDeviceUseCase implements UpdateUserDevicePort {
    private final UserDeviceRepository userDeviceRepository;

    @Override
    @Transactional
    public void execute(UpdateUserDeviceParams params) {
        final UUID deviceId = params.deviceId();
        final long userId = params.userId();
        log.debug("Attempt to update user device - Device: {}, User: {}", deviceId, userId);

        UserDevice userDevice = findUserDevice(deviceId, userId);
        userDevice.setAliasName(params.newAliasName());

        userDeviceRepository.save(userDevice);
        log.info("User device successfully updated - Device: {}, User: {}", deviceId, userId);
    }

    private UserDevice findUserDevice(UUID deviceId, long userId) {
        return userDeviceRepository.getByDeviceIdAndUserId(deviceId, userId)
                .orElseThrow(() -> {
                    log.warn("Not linked - Device: {}, User: {}", deviceId, userId);
                    return new DeviceNotLinkedToUserException(deviceId, userId);
                });
    }
}