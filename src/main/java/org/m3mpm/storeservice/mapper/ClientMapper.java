package org.m3mpm.storeservice.mapper;

import org.m3mpm.storeservice.dto.ClientDto;
import org.m3mpm.storeservice.entity.Client;
import org.m3mpm.storeservice.mapper.config.CentralMapperConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(config = CentralMapperConfig.class, uses = AddressMapper.class)
public interface ClientMapper {

    ClientDto toDto(Client client);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "registrationDate", ignore = true) // todo: Возможно не нужно будет. Подумать где нужно будет реализовать
    Client toEntity(ClientDto clientDto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "registrationDate", ignore = true) // todo: Возможно не нужно будет. Подумать где нужно будет реализовать
    void updateEntity(ClientDto clientDto, @MappingTarget Client client);
}
