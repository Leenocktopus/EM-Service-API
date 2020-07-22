package com.leandoer.service;

import com.leandoer.entity.model.ProductCommentModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductCommentService {

    Page<ProductCommentModel> getAllProductComments(long productId, Pageable pageable);

    ProductCommentModel addProductComment(long productId, ProductCommentModel comment);

    ProductCommentModel getProductComment(long productId, long commentId);

    ProductCommentModel modifyProductComment(long productId, long commentId, ProductCommentModel comment);

    ProductCommentModel deleteProductComment(long productId, long commentId);


}
