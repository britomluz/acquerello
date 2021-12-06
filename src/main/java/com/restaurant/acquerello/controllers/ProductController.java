package com.restaurant.acquerello.controllers;

import com.restaurant.acquerello.dtos.CreateProductDTO;
import com.restaurant.acquerello.dtos.ModifyProductDTO;
import com.restaurant.acquerello.dtos.ProductDTO;
import com.restaurant.acquerello.models.*;
import com.restaurant.acquerello.services.CategoryService;
import com.restaurant.acquerello.services.ProductCategoryService;
import com.restaurant.acquerello.services.ProductService;
import com.restaurant.acquerello.services.UserService;
import com.restaurant.acquerello.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;

//CREATED BY GABRIEL

@RestController
@RequestMapping("/api")
public class ProductController {
    @Autowired
    private ProductService productService;

    @Autowired
    private UserService userServices;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ProductCategoryService productCategoryServices;

    @GetMapping("/products")
    public ResponseEntity<?> getAllProducts(){
        try {
            return new ResponseEntity<>(Util.makeDTO("products", productService.getAll().stream().map(ProductDTO::new).collect(Collectors.toSet()).stream().sorted(Comparator.comparing(ProductDTO::getId)).collect(Collectors.toCollection(LinkedHashSet::new))), HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>("Error in request",HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/products/create")
    public ResponseEntity<?> creteProduct(Authentication authentication, @RequestBody CreateProductDTO createProductDTO){
        User user = userServices.getByEmail(authentication.getName());
        Category category = categoryService.getById(createProductDTO.getIdCategory()).orElse(null);
        if (!user.getType().equals(UserType.ADMIN)){
            return new ResponseEntity<>("Don't have authority",HttpStatus.FORBIDDEN);
        }
        if (category == null){
            return new ResponseEntity<>("Category doesn't exist",HttpStatus.FORBIDDEN);
        }
        if (createProductDTO.getName().isEmpty() || createProductDTO.getDescription().isEmpty() ||
                createProductDTO.getProductImage().isEmpty() ||  createProductDTO.getPrice().toString().isEmpty()
                || createProductDTO.getStock().toString().isEmpty()){
            return new ResponseEntity<>("Error in data",HttpStatus.FORBIDDEN);
        }
        if (createProductDTO.getPrice() <= 0){
            return new ResponseEntity<>("Price cannot be 0 or less",HttpStatus.FORBIDDEN);
        }
        if (createProductDTO.getStock() <= 0){
            return new ResponseEntity<>("Stock cannot be 0 or less",HttpStatus.FORBIDDEN);
        }
        Product product = new Product(createProductDTO.getName(), createProductDTO.getDescription(), createProductDTO.getProductImage(), createProductDTO.getPrice(), createProductDTO.getStock());
        productService.save(product);
        ProductCategory productCategory = new ProductCategory(product, category);
        productCategoryServices.save(productCategory);
        return new ResponseEntity<>("Product created",HttpStatus.CREATED);
    }

    @PatchMapping("/products/edit/{id}")
    public ResponseEntity<?> editProduct(Authentication authentication, @PathVariable("id") Long id,
                                         @RequestBody ModifyProductDTO modifyProductDTO){
        User user = userServices.getByEmail(authentication.getName());
        if (!user.getType().equals(UserType.ADMIN)){
            return new ResponseEntity<>("Don't have authority",HttpStatus.FORBIDDEN);
        }
        if (modifyProductDTO.getName().isEmpty() || modifyProductDTO.getDescription().isEmpty() ||
        modifyProductDTO.getProductImage().isEmpty() || modifyProductDTO.getPrice().toString().isEmpty()
        || modifyProductDTO.getStock().toString().isEmpty()){
            return new ResponseEntity<>("Error in data",HttpStatus.FORBIDDEN);
        }
        if (modifyProductDTO.getStock() < 0){
            return new ResponseEntity<>("Stock cannot be less than 0",HttpStatus.FORBIDDEN);
        }
        if (modifyProductDTO.getPrice() <= 0){
            return new ResponseEntity<>("Price cannot be 0 or less",HttpStatus.FORBIDDEN);
        }
        Product product = productService.getById(id).orElse(null);
        if (product == null){
            return new ResponseEntity<>("Product doesn't exist",HttpStatus.FORBIDDEN);
        }
        product.setName(modifyProductDTO.getName());
        product.setDescription(modifyProductDTO.getDescription());
        product.setProductImage(modifyProductDTO.getProductImage());
        product.setPrice(modifyProductDTO.getPrice());
        product.setStock(modifyProductDTO.getStock());
        productService.save(product);

        return new ResponseEntity<>("Product edited",HttpStatus.OK);
    }

    @DeleteMapping("/products/delete/{id}")
    public ResponseEntity<?> deleteProduct(Authentication authentication, @PathVariable("id") Long id){
        User user = userServices.getByEmail(authentication.getName());
        Product product = productService.getById(id).orElse(null);

        if (!user.getType().equals(UserType.ADMIN)){
            return new ResponseEntity<>("Don't have authority",HttpStatus.FORBIDDEN);
        }
        if (product == null){
            return new ResponseEntity<>("Product doesn't exist",HttpStatus.FORBIDDEN);
        }
        Set<ProductCategory> categories = product.getProductCategories();
        productCategoryServices.deleteAll(categories);
        productService.deleteProduct(id);
        return new ResponseEntity<>("Product deleted",HttpStatus.OK);
    }

}
