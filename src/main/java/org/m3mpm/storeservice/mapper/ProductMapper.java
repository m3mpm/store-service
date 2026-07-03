package org.m3mpm.storeservice.mapper;

import org.m3mpm.storeservice.dto.ProductDto;
import org.m3mpm.storeservice.entity.Product;
import org.m3mpm.storeservice.mapper.config.CentralMapperConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;

import java.util.List;

@Mapper(config = CentralMapperConfig.class)
public interface ProductMapper {

    @Mapping(target = "supplierId", source = "supplier.id")
    @Mapping(target = "imageId", source = "image.id")
    ProductDto toDto(Product product);

    // ДОБАВИЛ ЭТОТ МЕТОД: он маппит продукт, но игнорирует поставщика
    @Named("withoutSupplier") // Даем методу уникальное имя (идентификатор)
    @Mapping(target = "supplierId", ignore = true) // Явно говорим не трогать поставщика
    @Mapping(target = "imageId", source = "image.id")
    ProductDto toDtoWithoutSupplier(Product product);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "supplier", ignore = true)
    @Mapping(target = "image", ignore = true)
    Product toEntity(ProductDto productDto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "supplier", ignore = true)
    @Mapping(target = "image", ignore = true)
    void updateEntity(ProductDto productDto, @MappingTarget Product product);

    List<ProductDto> toDtoList(List<Product> productList);

    List<Product> toEntityList(List<ProductDto> productDtoList);
}
