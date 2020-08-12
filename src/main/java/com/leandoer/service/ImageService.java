package com.leandoer.service;

import com.leandoer.entity.model.ImageModel;

import java.util.List;

public interface ImageService {

    List<ImageModel> getAllImages(Long productId);

    ImageModel getOneImage(Long productId, Long imageId);

    ImageModel addImage(Long productId, ImageModel image);


    ImageModel deleteImage(Long productId, Long imageId);
}
