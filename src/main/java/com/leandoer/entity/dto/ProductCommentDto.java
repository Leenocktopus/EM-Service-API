package com.leandoer.entity.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.leandoer.entity.ProductComment;
import com.leandoer.entity.Score;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProductCommentDto {

    private long id;
    private String user;
    private Score score;
    private String text;
    private Timestamp date;

    public ProductCommentDto(ProductComment productComment) {
        this.id = productComment.getId();
        this.user = productComment.getUser();
        this.score = productComment.getScore();
        this.text = productComment.getText();
        this.date = productComment.getDate();
    }

    public ProductComment toProductComment(){
        ProductComment productComment = new ProductComment();
        productComment.setId(id);
        productComment.setUser(user);
        productComment.setScore(score);
        productComment.setText(text);
        productComment.setDate(date);
        return productComment;
    }


}
