package com.leandoer.repository;

import com.leandoer.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends PagingAndSortingRepository<Product, Long> {

    @Query(value = "select distinct p from Product p left join fetch p.images i join fetch p.manufacturer m join fetch p.category c where p.category.id not in :categories and p.manufacturer.id not in :manufacturers",
            countQuery = "select count(p) from Product p")
    Page<Product> findAll(Pageable pageable, @Param("categories") List<Long> categories, @Param("manufacturers") List<Long> manufacturers);

    @Query(value = "select distinct p from Product p left join fetch p.images i join fetch p.manufacturer m join fetch p.category c where p.name like %:str% OR p.descr like %:str% OR p.manufacturer.name like %:str% OR p.category.name like %:str% OR CONCAT(p.id, '') like %:str%",
            countQuery = "select count(p) from Product p")
    Page<Product> findAllByKeyword(Pageable pageable, @Param("str") String searchString);

    @Override
    @Query("select distinct p from Product p left join fetch p.images i join fetch p.manufacturer m join fetch p.category c where p.id =:id")
    Optional<Product> findById(@Param("id") Long id);

}
