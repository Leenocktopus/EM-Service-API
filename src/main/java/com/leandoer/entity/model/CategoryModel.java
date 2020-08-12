package com.leandoer.entity.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.leandoer.entity.Category;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Getter
@Setter
@NoArgsConstructor
@JsonIgnoreProperties(value = {"links"}, ignoreUnknown = true)
public class CategoryModel extends RepresentationModel<CategoryModel> {
    private Long id;
    private String name;
    private List<CategoryModel> subCategories = new ArrayList<>();

    public CategoryModel(Category category) {
        this.id = category.getId();
        this.name = category.getName();
        this.subCategories = category.getSubCategories().stream().map(CategoryModel::new).collect(Collectors.toList());
    }

    public Category toCategory() {
        Category category = new Category();
        category.setId(this.id);
        category.setName(this.name);
        category.setSubCategories(subCategories.stream().map(CategoryModel::toCategory).collect(Collectors.toSet()));
        return category;
    }
}
