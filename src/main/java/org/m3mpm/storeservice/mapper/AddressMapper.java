package org.m3mpm.storeservice.mapper;

import org.m3mpm.storeservice.dto.AddressDto;
import org.m3mpm.storeservice.entity.Address;
import org.m3mpm.storeservice.mapper.config.CentralMapperConfig;
import org.mapstruct.*;

import java.util.List;

@Mapper(config = CentralMapperConfig.class)
public interface AddressMapper {

    AddressDto toDto(Address address);

    @Mapping(target = "id", ignore = true)
    Address toEntity(AddressDto addressDto);

    @Mapping(target = "id", ignore = true)
    void updateEntity(AddressDto addressDto, @MappingTarget Address address);

    List<AddressDto> toDtoList(List<Address> addressList);

    List<Address> toEntityList(List<AddressDto> addressDtoList);
}
