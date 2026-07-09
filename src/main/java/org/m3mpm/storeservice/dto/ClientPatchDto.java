package org.m3mpm.storeservice.dto;

import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import org.m3mpm.storeservice.type.Gender;

import java.time.LocalDate;
import java.util.UUID;

public record ClientPatchDto(
        @Pattern(regexp = "^$|\\s*\\S.*", message = "Name cannot be blank if provided")
        String clientName,

        @Pattern(regexp = "^$|\\s*\\S.*", message = "Surname cannot be blank if provided")
        String clientSurname,

        @Past(message = "Birthday must be in the past")
        LocalDate birthday,

        String gender,

        UUID addressId

//        @Valid // На случай, если в PATCH запросе передали частичные изменения адреса
//        AddressPatchDto address // <-- Принимаем модель частичного обновления адреса
) { }
