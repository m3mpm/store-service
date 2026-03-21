package org.m3mpm.storeservice;

import com.github.f4b6a3.uuid.UuidCreator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.m3mpm.storeservice.dto.ImageDto;
import org.m3mpm.storeservice.entity.Image;
import org.m3mpm.storeservice.mapper.ImageMapper;
import org.mapstruct.factory.Mappers;

import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class ImageMapperTest {

    // Получаем экземпляр маппера через фабрику MapStruct
    private final ImageMapper mapper = Mappers.getMapper(ImageMapper.class);

    @Test
    @DisplayName("Должен корректно маппить Image в ImageDto")
    void shouldMapEntityToDto() {
        UUID randomId = UUID.randomUUID(); // UUID v4
        byte[] imageData = {1, 2, 3, 4, 5};
        Image image = new Image()
                .setId(randomId)
                .setImage(imageData);

        ImageDto resultDto = mapper.toDto(image);

        assertThat(resultDto).isNotNull();
        assertThat(resultDto.getId()).isEqualTo(randomId);
        assertThat(resultDto.getImage()).isEqualTo(imageData);
    }

    @Test
    @DisplayName("Должен создавать сущность из DTO, игнорируя ID")
    void shouldMapDtoToEntityAndIgnoreId() {
        UUID dtoId = UuidCreator.getTimeOrderedEpoch(); // UUID v7
        byte[] imageData = {10, 20, 30};
        ImageDto dto = new ImageDto()
                .setId(dtoId)
                .setImage(imageData);

        Image resultEntity = mapper.toEntity(dto);

        assertThat(resultEntity).isNotNull();
        assertThat(resultEntity.getId()).isNull();
        assertThat(resultEntity.getImage()).isEqualTo(imageData);
    }

    @Test
    @DisplayName("Должен обновлять существующую сущность, не затирая её ID")
    void shouldUpdateEntityFromDtoButKeepOriginalId() {
        UUID originalId = UuidCreator.getTimeOrderedEpoch();
        byte[] oldData = {0, 0, 0};
        byte[] newData = {1, 1, 1};

        Image existingEntity = new Image()
                .setId(originalId)
                .setImage(oldData);

        ImageDto incomingDto = new ImageDto()
                .setId(UuidCreator.getTimeOrderedEpoch())
                .setImage(newData);

        mapper.updateEntity(incomingDto, existingEntity);

        assertThat(existingEntity.getId()).isEqualTo(originalId);
        assertThat(existingEntity.getImage()).isEqualTo(newData);
    }

    @Test
    @DisplayName("Должен возвращать null, если на вход подан null")
    void shouldReturnNullWhenSourceIsNull() {
        assertThat(mapper.toDto(null)).isNull();
        assertThat(mapper.toEntity(null)).isNull();
    }
}
