package com.leandoer.repository;

import com.leandoer.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    @Override
    @Query("select c from Category c where c.parent is null")
    List<Category> findAll();

    Optional<Category> findByName(String name);

    boolean existsByName(String name);
}
