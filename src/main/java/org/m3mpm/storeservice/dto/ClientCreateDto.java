package org.m3mpm.storeservice.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;

import java.time.LocalDate;
import java.util.UUID;

@Builder(toBuilder = true)
public record ClientCreateDto(
        @NotBlank(message = "Name cannot be empty")
        String clientName,

        @NotBlank(message = "Surname cannot be empty")
        String clientSurname,

        @NotNull(message = "Birthday cannot be null")
        @Past(message = "Birthday must be in the past") // Защита: дата рождения только из прошлого
        LocalDate birthday,

        @NotBlank(message = "Gender cannot be null")
        @Pattern(regexp = "(?i)^(MALE|FEMALE)$", message = "Gender must be MALE or FEMALE")
        String gender, // Используем String, валидацию на корректность Enum сделаем в сервисе или через кастомную аннотацию

        @NotNull(message = "Address data cannot be null")
        UUID addressId

//        @NotNull(message = "Address data is required")
//        @Valid // <-- КРИТИЧЕСКИ ВАЖНО! Чтобы Spring зашел внутрь и провалидировал поля адреса
//        AddressCreateDto address // <-- Принимаем модель создания адреса
) { }
