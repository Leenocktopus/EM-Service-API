package com.leandoer.repository;

import com.leandoer.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {
    @Override
    @Query("select distinct o from Order o join fetch o.products op join fetch op.product opp join fetch opp.manufacturer join fetch opp.category")
    List<Order> findAll();

    @Override
    @Query("select distinct o from Order o join fetch o.products op join fetch op.product opp join fetch opp.manufacturer join fetch opp.category where o.orderId =:id")
    Optional<Order> findById(@Param("id") Long id);
}
