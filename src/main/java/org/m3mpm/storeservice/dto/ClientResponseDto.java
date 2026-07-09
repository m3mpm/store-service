package org.m3mpm.storeservice.dto;

import lombok.Builder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Builder(toBuilder = true)
public record ClientResponseDto(
        UUID id,
        String clientName,
        String clientSurname,
        LocalDate birthday,
        String gender, // Передаем как String для простоты интеграции
        LocalDateTime registrationDate,
        UUID addressId
//        AddressResponseDto address // <-- Заменили на Response-модель адреса
) { }
