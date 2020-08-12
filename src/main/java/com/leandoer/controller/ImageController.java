package com.leandoer.controller;

import com.leandoer.assembler.ImageAssembler;
import com.leandoer.entity.model.ImageModel;
import com.leandoer.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/products/")
public class ImageController {

    ImageService imageService;
    ImageAssembler assembler;

    @Autowired
    public ImageController(ImageService imageService, ImageAssembler assembler) {
        this.imageService = imageService;
        this.assembler = assembler;
    }


    @GetMapping("/{productId}/images")
    public CollectionModel<RepresentationModel<ImageModel>> getAllImages(@PathVariable("productId") Long productId) {
        return assembler.toCollectionModel(imageService.getAllImages(productId));
    }

    @GetMapping("/{productId}/images/{imageId}")
    public RepresentationModel<ImageModel> getImage(@PathVariable("productId") Long productId,
                                                    @PathVariable("imageId") Long imageId) {
        return assembler.toModel(imageService.getOneImage(productId, imageId));
    }

    @PostMapping("/{productId}/images")
    public RepresentationModel<ImageModel> addImage(@PathVariable("productId") Long productId,
                                                    @RequestBody ImageModel image) {
        return assembler.toModel(imageService.addImage(productId, image));
    }


    @DeleteMapping("/{productId}/images/{imageId}")
    public RepresentationModel<ImageModel> deleteImage(@PathVariable("productId") Long productId,
                                                       @PathVariable("imageId") Long imageId) {
        return assembler.toModel(imageService.deleteImage(productId, imageId));
    }


}
