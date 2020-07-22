package com.leandoer.repository;

import com.leandoer.entity.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends PagingAndSortingRepository<Order, Long> {
    @Override
    @Query(value = "select distinct o from Order o join fetch o.products op join fetch op.product opp join fetch opp.manufacturer join fetch opp.category",
    countQuery = "select count(o) from Order o")
    Page<Order> findAll(Pageable pageable);

    @Override
    @Query("select distinct o from Order o join fetch o.products op join fetch op.product opp join fetch opp.manufacturer join fetch opp.category where o.id =:id")
    Optional<Order> findById(@Param("id") Long id);
}
