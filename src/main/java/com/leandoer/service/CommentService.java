package com.leandoer.service;

import com.leandoer.entity.ProductComment;

import java.util.List;

public interface CommentService {

    List<ProductComment> getAllByProductId(String id);

    List<ProductComment> add(ProductComment comment);
}
