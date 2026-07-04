package org.m3mpm.storeservice;

import com.github.f4b6a3.uuid.UuidCreator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.m3mpm.storeservice.dto.AddressDto;
import org.m3mpm.storeservice.entity.Address;
import org.m3mpm.storeservice.mapper.AddressMapper;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


public class AddressMapperTests {

    // Получаем экземпляр маппера через фабрику MapStruct
    private final AddressMapper mapper = Mappers.getMapper(AddressMapper.class);

    @Test
    @DisplayName("Маппинг из Entity в DTO: все поля должны совпадать")
    void shouldMapEntityToDto() {
        UUID addressId = UUID.randomUUID(); // UUID v4
        Address entity = new Address()
                .setId(addressId)
                .setCountry("Russia")
                .setCity("Moscow")
                .setStreet("Lenina");

        AddressDto dto = mapper.toDto(entity);

        assertThat(dto).isNotNull();
        // ИСПРАВЛЕНО: замена геттеров getX() на синтаксис record x()
        assertThat(dto.id()).isEqualTo(entity.getId());
        assertThat(dto.country()).isEqualTo(entity.getCountry());
        assertThat(dto.city()).isEqualTo(entity.getCity());
        assertThat(dto.street()).isEqualTo(entity.getStreet());
    }

    @Test
    @DisplayName("Маппинг из DTO в Entity: ID должен быть проигнорирован")
    void shouldMapDtoToEntityAndIgnoreId(){
        UUID addressId = UuidCreator.getTimeOrderedEpoch(); // UUID v7
        // ИСПРАВЛЕНО: создание объекта через Builder вместо сеттеров
        AddressDto dto = AddressDto.builder()
                .id(addressId)
                .country("Russia")
                .city("SPb")
                .street("Nevsky")
                .build();

        Address entity = mapper.toEntity(dto);

        assertThat(entity).isNotNull();
        assertThat(entity.getId()).isNull();
        assertThat(entity.getCountry()).isEqualTo(dto.country());
        assertThat(entity.getCity()).isEqualTo(dto.city());
        assertThat(entity.getStreet()).isEqualTo(dto.street());
    }

    @Test
    @DisplayName("Обновление существующей Entity: поля меняются, ID остается прежним")
    void shouldUpdateEntityFromDtoButKeepOriginalId(){
        UUID originalId = UuidCreator.getTimeOrderedEpoch();
        Address entity = new Address()
                .setId(originalId)
                .setCountry("Old Country")
                .setCity("Old City")
                .setStreet("Old Street");

        // ИСПРАВЛЕНО: создание объекта через Builder вместо сеттеров
        AddressDto dto = AddressDto.builder()
                .id(null)
                .country("New Country")
                .city("New City")
                .street("New Street")
                .build();

        mapper.updateEntity(dto, entity);

        assertThat(entity.getId()).isEqualTo(originalId);
        assertThat(entity.getCountry()).isEqualTo("New Country");
        assertThat(entity.getCity()).isEqualTo("New City");
        assertThat(entity.getStreet()).isEqualTo("New Street");
    }

    @Test
    @DisplayName("Маппинг Entity списка в DTO список: размеры и содержимое должны совпадать")
    void shouldMapToDtoLit(){
        Address address1 = new Address().setCity("City 1");
        Address address2 = new Address().setCity("City 2");
        List<Address> list = List.of(address1, address2);

        List<AddressDto> dtoList = mapper.toDtoList(list);

        assertThat(dtoList.size()).isEqualTo(list.size());
        // ИСПРАВЛЕНО: замена геттеров getCity() на синтаксис record city()
        assertThat(dtoList.get(0).city()).isEqualTo("City 1");
        assertThat(dtoList.get(1).city()).isEqualTo("City 2");
    }

    @Test
    @DisplayName("Маппинг DTO списка в Entity список: размеры и содержимое должны совпадать")
    void shouldMapToEntityList(){
        // ИСПРАВЛЕНО: создание объектов через Builder вместо сеттеров
        AddressDto dto1 = AddressDto.builder().city("City 1").build();
        AddressDto dto2 = AddressDto.builder().city("City 2").build();
        List<AddressDto> list = List.of(dto1, dto2);

        List<Address> entityList = mapper.toEntityList(list);

        assertThat(entityList.size()).isEqualTo(list.size());
        assertThat(entityList.get(0).getCity()).isEqualTo("City 1");
        assertThat(entityList.get(1).getCity()).isEqualTo("City 2");
    }
}
