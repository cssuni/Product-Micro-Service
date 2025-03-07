package com.product_Micro.service.image;

import com.product_Micro.Model.Image;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface IImageService {


    void save(List<MultipartFile> files, Long productId) throws IOException;


    Image findById(int id);


    void updateImage(MultipartFile file, int id) throws IOException;



    void deleteImage(int id);
}
