package com.leandoer.service.implementation;

import com.leandoer.entity.Image;
import com.leandoer.entity.Product;
import com.leandoer.entity.model.ImageModel;
import com.leandoer.exception.EntityNotFoundException;
import com.leandoer.repository.ImageRepository;
import com.leandoer.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

@Service
public class ImageServiceImpl implements ImageService {

    ImageRepository imageRepository;

    @Autowired
    public ImageServiceImpl(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }


    @Override
    public List<ImageModel> getAllImages(Long productId) {
        return imageRepository.findImagesByProductId(productId).stream()
                .map(ImageModel::new)
                .collect(Collectors.toList());
    }

    @Override
    public ImageModel getOneImage(Long productId, Long imageId) {
        return new ImageModel(imageRepository.findImageByIdAndProductId(imageId, productId).orElseThrow(
                () -> new EntityNotFoundException("Image for with id '" + imageId + "' " +
                        "for product with id '" + productId + "' has not been found")
        ));
    }

    @Transactional
    @Override
    public ImageModel addImage(Long productId, ImageModel image) {
        Image newImage = image.toImage();
        Product product = new Product();
        product.setId(productId);
        newImage.setProduct(product);
        byte[] decodedImage = Base64.getDecoder().decode(image.getEncodedImage().getBytes(StandardCharsets.UTF_8));

        String classpath = this.getClass().getProtectionDomain().getCodeSource().getLocation().getPath().substring(1);
        classpath = new File(classpath).getParentFile().getParentFile().getPath();
        String relativePath = "/static/images/";
        String dirName = String.valueOf(productId);
        Path path = Paths.get(classpath + relativePath + dirName);

        String filename = null;
        try {
            filename = generateFilename(path, productId);
        } catch (IOException e) {
            e.printStackTrace();
        }
        newImage.setFilename(filename);

        ImageModel result = new ImageModel(imageRepository.save(newImage));
        try {
            Files.write(Paths.get(path.toString() + "/" + filename), decodedImage);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    public String generateFilename(Path path, Long productId) throws IOException {
        if (Files.notExists(path)) {
            Files.createDirectory(path);
        }
        List<Path> paths = Files.list(path).collect(Collectors.toList());

        String generated = generate();
        while (checkIfExists(paths, generated)) {
            generated = generate();
        }
        return generated + ".png";
    }

    private boolean checkIfExists(List<Path> paths, String filename) {
        for (Path p : paths) {
            if (p.getFileName().toString().equals(filename)) {
                return true;
            }
        }
        return false;
    }

    private String generate() {
        return ThreadLocalRandom.current().ints(10, 0, 10)
                .boxed().map(String::valueOf).collect(Collectors.joining());
    }


    @Override
    public ImageModel deleteImage(Long productId, Long imageId) {
        Image image = imageRepository.findImageByIdAndProductId(imageId, productId).orElseThrow(
                () -> new EntityNotFoundException("Image for with id '" + imageId + "' " +
                        "for product with id '" + productId + "' has not been found")
        );
        imageRepository.delete(image);
        return new ImageModel(image);
    }
}
