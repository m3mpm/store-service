package org.m3mpm.storeservice.mapper;

import org.m3mpm.storeservice.dto.ProductDto;
import org.m3mpm.storeservice.dto.SupplierDto;
import org.m3mpm.storeservice.entity.Product;
import org.m3mpm.storeservice.entity.Supplier;
import org.m3mpm.storeservice.mapper.config.CentralMapperConfig;
import org.mapstruct.*;

import java.util.List;

@Mapper(config = CentralMapperConfig.class, uses = {AddressMapper.class,ProductMapper.class})
public interface SupplierMapper {

    @Mapping(target = "products", qualifiedByName = "toSupplierProductDtoList")
    SupplierDto toDto(Supplier supplier);

    @Mapping(target = "id", ignore = true)
    Supplier toEntity(SupplierDto supplierDto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "products", ignore = true)
    @Mapping(target = "address", qualifiedByName = "standardUpdate")
    void updateEntity(SupplierDto supplierDto, @MappingTarget Supplier supplier);

    // Она заставит MapStruct использовать безопасный метод из ProductMapper
    @Named("toSupplierProductDtoList")
    @IterableMapping(qualifiedByName = "withoutSupplier")
    List<ProductDto> toDtoList(List<Product> products);

    List<Supplier> toEntityList(List<SupplierDto> supplierDtos);
}
