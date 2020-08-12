package com.leandoer.repository;

import com.leandoer.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ImageRepository extends JpaRepository<Image, Long> {

    List<Image> findImagesByProductId(Long productId);

    Optional<Image> findImageByIdAndProductId(Long imageId, Long productId);

}
