package com.leandoer.repository;

import com.leandoer.entity.ProductComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ProductCommentRepository extends JpaRepository<ProductComment, Long> {

    List<ProductComment> findAllByProductId(long id);

    Optional<ProductComment> findByIdAndProductId(long commentId, long productId);
}
