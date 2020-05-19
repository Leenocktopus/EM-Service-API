package leandoer.example.repository;

import leandoer.example.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Integer> {
    @Override
    @Query("select distinct o from Order o join fetch o.products op join fetch op.product")
    List<Order> findAll();

}
