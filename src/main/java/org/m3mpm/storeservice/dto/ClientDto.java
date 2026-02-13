package org.m3mpm.storeservice.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.m3mpm.storeservice.type.Gender;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

//@NoArgsConstructor
//@Setter @Getter
//@ToString
//@Accessors(chain = true)
public record ClientDto(
    UUID id,
    String clientName,
    String clientSurname,
    LocalDate birthday,
    Gender gender,
    LocalDateTime registrationDate,
    AddressDto address
){}
