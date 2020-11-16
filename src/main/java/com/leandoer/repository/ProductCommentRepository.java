package com.leandoer.repository;

import com.leandoer.entity.ProductComment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductCommentRepository extends JpaRepository<ProductComment, Long> {

    Page<ProductComment> findAllByProductId(Long id, Pageable pageable);

    Optional<ProductComment> findByIdAndProductId(Long commentId, Long productId);

    boolean existsByProductIdAndUser(Long productId, String user);

    void deleteProductCommentByProductId(Long productId);
}
