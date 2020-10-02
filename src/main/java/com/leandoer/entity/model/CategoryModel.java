package com.leandoer.entity.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
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

    public CategoryModel(Category category) {
        this.id = category.getId();
        this.name = category.getName();
    }

    public Category toCategory() {
        Category category = new Category();
        category.setId(this.id);
        category.setName(this.name);
        return category;
    }
}
