package com.product_Micro.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.product_Micro.Model.User;
import com.product_Micro.dto.ProductDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RedisService {

    private final RedisTemplate<String, Object> redisTemplate;
    private final ObjectMapper objectMapper;


    public void saveData(String key, String value) {
        redisTemplate.opsForValue().set(key, value);
    }

    public String getData(String key) {
        return (String) redisTemplate.opsForValue().get(key);
    }

    public void deleteData(String key) {
        redisTemplate.delete(key);
    }

    public void saveUser(String key, User user) throws JsonProcessingException {

        String userJson = objectMapper.writeValueAsString(user);
        System.out.println(userJson);
        redisTemplate.opsForValue().set(key,userJson);
        System.out.println(userJson);
    }

    public User getUser(String key) throws JsonProcessingException {
        String userJson = (String) redisTemplate.opsForValue().get(key);
        return objectMapper.readValue(userJson, User.class);
    }

    public void storeProductDtoInRedis(ProductDto productDto){

        try{
            String userDtoJson = objectMapper.writeValueAsString(productDto);
            redisTemplate.opsForValue().set(productDto.getId().toString(),userDtoJson);

        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public ProductDto findProductInRedis(Long id){
        String productDtoJson = (String)redisTemplate.opsForValue().get(id.toString());

        if(productDtoJson != null) {
            try {
                return objectMapper.readValue(productDtoJson, ProductDto.class);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        }

        return null;
    }

}
