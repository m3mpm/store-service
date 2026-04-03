package org.m3mpm.storeservice.dto;

import io.swagger.v3.oas.annotations.media.Schema;
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

    @Schema(accessMode = Schema.AccessMode.READ_ONLY, example = "550e8400-e29b-41d4-a716-446655440000")
    private UUID id;

    @Schema(example = "Россия")
    @NotBlank(message = "Country cannot be empty", groups = OnCreate.class)
    @Size(max = 100, message = "Country name is too long", groups = {OnCreate.class, OnUpdate.class})
    private String country;

    @Schema(example = "Москва")
    @NotBlank(message = "City cannot be empty", groups = OnCreate.class)
    @Size(max = 100, message = "City name is too long", groups = {OnCreate.class, OnUpdate.class})
    private String city;

    @Schema(example = "Ленина, 10")
    @NotBlank(message = "Street cannot be empty", groups = OnCreate.class)
    @Size(max = 100, message = "Street name is too long", groups = {OnCreate.class, OnUpdate.class})
    private String street;

}
