package app.servarium.domain.port.output;

import app.servarium.domain.model.UserDevice;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserDeviceRepository {
    boolean isDeviceLinkedToUser(UUID deviceId, long userId);

    Optional<UserDevice> getByDeviceIdAndUserId(UUID deviceId, long userId);

    List<UserDevice> getAllByUserId(long userId);

    void save(UserDevice userDevice);

    void delete(UUID deviceId, long userId);
}