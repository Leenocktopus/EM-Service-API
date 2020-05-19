package leandoer.example.service.implementation;

import leandoer.example.entity.ProductComment;
import leandoer.example.exception.DuplicateSaveException;
import leandoer.example.repository.CommentRepository;
import leandoer.example.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {
    CommentRepository commentRepository;
    ProductServiceImpl productService;

    @Autowired
    public CommentServiceImpl(CommentRepository commentRepository, ProductServiceImpl productService) {
        this.commentRepository = commentRepository;
        this.productService = productService;
    }

    @Override
    public List<ProductComment> getAllByProductId(String id) {
        return commentRepository.getAllByProductOrderByDateDesc(productService.getProductById(id));
    }

    @Override
    public List<ProductComment> add(ProductComment comment) {
        boolean isPresent = commentRepository.existsByProductAndUser(comment.getProduct(), comment.getUser());
        if (isPresent) {
            throw new DuplicateSaveException("comment");
        }
        commentRepository.save(comment);
        return commentRepository.getAllByProductOrderByDateDesc(comment.getProduct());
    }
}
