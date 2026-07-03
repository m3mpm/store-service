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

    // Используется для PUT (полное обновление: null затрет старое значение)
    @Named("standardUpdate")
    @Mapping(target = "id", ignore = true) // Защищаем ID от перезаписи
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.SET_TO_NULL)
    void updateEntity(AddressDto addressDto, @MappingTarget Address address);

    // Используется для PATCH (частичное обновление: null игнорируется)
    @Named("standardPatch")
    @Mapping(target = "id", ignore = true) // Защищаем ID от перезаписи
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void patchEntity(AddressDto addressDto, @MappingTarget Address address);

    List<AddressDto> toDtoList(List<Address> addressList);

    List<Address> toEntityList(List<AddressDto> addressDtoList);
}
