package org.m3mpm.storeservice.mapper;

import org.m3mpm.storeservice.dto.ImageDto;
import org.m3mpm.storeservice.entity.Image;
import org.m3mpm.storeservice.mapper.config.CentralMapperConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;


@Mapper(config = CentralMapperConfig.class)
public interface ImageMapper {

    ImageDto toDto(Image image);

    @Mapping(target = "id", ignore = true)
    Image toEntity(ImageDto imageDto);

    @Mapping(target = "id", ignore = true)
    void updateEntity(ImageDto imageDto, @MappingTarget Image image);

}
