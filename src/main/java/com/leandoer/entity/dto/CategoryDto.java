package com.leandoer.entity.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.leandoer.entity.Category;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;


@Getter
@Setter
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class CategoryDto {
    private long id;
    private String name;

    public CategoryDto(Category category) {
        this.id = category.getId();
        this.name = category.getName();
    }

    public Category toCategory(){
        Category category = new Category();
        category.setId(this.id);
        category.setName(this.name);
        return category;
    }
}
