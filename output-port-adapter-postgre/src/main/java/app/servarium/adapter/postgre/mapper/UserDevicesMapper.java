package app.servarium.adapter.postgre.mapper;

import app.servarium.adapter.postgre.jooq.generated.tables.records.UserDevicesRecord;
import app.servarium.domain.model.UserDevice;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(config = MapperConfig.class)
public interface UserDevicesMapper {

    UserDevice toDomain(UserDevicesRecord r);

    void updateRecord(@MappingTarget UserDevicesRecord r, UserDevice domain);
}
