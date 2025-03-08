package com.product_Micro.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.product_Micro.Model.Product;
import com.product_Micro.Model.User;
import com.product_Micro.dto.ProductDto;
import com.product_Micro.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/redis")
public class RedisController {

    @Autowired
    private RedisService redisService;

    @GetMapping("/save")
    public String saveData(@RequestParam String key, @RequestParam String value) {
        redisService.saveData(key, value);
        return "Data saved";
    }

    @GetMapping("/get")
    public String getData(@RequestParam String key) {
        return redisService.getData(key);
    }

    @GetMapping("/delete")
    public String deleteData(@RequestParam String key) {
        redisService.deleteData(key);
        return "Data deleted";
    }

    @PostMapping("/addUser")
    public String addUser(@RequestParam String key, @RequestBody User user){
        try {
            redisService.saveUser(key, user);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return "working";
    }

    @GetMapping("/getUser")
    public User getUser(@RequestParam String key){
        try{
            return redisService.getUser(key);
        } catch (Exception e){
            return null;

        }
    }

}

