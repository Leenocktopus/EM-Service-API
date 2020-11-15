package com.leandoer.service.implementation;

import com.leandoer.entity.Product;
import com.leandoer.entity.ProductComment;
import com.leandoer.entity.Score;
import com.leandoer.entity.model.ProductCommentModel;
import com.leandoer.exception.EntityNotFoundException;
import com.leandoer.repository.ProductCommentRepository;
import com.leandoer.service.ProductCommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class ProductCommentServiceImpl implements ProductCommentService {

    ProductCommentRepository productCommentRepository;

    @Autowired
    public ProductCommentServiceImpl(ProductCommentRepository productCommentRepository) {
        this.productCommentRepository = productCommentRepository;
    }

    @Override
    public Page<ProductCommentModel> getAllProductComments(long productId, Pageable pageable) {
        return productCommentRepository.findAllByProductId(productId, pageable).map(ProductCommentModel::new);
    }

    @Override
    public ProductCommentModel addProductComment(long productId, ProductCommentModel comment) {
        if (comment.getId()!= null && productCommentRepository.existsById(comment.getId())) {
            throw new RuntimeException();
        }
        Product id = new Product();
        id.setId(productId);
        ProductComment entity = comment.toProductComment();
        entity.setProduct(id);
        return new ProductCommentModel(productCommentRepository.save(entity));
    }

    @Override
    public ProductCommentModel getProductComment(long productId, long commentId) {
        return new ProductCommentModel(productCommentRepository.findByIdAndProductId(commentId, productId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Comment with id: " + commentId + " was not found for product with id: " + productId
                )));
    }

    @Override
    public ProductCommentModel modifyProductComment(long productId, long commentId, ProductCommentModel comment) {
        ProductComment selected = productCommentRepository.findByIdAndProductId(commentId, productId)
                .orElse(new ProductComment());
        System.out.println(selected.getId());
        selected.setUser(comment.getUser());
        selected.setText(comment.getText());
        selected.setScore(Arrays.stream(Score.values())
                .filter(value -> value.getNumericValue() == comment.getScore())
                .findFirst().orElseThrow(RuntimeException::new));
        selected.setDate(comment.getDate());
        Product id = new Product();
        id.setId(productId);
        selected.setProduct(id);
        return new ProductCommentModel(productCommentRepository.save(selected));
    }

    @Override
    public ProductCommentModel deleteProductComment(long productId, long commentId) {
        ProductComment selected = productCommentRepository.findByIdAndProductId(commentId, productId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Comment with id: " + commentId + " was not found for product with id: " + productId
                ));
        productCommentRepository.delete(selected);
        return new ProductCommentModel(selected);
    }
}
