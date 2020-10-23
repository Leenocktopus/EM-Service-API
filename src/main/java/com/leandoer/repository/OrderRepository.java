package com.leandoer.repository;

import com.leandoer.entity.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface OrderRepository extends PagingAndSortingRepository<Order, Long> {
    @Override
    @Query(value = "select distinct o from Order o left join fetch o.products op join fetch op.product p join fetch p.images i join fetch p.manufacturer m join fetch p.category c",
            countQuery = "select count(o) from Order o")
    Page<Order> findAll(Pageable pageable);

    @Query(value = "select distinct o from Order o join fetch o.products op join fetch op.product opp left join fetch opp.images join fetch opp.manufacturer join fetch opp.category where o.customerName like %:str% OR o.customerPhone like %:str% OR o.customerEmail like %:str% OR CONCAT(o.orderStatus, '') like %:str% OR CONCAT(o.id, '') like %:str% order by o.date desc",
            countQuery = "select count(o) from Order o")
    Page<Order> findAllByKeyword(Pageable pageable, @Param("str") String searchString);

    @Override
    @Query("select distinct o from Order o join fetch o.products op join fetch op.product opp left join fetch opp.images join fetch opp.manufacturer join fetch opp.category where o.id =:id")
    Optional<Order> findById(@Param("id") Long id);

}
