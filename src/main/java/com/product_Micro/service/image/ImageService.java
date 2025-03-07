package com.product_Micro.service.image;


import com.product_Micro.Model.Image;
import com.product_Micro.Model.Product;
import com.product_Micro.dto.ImageDto;
import com.product_Micro.repository.ImageRepository;
import com.product_Micro.service.product.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.modelmapper.ModelMapper;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ImageService implements IImageService {

    private final ImageRepository imageRepository;
    private  final ProductService productService;
    private final ModelMapper modelMapper;


    public void save(List<MultipartFile> files, Long productId) throws IOException {

        Product product = productService.getProductById(productId);

        for (MultipartFile file : files) {
            Image image = new Image();
            image.setName(file.getOriginalFilename());
            image.setData(file.getBytes());
            image.setType(file.getContentType());
            image.setProduct(product);
            Image SavedImage = imageRepository.save(image);
            SavedImage.setDownloadUrl( "http://localhost:8080/api/v2/Images/download/"+SavedImage.getId());
            imageRepository.save(SavedImage);
        }
    }

    @Override
    public Image findById(int id) {
        return imageRepository.findById(Math.toIntExact(id)).orElseThrow(() -> new RuntimeException("Image not found"));
    }

    @Override
    public void updateImage(MultipartFile file, int id) throws IOException {
        Image image = findById(id);
        image.setName(file.getOriginalFilename());
        image.setData(file.getBytes());
        image.setType(file.getContentType());
        imageRepository.save(image);
    }

    @Override
    public void deleteImage(int id) {
        imageRepository.deleteById((int) id);
    }

    public List<ImageDto> getProductImages(Long productId) {
        Product product = productService.getProductById(productId);
        List<Image> images =  imageRepository.findByProduct(product);

        return images.stream().map(this::convertToDto).toList();

    }

    public ImageDto convertToDto(Image image) {
        return modelMapper.map(image, ImageDto.class);
    }

}
