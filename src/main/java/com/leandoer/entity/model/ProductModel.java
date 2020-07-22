package com.leandoer.entity.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.leandoer.entity.Product;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@JsonIgnoreProperties(value = {"links"}, ignoreUnknown = true)
public class ProductModel extends RepresentationModel<ProductModel> {
    private long id;
    private String name;
    // Default values added to reduce the number of fields needed to create an Order com.leandoer.entity.Order
    // Since each product could be identified by its id, there is no need to pass any additional info
    private ManufacturerModel manufacturer = new ManufacturerModel();
    private CategoryModel category = new CategoryModel();
    private BigDecimal price;
    private int amountInStock;
    private String descr;
    private int popularity;
    private float totalScore;

    public ProductModel(Product product) {
        this.id = product.getId();
        this.name = product.getName();
        this.manufacturer = new ManufacturerModel(product.getManufacturer());
        this.category = new CategoryModel(product.getCategory());
        this.price = product.getPrice();
        this.amountInStock = product.getAmountInStock();
        this.descr = product.getDescr();
        this.popularity = product.getPopularity();
        this.totalScore = product.getTotalScore();
    }

    public Product toProduct() {
        Product product = new Product();
        product.setId(this.id);
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
