package com.leandoer.repository;

import com.leandoer.entity.Category;
import com.leandoer.entity.Manufacturer;
import com.leandoer.entity.Product;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.Optional;

public interface ProductRepository extends PagingAndSortingRepository<Product, String> {


    @Query("select p from Product p join fetch p.manufacturer join fetch p.category where p.manufacturer in :manufacturers and p.category in :categories")
    Slice<Product> findAllProducts(Pageable pageable, Collection<Manufacturer> manufacturers, Collection<Category> categories);

    @Override
    @Query("select p from Product p join fetch p.manufacturer join fetch p.category where p.productId =:id")
    Optional<Product> findById(@Param("id") String s);

    @Query("select p from Product p " +
            "join fetch p.manufacturer " +
            "join fetch p.category " +
            "where p.name like %:word% OR p.descr like %:word% OR p.manufacturer.name like %:word% OR p.category.name like %:word%")
    Slice<Product> findAllByKeyword(@Param("word") String keyword, Pageable pageable);

}
