package org.m3mpm.storeservice.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.Accessors;
import org.m3mpm.storeservice.validation.OnCreate;
import org.m3mpm.storeservice.validation.OnUpdate;

import java.util.UUID;

@NoArgsConstructor
@Setter @Getter
@ToString
@Accessors(chain = true)
public class AddressDto {

    private UUID id;

    @NotBlank(message = "City cannot be empty", groups = OnCreate.class)
    @Size(max = 100, message = "City name is too long", groups = {OnCreate.class, OnUpdate.class})
    private String city;

    @NotBlank(message = "Country cannot be empty", groups = OnCreate.class)
    @Size(max = 100, message = "Country name is too long", groups = {OnCreate.class, OnUpdate.class})
    private String country;

    @NotBlank(message = "Street cannot be empty", groups = OnCreate.class)
    @Size(max = 100, message = "Street name is too long", groups = {OnCreate.class, OnUpdate.class})
    private String street;

}
