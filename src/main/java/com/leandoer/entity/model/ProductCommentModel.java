package com.leandoer.entity.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.leandoer.entity.Product;
import com.leandoer.entity.ProductComment;
import com.leandoer.entity.Score;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
@JsonIgnoreProperties(value = {"links"}, ignoreUnknown = true)
public class ProductCommentModel extends RepresentationModel<ProductCommentModel> {

    private long id;
    private String user;
    private Score score;
    private String text;
    private Timestamp date;
    @JsonIgnore
    private long productId = 0;

    public ProductCommentModel(ProductComment productComment) {
        this.id = productComment.getId();
        this.user = productComment.getUser();
        this.score = productComment.getScore();
        this.text = productComment.getText();
        this.date = productComment.getDate();
        this.productId = productComment.getProduct().getId();
    }

    public ProductComment toProductComment() {
        ProductComment productComment = new ProductComment();
        productComment.setId(id);
        productComment.setUser(user);
        productComment.setScore(score);
        productComment.setText(text);
        productComment.setDate(date);
        Product product = new Product();
        product.setId(productId);
        productComment.setProduct(product);
        return productComment;
    }


}
