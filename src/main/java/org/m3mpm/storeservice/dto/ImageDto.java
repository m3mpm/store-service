package org.m3mpm.storeservice.dto;

import lombok.Builder;

import java.util.UUID;


@Builder(toBuilder = true)
public record ImageDto(
        UUID id,
        byte[] image
) {
    // Вручную скрываем бинарные данные из логов
    @Override
    public String toString() {
        return "ImageDto[id=" + id + ", image=(binary data hidden)]";
    }
}