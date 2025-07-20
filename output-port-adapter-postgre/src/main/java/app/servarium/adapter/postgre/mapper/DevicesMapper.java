package app.servarium.adapter.postgre.mapper;

import app.servarium.adapter.postgre.jooq.generated.enums.OsType;
import app.servarium.adapter.postgre.jooq.generated.tables.records.DevicesRecord;
import app.servarium.domain.model.Device;
import app.servarium.domain.model.value.HardwareConfig;
import app.servarium.domain.model.value.SoftwareConfig;
import org.jooq.Record3;
import org.jooq.Record5;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.math.BigDecimal;
import java.util.UUID;

@Mapper(config = MapperConfig.class)
public interface DevicesMapper {

    @Mapping(source = "id", target = "id")
    @Mapping(source = "softwareConfig.osType", target = "osType")
    @Mapping(source = "softwareConfig.osVersion", target = "osVersion")
    @Mapping(source = "hardwareConfig.cpuName", target = "cpuName")
    @Mapping(source = "hardwareConfig.gpuName", target = "gpuName")
    @Mapping(source = "hardwareConfig.machineName", target = "machineName")
    @Mapping(source = "hardwareConfig.mainDiskName", target = "mainDiskName")
    @Mapping(source = "hardwareConfig.ramAmount", target = "ramAmount")
    void updateRecord(@MappingTarget DevicesRecord r, Device model);

    @InheritInverseConfiguration
    Device toDomain(DevicesRecord r);

    @Mapping(target = "cpuName", expression = "java(r.value1())")
    @Mapping(target = "gpuName", expression = "java(r.value2())")
    @Mapping(target = "ramAmount", expression = "java(r.value3().floatValue())")
    @Mapping(target = "mainDiskName", expression = "java(r.value4())")
    @Mapping(target = "machineName", expression = "java(r.value5())")
    HardwareConfig toHardwareConfig(Record5<String, String, BigDecimal, String, String> r);

    @Mapping(target = "osType", expression = "java(OSType.valueOf(r.value2().name()))")
    @Mapping(target = "osVersion", expression = "java(r.value3())")
    SoftwareConfig toSoftwareConfig(Record3<UUID, OsType, String> r);
}