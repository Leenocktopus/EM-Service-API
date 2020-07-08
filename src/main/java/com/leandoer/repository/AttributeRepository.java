package com.leandoer.repository;

import com.leandoer.entity.Product;
import com.leandoer.entity.ProductAttribute;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AttributeRepository extends JpaRepository<ProductAttribute, Long> {

    List<ProductAttribute> getAllByProduct(Product product);

    boolean existsByProductAndName(Product product, String name);
}
