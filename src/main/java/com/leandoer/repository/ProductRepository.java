package com.leandoer.repository;

import com.leandoer.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ProductRepository extends PagingAndSortingRepository<Product, Long> {

    @Override
    @Query(value = "select distinct p from Product p left join fetch p.images i join fetch p.manufacturer m join fetch p.category c ",
            countQuery = "select count(p) from Product p")
    Page<Product> findAll(Pageable pageable);

    @Override
    @Query("select p from Product p join fetch p.manufacturer join fetch p.category right join fetch p.images where p.id =:id")
    Optional<Product> findById(@Param("id") Long id);

}
