package org.m3mpm.storeservice.mapper;

import org.m3mpm.storeservice.dto.*;
import org.m3mpm.storeservice.entity.Address;
import org.m3mpm.storeservice.mapper.config.CentralMapperConfig;
import org.mapstruct.*;

import java.util.List;

@Mapper(config = CentralMapperConfig.class)
public interface AddressMapper {

    // 1. Из Entity в DTO для ответа клиенту (GET, POST, PUT, PATCH)
    AddressResponseDto toDto(Address address);

    // 2. Из DTO создания в новую Entity (POST)
    @Mapping(target = "id", ignore = true)
    Address toEntity(AddressCreateDto createDto);

    // 3. Для добавления Address в объектах Client & Supplier после поиска адрсса по id: AddressResponseDto findById(UUID id)
    Address toEntity(AddressResponseDto responseDto);

    // 4. Для PUT (полное обновление: null затрет старое значение)
    @Mapping(target = "id", ignore = true) // Защищаем ID от перезаписи
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.SET_TO_NULL)
    void updateEntity(AddressPutDto updateDto, @MappingTarget Address address);

    // 5. Для PATCH (частичное обновление: null игнорируется)
    @Mapping(target = "id", ignore = true) // Защищаем ID от перезаписи
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void patchEntity(AddressPatchDto patchDto, @MappingTarget Address address);

    // 6. Маппинг списков (для GET /api/v1/addresses)
    List<AddressResponseDto> toDtoList(List<Address> addressList);

}
