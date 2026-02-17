package org.m3mpm.storeservice.mapper;

import org.m3mpm.storeservice.dto.SupplierDto;
import org.m3mpm.storeservice.entity.Supplier;
import org.m3mpm.storeservice.mapper.config.CentralMapperConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(config = CentralMapperConfig.class, uses = {AddressMapper.class,ProductMapper.class})
public interface SupplierMapper {

    SupplierDto toDto(Supplier supplier);

    @Mapping(target = "id", ignore = true)
    Supplier toEntity(SupplierDto supplierDto);

    @Mapping(target = "id", ignore = true)
    void updateEntity(SupplierDto supplierDto, @MappingTarget Supplier supplier);
}
