package com.leandoer.repository;


import com.leandoer.entity.ProductAttribute;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductAttributeRepository extends JpaRepository<ProductAttribute, Long> {

    List<ProductAttribute> findAllByProductId(Long productId);

    Optional<ProductAttribute> findByIdAndProductId(Long attributeId, Long productId);
}
