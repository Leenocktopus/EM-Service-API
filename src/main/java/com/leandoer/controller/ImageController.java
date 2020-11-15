package com.leandoer.controller;

import com.leandoer.assembler.ImageAssembler;
import com.leandoer.entity.model.ImageModel;
import com.leandoer.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

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
        return assembler.toCollectionModel(imageService.getAllImages(productId), productId);
    }

    @GetMapping("/{productId}/images/{imageId}")
    public RepresentationModel<ImageModel> getImage(@PathVariable("productId") Long productId,
                                                    @PathVariable("imageId") Long imageId) {
        return assembler.toModel(imageService.getOneImage(productId, imageId));
    }

    @PostMapping("/{productId}/images")
    @ResponseStatus(HttpStatus.CREATED)
    public RepresentationModel<ImageModel> addImage(HttpServletResponse response,
                                                    @PathVariable("productId") Long productId,
                                                    @RequestBody ImageModel image) {
        RepresentationModel<ImageModel> newImage = assembler.toModel(imageService.addImage(productId, image));
        response.addHeader(HttpHeaders.LOCATION, newImage.getLink("self").get().getHref());
        return newImage;
    }

    @DeleteMapping("/{productId}/images/{imageId}")
    public RepresentationModel<ImageModel> deleteImage(@PathVariable("productId") Long productId,
                                                       @PathVariable("imageId") Long imageId) {
        return assembler.toModel(imageService.deleteImage(productId, imageId));
    }

}
