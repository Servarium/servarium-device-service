package app.servarium.adapter.postgre;

import app.servarium.adapter.postgre.mapper.UserDevicesMapper;
import app.servarium.domain.model.UserDevice;
import app.servarium.domain.port.output.UserDeviceRepository;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static app.servarium.adapter.postgre.jooq.generated.Tables.USER_DEVICES;

@Repository
@RequiredArgsConstructor
public class JooqUserDeviceRepositoryAdapter implements UserDeviceRepository {
    private final DSLContext dsl;
    private final UserDevicesMapper recordMapper;

    @Override
    public boolean isDeviceLinkedToUser(UUID deviceId, long userId) {
        return dsl.fetchExists(dsl.selectOne()
                .from(USER_DEVICES)
                .where(USER_DEVICES.DEVICE_ID.eq(deviceId))
                .and(USER_DEVICES.USER_ID.eq(userId)));
    }

    @Override
    public Optional<UserDevice> getByDeviceIdAndUserId(UUID deviceId, long userId) {
        var condition = USER_DEVICES.USER_ID.eq(userId)
                .and(USER_DEVICES.DEVICE_ID.eq(deviceId));

        return dsl.fetchOptional(USER_DEVICES, condition)
                .map(recordMapper::toDomain);
    }

    @Override
    public List<UserDevice> getAllByUserId(long userId) {
        return dsl.selectFrom(USER_DEVICES)
                .where(USER_DEVICES.USER_ID.eq(userId))
                .fetch()
                .map(recordMapper::toDomain);
    }

    @Override
    public void save(UserDevice userDevice) {
        var condition = USER_DEVICES.USER_ID.eq(userDevice.getUserId())
                .and(USER_DEVICES.DEVICE_ID.eq(userDevice.getDeviceId()));

        var userDeviceRecord = dsl.fetchOptional(USER_DEVICES, condition)
                .orElse(dsl.newRecord(USER_DEVICES));

        recordMapper.updateRecord(userDeviceRecord, userDevice);

        userDeviceRecord.store();
    }

    @Override
    public void delete(UUID deviceId, long userId) {
        var condition = USER_DEVICES.USER_ID.eq(userId)
                .and(USER_DEVICES.DEVICE_ID.eq(deviceId));

        dsl.deleteFrom(USER_DEVICES)
                .where(condition)
                .execute();
    }
}
