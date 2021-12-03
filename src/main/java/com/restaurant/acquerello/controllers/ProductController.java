package com.restaurant.acquerello.controllers;

import com.restaurant.acquerello.dtos.ProductDTO;
import com.restaurant.acquerello.services.ProductService;
import com.restaurant.acquerello.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class ProductController {
    @Autowired
    private ProductService productService;

    @GetMapping("/products")
    public ResponseEntity<?> getAllProducts(){
        try {
            return new ResponseEntity<>(Util.makeDTO("products", productService.getAll().stream().map(ProductDTO::new).collect(Collectors.toSet())), HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>("Error en solicitud",HttpStatus.BAD_REQUEST);
        }
    }
}
