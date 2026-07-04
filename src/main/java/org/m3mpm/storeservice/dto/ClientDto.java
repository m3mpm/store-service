package org.m3mpm.storeservice.dto;

import lombok.*;
import lombok.experimental.Accessors;
import org.m3mpm.storeservice.type.Gender;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Builder(toBuilder = true)
public record ClientDto(
    UUID id,
    String clientName,
    String clientSurname,
    LocalDate birthday,
    Gender gender,
    LocalDateTime registrationDate,
    AddressDto address
){}
