package org.m3mpm.storeservice;

import com.github.f4b6a3.uuid.UuidCreator;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.m3mpm.storeservice.dto.AddressDto;
import org.m3mpm.storeservice.dto.ProductDto;
import org.m3mpm.storeservice.dto.SupplierDto;
import org.m3mpm.storeservice.entity.Address;
import org.m3mpm.storeservice.entity.Image;
import org.m3mpm.storeservice.entity.Product;
import org.m3mpm.storeservice.entity.Supplier;
import org.m3mpm.storeservice.mapper.SupplierMapper;
import org.m3mpm.storeservice.type.ProductCategory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = SupplierMapperTest.TestConfig.class)
public class SupplierMapperTest {

    @Configuration
    @ComponentScan(basePackages = "org.m3mpm.storeservice.mapper")
    static class TestConfig {}

    @Autowired
    private SupplierMapper supplierMapper;

    @Test
    @DisplayName("Маппинг из Entity в DTO")
    void shouldMapEntityToDto() {

        UUID imageId0 = UuidCreator.getTimeOrderedEpoch();
        UUID imageId1 = UuidCreator.getTimeOrderedEpoch();
        UUID addressId = UuidCreator.getTimeOrderedEpoch();
        UUID supplierId = UuidCreator.getTimeOrderedEpoch();
        UUID productId0 = UuidCreator.getTimeOrderedEpoch();
        UUID productId1 = UuidCreator.getTimeOrderedEpoch();

        Image image0 = new Image();
        image0.setId(imageId0);

        Image image1 = new Image();
        image1.setId(imageId1);

        Address addressSupplier = new Address().
                setId(addressId).
                setCountry("Russia").
                setCity("Moscow").
                setStreet("Lenina");

        Supplier supplier = new Supplier()
                .setId(supplierId)
                .setName("ООО Ромашка")
                .setPhoneNumber("+79991234567")
                .setAddress(addressSupplier);


        Product product0 = new Product()
                .setId(productId0)
                .setName("Kettle")
                .setCategory(ProductCategory.KETTLE)
                .setPrice(new BigDecimal("49.99"))
                .setAvailableStock(100)
                .setLastUpdateDate(LocalDateTime.now())
                .setSupplier(supplier)
                .setImage(image0);

        Product product1 = new Product()
                .setId(productId1)
                .setName("Washing Machine")
                .setCategory(ProductCategory.WASHING_MACHINE)
                .setPrice(new BigDecimal("999.00"))
                .setAvailableStock(10)
                .setLastUpdateDate(LocalDateTime.now())
                .setSupplier(supplier)
                .setImage(image1);

        List<Product> listOfProducts = List.of(product0,product1);
        supplier.setProducts(listOfProducts);

        SupplierDto dto = supplierMapper.toDto(supplier);

        assertThat(dto).isNotNull();
        assertThat(dto.id()).isEqualTo(supplierId);
        assertThat(dto.name()).isEqualTo("ООО Ромашка");
        assertThat(dto.phoneNumber()).isEqualTo(supplier.getPhoneNumber());
        assertThat(dto.address().getId()).isEqualTo(addressId);
        assertThat(dto.products().size()).isEqualTo(2);
    }

    @Test
    @DisplayName("Маппинг DTO в Entity: игнорирование ID и даты регистрации")
    void shouldMapDtoToEntityAndIgnoreId() {

        UUID supplierId = UuidCreator.getTimeOrderedEpoch();
        UUID addressId = UuidCreator.getTimeOrderedEpoch();

        AddressDto addressDto = new AddressDto().
                setId(addressId).
                setCountry("Russia").setCity("SPb").
                setStreet("Nevsky");

        ProductDto productDto = ProductDto.builder()
                .id(UuidCreator.getTimeOrderedEpoch())
                .name("Washing Machine")
                .category(ProductCategory.WASHING_MACHINE)
                .price(new BigDecimal("999.00"))
                .availableStock(10)
                .lastUpdateDate(LocalDateTime.now())
                .supplierId(supplierId)
                .imageId(UuidCreator.getTimeOrderedEpoch())
                .build();

        SupplierDto dto = SupplierDto.builder()
                .id(supplierId)
                .name("ТехноСнаб")
                .phoneNumber("88005553535")
                .address(addressDto)
                .products(List.of(productDto))
                .build();

        Supplier entity = supplierMapper.toEntity(dto);

        assertThat(entity.getId()).isNull();
        assertThat(entity.getName()).isEqualTo("ТехноСнаб");
        assertThat(entity.getAddress()).isNotNull();
        assertThat(entity.getProducts().size()).isEqualTo(1);
    }

    @Test
    @DisplayName("Должен обновлять существующую Entity из DTO")
    void shouldUpdateEntityFromDto() {

        // 1. создание существующего в БД Supplier
        UUID imageId0 = UuidCreator.getTimeOrderedEpoch();
        UUID imageId1 = UuidCreator.getTimeOrderedEpoch();
        UUID existingAddressId = UuidCreator.getTimeOrderedEpoch();
        UUID existingSupplierId = UuidCreator.getTimeOrderedEpoch();
        UUID productId0 = UuidCreator.getTimeOrderedEpoch();
        UUID productId1 = UuidCreator.getTimeOrderedEpoch();

        Image image0 = new Image();
        image0.setId(imageId0);

        Image image1 = new Image();
        image1.setId(imageId1);

        Address existingAddress = new Address().
                setId(existingAddressId).
                setCountry("Russia").
                setCity("Moscow").
                setStreet("Lenina");

        Supplier existingSupplier = new Supplier()
                .setId(existingSupplierId)
                .setName("ООО Ромашка")
                .setPhoneNumber("+79991234567")
                .setAddress(existingAddress);


        Product product0 = new Product()
                .setId(productId0)
                .setName("Kettle")
                .setCategory(ProductCategory.KETTLE)
                .setPrice(new BigDecimal("49.99"))
                .setAvailableStock(100)
                .setLastUpdateDate(LocalDateTime.now())
                .setSupplier(existingSupplier)
                .setImage(image0);

        Product product1 = new Product()
                .setId(productId1)
                .setName("Washing Machine")
                .setCategory(ProductCategory.WASHING_MACHINE)
                .setPrice(new BigDecimal("999.00"))
                .setAvailableStock(10)
                .setLastUpdateDate(LocalDateTime.now())
                .setSupplier(existingSupplier)
                .setImage(image1);

        List<Product> listOfProducts = new ArrayList<>();
        listOfProducts.add(product0);
        listOfProducts.add(product1);
        existingSupplier.setProducts(listOfProducts);


        // 2. создание нового Supplier
        UUID newAddressId = UuidCreator.getTimeOrderedEpoch();

        AddressDto addressDto = new AddressDto().
                setId(newAddressId).
                setCountry("Russia").setCity("SPb").
                setStreet("Nevsky");

        ProductDto productDto = ProductDto.builder()
                .id(UuidCreator.getTimeOrderedEpoch())
                .name("Washing Machine")
                .category(ProductCategory.WASHING_MACHINE)
                .price(new BigDecimal("999.00"))
                .availableStock(10)
                .lastUpdateDate(LocalDateTime.now())
                .supplierId(existingSupplierId)
                .imageId(UuidCreator.getTimeOrderedEpoch())
                .build();

        SupplierDto dto = SupplierDto.builder()
                .id(existingSupplierId)
                .name("ТехноСнаб")
                .phoneNumber("88005553535")
                .address(addressDto)
                .products(List.of(productDto))
                .build();

        // 3. обновление Supplier
        supplierMapper.updateEntity(dto,existingSupplier);

        // 4. проверка
        assertThat(existingSupplier.getId()).isEqualTo(existingSupplierId);
        assertThat(existingSupplier.getName()).isEqualTo("ТехноСнаб");
        assertThat(existingSupplier.getPhoneNumber()).isEqualTo("88005553535");

        assertThat(existingSupplier.getAddress().getId()).isEqualTo(existingAddressId);
        assertThat(existingSupplier.getAddress().getCountry()).isEqualTo("Russia");
        assertThat(existingSupplier.getAddress().getCity()).isEqualTo("SPb");
        assertThat(existingSupplier.getAddress().getStreet()).isEqualTo("Nevsky");

        assertThat(existingSupplier.getProducts().size()).isEqualTo(1);
        assertThat(existingSupplier.getProducts().getFirst().getName()).isEqualTo("Washing Machine");
        Assertions.assertThat(existingSupplier.getProducts().getFirst().getPrice()).isEqualByComparingTo("999.00");
        assertThat(existingSupplier.getProducts().getFirst().getAvailableStock()).isEqualTo(10);

    }
}
