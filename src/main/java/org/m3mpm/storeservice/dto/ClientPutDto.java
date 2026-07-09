package org.m3mpm.storeservice.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import org.m3mpm.storeservice.type.Gender;

import java.time.LocalDate;
import java.util.UUID;

public record ClientPutDto(
        @NotBlank(message = "Name cannot be empty")
        String clientName,

        @NotBlank(message = "Surname cannot be empty")
        String clientSurname,

        @NotNull(message = "Birthday cannot be null")
        @Past(message = "Birthday must be in the past")
        LocalDate birthday,

        @NotBlank(message = "Gender is required for full update")
        @Pattern(regexp = "(?i)^(MALE|FEMALE)$", message = "Gender must be MALE or FEMALE")
        String gender, // Используем String, валидацию на корректность Enum сделаем в сервисе или через кастомную аннотацию

        @NotNull(message = "Address data is required")
        UUID addressId

//        @NotNull(message = "Address data is required")
//        @Valid
//        AddressUpdateDto address // <-- Принимаем модель полного обновления адреса
) { }
