package com.product_Micro.controller;



import com.product_Micro.Model.Image;
import com.product_Micro.dto.ImageDto;
import com.product_Micro.response.ApiResponse;
import com.product_Micro.service.image.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("${api.prefix}/Images")
@RequiredArgsConstructor
public class ImageController {


    private final ImageService imageService;

    @PostMapping("/addImage")
    public ResponseEntity<ApiResponse> saveImage(@RequestParam List<MultipartFile> files, @RequestParam Long productId) throws IOException {
        try{
            imageService.save(files, productId);
            return ResponseEntity.ok(new ApiResponse("Image Saved Successfully", null));
        }catch (Exception e){
            return ResponseEntity.ok(new ApiResponse(e.getMessage(), null));
        }
    }

    @GetMapping("/download/{id}")
    public ResponseEntity<Resource> getImageById(@PathVariable int id) {
        Image image = imageService.findById(id);
        Resource imageResource = new ByteArrayResource(image.getData());
        String type = image.getType();

        HttpHeaders headers = new HttpHeaders();
        // Setting the Type in the header
        headers.setContentType(MediaType.parseMediaType(type)); // Change to the appropriate image type if needed
       // returning the type and the image data
        return ResponseEntity.ok() .headers(headers) .body(imageResource);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse> deleteImage(@PathVariable int id) {

        try {
            imageService.deleteImage(id);
            return ResponseEntity.ok(new ApiResponse("Image deleted successfully", null));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ApiResponse(e.getMessage(), null));
        }
    }

    @PutMapping("/update")
    public ResponseEntity<ApiResponse> updateImage(@RequestParam MultipartFile file, @RequestParam int imageId) throws IOException {
        try {
            imageService.updateImage(file, imageId);
            return ResponseEntity.ok(new ApiResponse("Image Updated Successfully", null));
        } catch (Exception e) {
            return ResponseEntity.ok(new ApiResponse(e.getMessage(), null));
        }
    }

        @GetMapping("/productImages/{productId}")
        public ResponseEntity<ApiResponse> getImageById(@PathVariable Long productId) {
        List<ImageDto> image = imageService.getProductImages(productId);
        return ResponseEntity.ok(new ApiResponse("Image found successfully", image));
    }

}
