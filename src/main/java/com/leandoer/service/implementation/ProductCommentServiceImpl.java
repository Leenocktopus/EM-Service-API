package com.leandoer.service.implementation;

import com.leandoer.entity.Product;
import com.leandoer.entity.ProductComment;
import com.leandoer.entity.dto.ProductCommentDto;
import com.leandoer.exception.EntityNotFoundException;
import com.leandoer.repository.ProductCommentRepository;
import com.leandoer.service.ProductCommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductCommentServiceImpl implements ProductCommentService {

    ProductCommentRepository productCommentRepository;

    @Autowired
    public ProductCommentServiceImpl(ProductCommentRepository productCommentRepository) {
        this.productCommentRepository = productCommentRepository;
    }

    @Override
    public List<ProductCommentDto> getAllProductComments(long productId) {
        return productCommentRepository.findAllByProductProductId(productId).stream().map(ProductCommentDto::new).collect(Collectors.toList());
    }

    @Override
    public ProductCommentDto addProductComment(long productId, ProductCommentDto comment) {
        if (productCommentRepository.existsById(comment.getCommentId())){
            throw new RuntimeException();
        }
        Product id = new Product();
        id.setProductId(productId);
        ProductComment entity = comment.toProductComment();
        entity.setProduct(id);
        productCommentRepository.save(entity);
        return comment;
    }

    @Override
    public ProductCommentDto getProductComment(long productId, long commentId) {
        return new ProductCommentDto(productCommentRepository.findByCommentIdAndProductProductId(commentId, productId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Comment with id: "+commentId+" was not found for product with id: "+productId
                )));
    }

    @Override
    public ProductCommentDto modifyProductComment(long productId, long commentId, ProductCommentDto comment) {
        ProductComment selected = productCommentRepository.findByCommentIdAndProductProductId(commentId, productId)
                .orElse(new ProductComment());
        System.out.println(selected.getCommentId());
        selected.setUser(comment.getUser());
        selected.setText(comment.getText());
        selected.setScore(comment.getScore());
        selected.setDate(comment.getDate());
        Product id = new Product();
        id.setProductId(productId);
        selected.setProduct(id);
        productCommentRepository.save(selected);
        return new ProductCommentDto(selected);
    }

    @Override
    public ProductCommentDto deleteProductComment(long productId, long commentId) {
        ProductComment selected = productCommentRepository.findByCommentIdAndProductProductId(commentId, productId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Comment with id: "+commentId+" was not found for product with id: "+productId
                ));
        productCommentRepository.delete(selected);
        return new ProductCommentDto(selected);
    }
}
