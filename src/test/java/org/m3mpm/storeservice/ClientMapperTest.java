package org.m3mpm.storeservice;

import com.github.f4b6a3.uuid.UuidCreator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.m3mpm.storeservice.dto.AddressDto;
import org.m3mpm.storeservice.dto.ClientDto;
import org.m3mpm.storeservice.entity.Address;
import org.m3mpm.storeservice.entity.Client;
import org.m3mpm.storeservice.mapper.AddressMapperImpl;
import org.m3mpm.storeservice.mapper.ClientMapper;
import org.m3mpm.storeservice.mapper.ClientMapperImpl;
import org.m3mpm.storeservice.type.Gender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {
        ClientMapperImpl.class,
        AddressMapperImpl.class})
public class ClientMapperTest {

    @Autowired
    private ClientMapper clientMapper;

    @Test
    @DisplayName("Маппинг из Entity в DTO: все поля должны совпадать, включая вложенный адрес")
    void shouldMapEntityToDto() {

        UUID idAddress = UUID.randomUUID();
        Address address = new Address();
        address.setId(idAddress);
        address.setCity("Moscow");

        UUID idClient = UUID.randomUUID();
        LocalDateTime regDate = LocalDateTime.now();

        Client entity = new Client()
                .setId(idClient)
                .setClientName("Ivan")
                .setClientSurname("Ivanov")
                .setBirthday(LocalDate.of(1990, 5, 20))
                .setGender(Gender.MALE)
                .setRegistrationDate(regDate)
                .setAddress(address);

        ClientDto dto = clientMapper.toDto(entity);

        assertThat(dto).isNotNull();
        assertThat(dto.id()).isEqualTo(idClient);
        assertThat(dto.clientName()).isEqualTo("Ivan");
        assertThat(dto.gender()).isEqualTo(Gender.MALE);
        assertThat(dto.registrationDate()).isEqualTo(regDate);

        // Проверка работы AddressMapper внутри ClientMapper
        assertThat(dto.address()).isNotNull();
        assertThat(dto.address().getCity()).isEqualTo("Moscow");
    }

    @Test
    @DisplayName("Маппинг DTO в Entity: игнорирование ID и даты регистрации")
    void shouldMapDtoToEntityAndIgnoreIdAndRegistrationDate() {
        UUID clientDtoId = UuidCreator.getTimeOrderedEpoch();
        ClientDto dto = new ClientDto(
                clientDtoId,
                "Anna",
                "Smith",
                LocalDate.of(1995, 1, 1),
                Gender.FEMALE,
                LocalDateTime.now(),
                null
        );

        Client entity = clientMapper.toEntity(dto);

        assertThat(entity.getClientName()).isEqualTo("Anna");
        assertThat(entity.getGender()).isEqualTo(Gender.FEMALE);

        assertThat(entity.getId()).isNull();
        assertThat(entity.getRegistrationDate()).isNull();
    }

    @Test
    @DisplayName("Обновление существующей Entity: поля меняются, ID и дата регистрации остаются прежними")
    void shouldUpdateEntityFromDtoButKeepOriginalIdAndRegistrationDate() {

        UUID originalAddressId = UuidCreator.getTimeOrderedEpoch();
        Address originalAddress = new Address()
                .setId(originalAddressId)
                .setCountry("Belarus")
                .setCity("Minsk")
                .setStreet("Voloha");


        UUID originalClientId = UuidCreator.getTimeOrderedEpoch();
        LocalDateTime originalRegDate = LocalDateTime.now().minusYears(1);
        Client existingClient = new Client()
                .setId(originalClientId)
                .setClientName("OldName")
                .setClientSurname("OldSurname")
                .setBirthday(LocalDate.of(1995, 1, 1))
                .setGender(Gender.MALE)
                .setRegistrationDate(originalRegDate)
                .setAddress(originalAddress);



        UUID updateAddressId = UuidCreator.getTimeOrderedEpoch();
        AddressDto addressDto = new AddressDto()
                .setId(updateAddressId)
                .setCountry("Russia")
                .setCity("Moscow")
                .setStreet("Varvarka");

        ClientDto updateDto = new ClientDto(
                UuidCreator.getTimeOrderedEpoch(),
                "NewName",
                "NewSurname", LocalDate.of(1996, 1, 31), Gender.FEMALE,
                LocalDateTime.now(), // Новая дата в DTO
                addressDto
        );

        clientMapper.updateEntity(updateDto, existingClient);

        // ID & Дата регистрации не изменились
        assertThat(existingClient.getId()).isEqualTo(originalClientId);
        assertThat(existingClient.getRegistrationDate()).isEqualTo(originalRegDate);

        assertThat(existingClient.getClientName()).isEqualTo(updateDto.clientName());
        assertThat(existingClient.getClientSurname()).isEqualTo(updateDto.clientSurname());
        assertThat(existingClient.getBirthday()).isEqualTo(updateDto.birthday());
        assertThat(existingClient.getGender()).isEqualTo(updateDto.gender());

        // Проверка работы AddressMapper внутри ClientMapper
        // ID не изменилось
        assertThat(existingClient.getAddress().getId()).isEqualTo(originalAddressId);

        assertThat(existingClient.getAddress().getCountry()).isEqualTo(addressDto.getCountry());
        assertThat(existingClient.getAddress().getCity()).isEqualTo(addressDto.getCity());
        assertThat(existingClient.getAddress().getStreet()).isEqualTo(addressDto.getStreet());

    }
}
