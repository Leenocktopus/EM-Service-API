package leandoer.example.controller;


import leandoer.example.entity.Product;
import leandoer.example.entity.ProductAttribute;
import leandoer.example.entity.ProductComment;
import leandoer.example.service.AttributeService;
import leandoer.example.service.CommentService;
import leandoer.example.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/products")
public class ProductController {

    CommentService commentService;

    AttributeService attributeService;

    ProductService productService;

    @Autowired
    public ProductController(ProductService productService, CommentService commentService, AttributeService attributeService) {
        this.productService = productService;
        this.commentService = commentService;
        this.attributeService = attributeService;
    }


    @GetMapping
    public List<Product> getAllProducts(@RequestParam(value = "key", required = false) String keyword,
                                        @RequestParam(value = "cat", required = false) List<String> categories,
                                        @RequestParam(value = "man", required = false) List<String> manufacturers,
                                        @PageableDefault(size = 32) Pageable pageable) {

        if (keyword != null && !keyword.isEmpty()) {
            return productService.findByKeyword(keyword, pageable);
        } else {
            Map<String, List<String>> params = new HashMap<>();
            if (categories != null && !categories.isEmpty()) {
                params.put("categories", categories);
            }
            if (manufacturers != null && !manufacturers.isEmpty()) {
                params.put("manufacturers", manufacturers);
            }

            return productService.getAllProducts(params, pageable);
        }
    }


    @GetMapping("/{id}")
    public Product getInfo(@PathVariable("id") String id) {
        return productService.getProductById(id);
    }


    @GetMapping("/{id}/comments")
    List<ProductComment> getComments(@PathVariable("id") String id) {
        return commentService.getAllByProductId(id);
    }


    @PostMapping("/{id}/comments")
    List<ProductComment> addComment(@PathVariable("id") String id,
                                    @RequestBody ProductComment comment) {
        return commentService.add(comment);
    }


    @GetMapping("/{id}/attributes")
    List<ProductAttribute> getAttributes(@PathVariable("id") String id) {
        return attributeService.getAllByProductId(id);
    }


    @PostMapping("/{id}/attributes")
    ProductAttribute addAttribute(@PathVariable("id") String id,
                                  @RequestBody ProductAttribute attribute) {
        return attributeService.add(attribute);
    }
}
