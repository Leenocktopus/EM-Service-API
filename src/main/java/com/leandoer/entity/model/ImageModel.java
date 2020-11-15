package com.leandoer.entity.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.leandoer.entity.Image;
import com.leandoer.entity.Product;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

@Getter
@Setter
@NoArgsConstructor
@JsonIgnoreProperties(value = {"links"})
public class ImageModel extends RepresentationModel<ImageModel> {
    private Long id;
    private String filename;
    @JsonIgnore
    private long productId = 0;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String encodedImage;

    public ImageModel(Image image) {
        this.id = image.getId();
        this.filename = image.getFilename();
        this.productId = image.getProduct().getId();
    }

    public Image toImage() {
        Image image = new Image();
        image.setId(this.id);
        image.setFilename(this.filename);
        Product product = new Product();
        product.setId(productId);
        image.setProduct(product);
        return image;
    }

}
