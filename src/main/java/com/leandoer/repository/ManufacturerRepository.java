package com.leandoer.repository;

import com.leandoer.entity.Manufacturer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ManufacturerRepository extends JpaRepository<Manufacturer, Long> {

    boolean existsById(Manufacturer manufacturer);
}
