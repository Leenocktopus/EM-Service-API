package leandoer.example.service.implementation;

import leandoer.example.entity.Category;
import leandoer.example.entity.Manufacturer;
import leandoer.example.entity.Product;
import leandoer.example.exception.NoSuchResourceException;
import leandoer.example.repository.AttributeRepository;
import leandoer.example.repository.CommentRepository;
import leandoer.example.repository.ProductRepository;
import leandoer.example.service.CategoryService;
import leandoer.example.service.ManufacturerService;
import leandoer.example.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    ProductRepository productRepository;
    AttributeRepository attributeRepository;
    CommentRepository commentRepository;
    ManufacturerService manufacturerService;
    CategoryService categoryService;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository, AttributeRepository attributeRepository, CommentRepository commentRepository, ManufacturerService manufacturerService, CategoryService categoryService) {
        this.productRepository = productRepository;
        this.attributeRepository = attributeRepository;
        this.commentRepository = commentRepository;
        this.manufacturerService = manufacturerService;
        this.categoryService = categoryService;
    }

    @Override
    public List<Product> getAllProducts(Map<String, List<String>> params, Pageable pageable) {
        List<Manufacturer> manufacturerSet;
        List<Category> categorySet;
        if (params.containsKey("manufacturers")) {
            manufacturerSet = params.get("manufacturers").stream()
                    .map(item -> {
                        Manufacturer m = new Manufacturer();
                        m.setId(Integer.parseInt(item));
                        return m;
                    }).collect(Collectors.toList());
        } else {
            manufacturerSet = manufacturerService.getAll();
        }

        if (params.containsKey("categories")) {
            categorySet = params.get("categories").stream()
                    .map(item -> {
                        Category m = new Category();
                        m.setId(Integer.parseInt(item));
                        return m;
                    }).collect(Collectors.toList());
        } else {
            categorySet = categoryService.getAll();
        }


        return productRepository.findAllProducts(pageable, manufacturerSet, categorySet).getContent();
    }

    @Override
    public Product getProductById(String id) {
        return this.productRepository.findById(id).orElseThrow(() -> new NoSuchResourceException(id, "product"));
    }

    @Override
    public List<Product> findByKeyword(String keyword, Pageable pageable) {
        return this.productRepository.findAllByKeyword(keyword, pageable).getContent();
    }


}
