package org.m3mpm.storeservice.repository;

import org.m3mpm.storeservice.entity.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SupplierRepository extends JpaRepository<Supplier, UUID> {
}
