package com.leandoer.repository;

import com.leandoer.entity.ProductComment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductCommentRepository extends JpaRepository<ProductComment, Long> {

    Page<ProductComment> findAllByProductId(long id, Pageable pageable);

    Optional<ProductComment> findByIdAndProductId(long commentId, long productId);
}
