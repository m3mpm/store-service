package org.m3mpm.storeservice.repository;

import org.m3mpm.storeservice.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ProductRepository extends JpaRepository<Product, UUID> {
}
