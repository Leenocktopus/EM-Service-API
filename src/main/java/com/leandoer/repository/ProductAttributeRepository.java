package com.leandoer.repository;


import com.leandoer.entity.ProductAttribute;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductAttributeRepository extends JpaRepository<ProductAttribute, Long> {

    List<ProductAttribute> findAllByProductId(long productId);

    Optional<ProductAttribute> findByIdAndProductId(long attributeId, long productId);
}
