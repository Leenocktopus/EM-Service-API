package leandoer.example.repository;

import leandoer.example.entity.Product;
import leandoer.example.entity.ProductAttribute;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AttributeRepository extends JpaRepository<ProductAttribute, Integer> {

    List<ProductAttribute> getAllByProduct(Product product);

    boolean existsByProductAndName(Product product, String name);
}
