package leandoer.example.service.implementation;

import leandoer.example.entity.ProductAttribute;
import leandoer.example.exception.DuplicateSaveException;
import leandoer.example.repository.AttributeRepository;
import leandoer.example.service.AttributeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AttributeServiceImpl implements AttributeService {
    AttributeRepository attributeRepository;
    ProductServiceImpl productService;

    @Autowired
    public AttributeServiceImpl(AttributeRepository attributeRepository, ProductServiceImpl productService) {
        this.attributeRepository = attributeRepository;
        this.productService = productService;
    }

    @Override
    public List<ProductAttribute> getAllByProductId(String id) {
        return attributeRepository.getAllByProduct(productService.getProductById(id));
    }

    @Override
    public ProductAttribute add(ProductAttribute attribute) {
        boolean isPresent = attributeRepository.existsByProductAndName(attribute.getProduct(), attribute.getName());
        if (isPresent) {
            throw new DuplicateSaveException("attribute");
        }
        return attributeRepository.save(attribute);
    }
}
