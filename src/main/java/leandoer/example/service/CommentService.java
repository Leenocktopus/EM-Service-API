package leandoer.example.service;

import leandoer.example.entity.ProductComment;

import java.util.List;

public interface CommentService {

    List<ProductComment> getAllByProductId(String id);

    List<ProductComment> add(ProductComment comment);
}
