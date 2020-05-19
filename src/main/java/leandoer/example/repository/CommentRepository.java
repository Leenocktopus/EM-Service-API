package leandoer.example.repository;

import leandoer.example.entity.Product;
import leandoer.example.entity.ProductComment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<ProductComment, Integer> {

    List<ProductComment> getAllByProductOrderByDateDesc(Product product);

    boolean existsByProductAndUser(Product product, String user);
}
