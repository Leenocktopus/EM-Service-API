package com.leandoer.entity.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.leandoer.entity.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProductDto {
    private long productId;
    private String name;
    // Default values added to reduce the number of data needed to create an Order com.leandoer.entity.Order
    // Since each product could be uniquely identified by its id, there is no need to pass any additional info
    private ManufacturerDto manufacturer = new ManufacturerDto();
    private CategoryDto category = new CategoryDto();
    private BigDecimal price;
    private int amountInStock;
    private String descr;
    private int popularity;
    private float totalScore;

    public ProductDto(Product product) {
        this.productId = product.getProductId();
        this.name = product.getName();
        this.manufacturer = new ManufacturerDto(product.getManufacturer());
        this.category = new CategoryDto(product.getCategory());
        this.price = product.getPrice();
        this.amountInStock = product.getAmountInStock();
        this.descr = product.getDescr();
        this.popularity = product.getPopularity();
        this.totalScore = product.getTotalScore();
    }

    public Product toProduct(){
        Product product = new Product();
        product.setProductId(this.productId);
        product.setName(this.name);
        product.setManufacturer(this.manufacturer.toManufacturer());
        product.setCategory(this.category.toCategory());
        product.setPrice(this.price);
        product.setAmountInStock(this.amountInStock);
        product.setDescr(this.descr);
        product.setPopularity(popularity);
        product.setTotalScore(this.totalScore);
        return product;
    }
}
