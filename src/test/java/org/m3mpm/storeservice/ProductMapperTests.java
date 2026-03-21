package org.m3mpm.storeservice;

import com.github.f4b6a3.uuid.UuidCreator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.m3mpm.storeservice.dto.ProductDto;
import org.m3mpm.storeservice.entity.Image;
import org.m3mpm.storeservice.entity.Product;
import org.m3mpm.storeservice.entity.Supplier;
import org.m3mpm.storeservice.mapper.ProductMapper;
import org.m3mpm.storeservice.type.ProductCategory;
import org.mapstruct.factory.Mappers;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class ProductMapperTests {

    // Получаем экземпляр маппера через фабрику MapStruct для Unit-теста
    private final ProductMapper mapper = Mappers.getMapper(ProductMapper.class);

    @Test
    @DisplayName("Маппинг из Entity в DTO должен корректно переносить ID связанных сущностей")
    void shouldMapEntityToDto() {

        UUID productId = UUID.randomUUID();
        UUID supplierId = UUID.randomUUID();
        UUID imageId = UUID.randomUUID();
        LocalDateTime now = LocalDateTime.now();

        Supplier supplier = new Supplier();
        supplier.setId(supplierId);

        Image image = new Image();
        image.setId(imageId);

        Product product = new Product()
                .setId(productId)
                .setName("Kettle")
                .setCategory(ProductCategory.KETTLE)
                .setPrice(new BigDecimal("49.99"))
                .setAvailableStock(100)
                .setLastUpdateDate(now)
                .setSupplier(supplier)
                .setImage(image);

        ProductDto dto = mapper.toDto(product);

        assertThat(dto).isNotNull();
        assertThat(dto.id()).isEqualTo(productId);
        assertThat(dto.name()).isEqualTo("Kettle");
        assertThat(dto.category()).isEqualTo(ProductCategory.KETTLE);
        assertThat(dto.price()).isEqualByComparingTo("49.99");
        assertThat(dto.availableStock()).isEqualTo(100);
        assertThat(dto.lastUpdateDate()).isEqualTo(now);
        assertThat(dto.supplierId()).isEqualTo(supplierId); // Проверка вложенного маппинга
        assertThat(dto.imageId()).isEqualTo(imageId);

    }

    @Test
    @DisplayName("Маппинг из DTO в Entity должен игнорировать ID и связи (согласно настройкам)")
    void shouldMapDtoToEntityWithIgnoredFields() {

        ProductDto dto = ProductDto.builder()
                .id(UuidCreator.getTimeOrderedEpoch())
                .name("Washing Machine")
                .category(ProductCategory.WASHING_MACHINE)
                .price(new BigDecimal("999.00"))
                .availableStock(10)
                .lastUpdateDate(LocalDateTime.now())
                .supplierId(UuidCreator.getTimeOrderedEpoch())
                .imageId(UuidCreator.getTimeOrderedEpoch())
                .build();


        Product entity = mapper.toEntity(dto);

        assertThat(entity.getId()).isNull();
        assertThat(entity.getName()).isEqualTo("Washing Machine");
        assertThat(entity.getCategory()).isEqualTo(dto.category());
        assertThat(entity.getPrice()).isEqualByComparingTo("999.00");
        assertThat(entity.getAvailableStock()).isEqualTo(dto.availableStock());
        assertThat(entity.getLastUpdateDate()).isEqualTo(dto.lastUpdateDate());
        assertThat(entity.getSupplier()).isNull();
        assertThat(entity.getImage()).isNull();
    }

    @Test
    @DisplayName("Обновление Entity должно изменять только разрешенные поля и сохранять существующие связи")
    void shouldUpdateEntityFromDto() {

        UUID productId = UuidCreator.getTimeOrderedEpoch();
        UUID supplierId = UuidCreator.getTimeOrderedEpoch();
        UUID imageId = UuidCreator.getTimeOrderedEpoch();

        Product existingProduct = new Product()
                .setId(productId)
                .setName("Old Name")
                .setSupplier(new Supplier().setId(supplierId))
                .setImage(new Image().setId(imageId));

        ProductDto dto = ProductDto.builder()
                .name("New Name")
                .price(new BigDecimal("150.00"))
                .build();

        mapper.updateEntity(dto, existingProduct);

        assertThat(existingProduct.getId()).isNotEqualTo(dto.id());
        assertThat(existingProduct.getId()).isEqualTo(productId);

        assertThat(existingProduct.getName()).isEqualTo(dto.name());
        assertThat(existingProduct.getPrice()).isEqualByComparingTo(dto.price());

        assertThat(existingProduct.getSupplier()).isNotNull();
        assertThat(existingProduct.getSupplier().getId()).isEqualTo(supplierId);
        assertThat(existingProduct.getImage()).isNotNull();
        assertThat(existingProduct.getImage().getId()).isEqualTo(imageId);
    }

    @Test
    @DisplayName("Должен возвращать null, если на вход подан null")
    void shouldReturnNullWhenSourceIsNull() {
        assertThat(mapper.toDto(null)).isNull();
        assertThat(mapper.toEntity(null)).isNull();
    }

}
