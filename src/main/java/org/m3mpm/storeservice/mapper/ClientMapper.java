package org.m3mpm.storeservice.mapper;

import org.m3mpm.storeservice.dto.ClientCreateDto;
import org.m3mpm.storeservice.dto.ClientPatchDto;
import org.m3mpm.storeservice.dto.ClientPutDto;
import org.m3mpm.storeservice.dto.ClientResponseDto;
import org.m3mpm.storeservice.entity.Client;
import org.m3mpm.storeservice.mapper.config.CentralMapperConfig;
import org.m3mpm.storeservice.type.Gender;
import org.mapstruct.*;

import java.util.List;

@Mapper(config = CentralMapperConfig.class, uses = {AddressMapper.class})
public interface ClientMapper {

    // 1. Из Entity в DTO для ответа клиенту (GET, POST, PUT, PATCH)
    @Mapping(source = "address.id", target = "addressId")
    ClientResponseDto toDto(Client client);

    // 2. Из DTO создания новую Entity (POST)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "registrationDate", ignore = true)
    @Mapping(target = "address", ignore = true) // Игнорируем вложенный объект Address, свяжем его вручную в сервисе
    @Mapping(target = "gender", source = "gender", qualifiedByName = "stringToGender")
    Client toEntity(ClientCreateDto createDto);

    // 3. Для PUT (полное обновление: null затрет старое значение)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "registrationDate", ignore = true)
    @Mapping(target = "address", ignore = true) // Игнорируем вложенный объект Address, свяжем его вручную в сервисе
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.SET_TO_NULL)
    void updateEntity(ClientPutDto updateDto, @MappingTarget Client client);

    // 4. Для PATCH (частичное обновление: null игнорируется)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "registrationDate", ignore = true)
    @Mapping(target = "address", ignore = true) // Игнорируем вложенный объект Address, свяжем его вручную в сервисе
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void patchEntity(ClientPatchDto patchDto, @MappingTarget Client client);

    // 5. Маппинг списков (для GET /api/v1/addresses)
    List<ClientResponseDto> toDtoList(List<Client> clientList);

    // ЭТОТ МЕТОД будет автоматически вызываться MapStruct для любого маппинга String -> Gender
    @Named("stringToGender")
    default Gender mapStringToGender(String genderStr) {
        if (genderStr == null || genderStr.isBlank()) {
            return null;
        }
        // Переводим в UPPERCASE ("male" или "Male" станут "MALE") и убираем лишние пробелы
        return Gender.valueOf(genderStr.trim().toUpperCase());
    }

}
