package com.restaurant.acquerello.controllers;

import com.restaurant.acquerello.dtos.CategoryDTO;
import com.restaurant.acquerello.services.CategoryService;
import com.restaurant.acquerello.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.stream.Collectors;

//CREATED BY GABRIEL

@RestController
@RequestMapping("/api")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/categories")
    public ResponseEntity<?> getAllCategories(){
        try {
            return new ResponseEntity<>(Util.makeDTO("categories", categoryService.getAll().stream().map(CategoryDTO::new).collect(Collectors.toList())), HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>("Error en solicitud",HttpStatus.BAD_REQUEST);
        }
    }
}
