package com.leandoer.repository;

import com.leandoer.entity.Product;
import com.leandoer.entity.ProductComment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<ProductComment, Long> {

    List<ProductComment> getAllByProductOrderByDateDesc(Product product);

    boolean existsByProductAndUser(Product product, String user);
}
