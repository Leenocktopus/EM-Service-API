package leandoer.example.service;

import leandoer.example.entity.ProductAttribute;

import java.util.List;

public interface AttributeService {
    List<ProductAttribute> getAllByProductId(String id);

    ProductAttribute add(ProductAttribute attribute);
}
