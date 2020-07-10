package com.leandoer.service;

import com.leandoer.entity.ProductComment;
import com.leandoer.entity.dto.ProductCommentDto;

import java.util.List;

public interface ProductCommentService {

    List<ProductCommentDto> getAllProductComments(long productId);

    ProductCommentDto addProductComment(long productId, ProductCommentDto comment);

    ProductCommentDto getProductComment(long productId, long commentId);

    ProductCommentDto modifyProductComment(long productId, long commentId, ProductCommentDto comment);

    ProductCommentDto deleteProductComment(long productId, long commentId);


}
