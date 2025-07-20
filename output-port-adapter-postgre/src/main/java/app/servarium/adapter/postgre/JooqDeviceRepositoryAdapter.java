package app.servarium.adapter.postgre;

import app.servarium.adapter.postgre.jooq.generated.tables.records.DevicesRecord;
import app.servarium.adapter.postgre.mapper.DevicesMapper;
import app.servarium.domain.model.Device;
import app.servarium.domain.model.value.HardwareConfig;
import app.servarium.domain.model.value.SoftwareConfig;
import app.servarium.domain.port.output.DeviceRepository;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.jooq.Record3;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import static app.servarium.adapter.postgre.jooq.generated.Tables.DEVICES;

@Repository
@RequiredArgsConstructor
public class JooqDeviceRepositoryAdapter implements DeviceRepository {
    private final DSLContext dsl;
    private final DevicesMapper recordMapper;

    @Override
    public boolean existsById(UUID deviceId) {
        return dsl.fetchExists(dsl.selectOne()
                .from(DEVICES)
                .where(DEVICES.ID.eq(deviceId)));
    }

    @Override
    public void save(Device device) {
        DevicesRecord deviceRecord = dsl.fetchOptional(DEVICES, DEVICES.ID.eq(device.getId()))
                .orElse(dsl.newRecord(DEVICES));

        recordMapper.updateRecord(deviceRecord, device);

        deviceRecord.store();
    }

    @Override
    public Optional<HardwareConfig> getHardwareConfigByDeviceId(UUID deviceId) {
        var hardwareConfigRecord = dsl.select(DEVICES.CPU_NAME,
                        DEVICES.GPU_NAME,
                        DEVICES.RAM_AMOUNT,
                        DEVICES.MAIN_DISK_NAME,
                        DEVICES.MACHINE_NAME)
                .from(DEVICES)
                .where(DEVICES.ID.eq(deviceId))
                .fetchOptional();

        return hardwareConfigRecord.map(recordMapper::toHardwareConfig);
    }

    @Override
    public Map<UUID, SoftwareConfig> getSoftwareConfigsByDeviceIds(List<UUID> deviceIds) {
        return dsl.select(DEVICES.ID, DEVICES.OS_TYPE, DEVICES.OS_VERSION)
                .from(DEVICES)
                .where(DEVICES.ID.in(deviceIds))
                .fetchMap(
                        Record3::value1,
                        recordMapper::toSoftwareConfig
                );
    }

    @Override
    public Optional<Device> getById(UUID deviceId) {
        return dsl.fetchOptional(DEVICES)
                .map(recordMapper::toDomain);
    }
}
