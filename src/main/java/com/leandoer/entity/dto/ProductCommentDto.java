package com.leandoer.entity.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.leandoer.entity.Product;
import com.leandoer.entity.ProductComment;
import com.leandoer.entity.Score;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProductCommentDto {

    private long commentId;
    private String user;
    private Score score;
    private String text;
    private Timestamp date;

    public ProductCommentDto(ProductComment productComment) {
        this.commentId = productComment.getCommentId();
        this.user = productComment.getUser();
        this.score = productComment.getScore();
        this.text = productComment.getText();
        this.date = productComment.getDate();
    }

    public ProductComment toProductComment(){
        ProductComment productComment = new ProductComment();
        productComment.setCommentId(commentId);
        productComment.setUser(user);
        productComment.setScore(score);
        productComment.setText(text);
        productComment.setDate(date);
        return productComment;
    }


}
