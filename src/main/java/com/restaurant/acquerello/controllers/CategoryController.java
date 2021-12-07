package com.restaurant.acquerello.controllers;

import com.restaurant.acquerello.dtos.CategoryDTO;
import com.restaurant.acquerello.dtos.CreateCategoryDTO;
import com.restaurant.acquerello.dtos.ModifyCategoryDTO;
import com.restaurant.acquerello.models.Category;
import com.restaurant.acquerello.models.ProductCategory;
import com.restaurant.acquerello.models.User;
import com.restaurant.acquerello.models.UserType;
import com.restaurant.acquerello.services.CategoryService;
import com.restaurant.acquerello.services.ProductCategoryService;
import com.restaurant.acquerello.services.UserService;
import com.restaurant.acquerello.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Set;
import java.util.stream.Collectors;

//CREATED BY GABRIEL

@RestController
@RequestMapping("/api")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private UserService userService;

    @Autowired
    private ProductCategoryService productCategoryService;

    @GetMapping("/categories")
    public ResponseEntity<?> getAllCategories(){
        try {
            return new ResponseEntity<>(Util.makeDTO("categories", categoryService.getAll().stream().map(CategoryDTO::new).collect(Collectors.toList())), HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>("Error en solicitud",HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/categories/create")
    public ResponseEntity<?> createCategory(Authentication authentication, @RequestBody CreateCategoryDTO category){
        User user = userService.getByEmail(authentication.getName());
        if (!user.getType().equals(UserType.ADMIN)){
            return new ResponseEntity<>("Don't have authority",HttpStatus.FORBIDDEN);
        }
        if (category.getName().isEmpty() || category.getDescription().isEmpty() || category.getCategoryImage().isEmpty()){
            return new ResponseEntity<>("Fields cannot be empty", HttpStatus.FORBIDDEN);
        }
        Category category1 = new Category(category.getName(), category.getDescription(), category.getCategoryImage());
        categoryService.save(category1);
        return new ResponseEntity<>("Category created",HttpStatus.CREATED);
    }

    @PatchMapping("/categories/edit/{id}")
    public ResponseEntity<?> editCategory(Authentication authentication, @RequestBody ModifyCategoryDTO category,
                                          @PathVariable("id") Long id){
        User user = userService.getByEmail(authentication.getName());
        Category category1 = categoryService.getById(id).orElse(null);
        if (!user.getType().equals(UserType.ADMIN)){
            return new ResponseEntity<>("Don't have authority",HttpStatus.UNAUTHORIZED);
        }
        if (category.getName().isEmpty() || category.getDescription().isEmpty() || category.getCategoryImage().isEmpty()){
            return new ResponseEntity<>("Fields cannot be empty", HttpStatus.FORBIDDEN);
        }
        if (category1 == null){
            return new ResponseEntity<>("Category doesn't exist", HttpStatus.FORBIDDEN);
        }
        category1.setName(category.getName());
        category1.setDescription(category.getDescription());
        category1.setCategoryImage(category.getCategoryImage());
        categoryService.save(category1);

        return new ResponseEntity<>("Category edited",HttpStatus.OK);
    }

    @DeleteMapping("/categories/delete/{id}")
    public ResponseEntity<?> deleteCategory(Authentication authentication, @PathVariable("id") Long id){
        User user = userService.getByEmail(authentication.getName());
        Category category = categoryService.getById(id).orElse(null);
        if (!user.getType().equals(UserType.ADMIN)){
            return new ResponseEntity<>("Don't have authority",HttpStatus.UNAUTHORIZED);
        }
        if (category == null){
            return new ResponseEntity<>("Category doesn't exist",HttpStatus.FORBIDDEN);
        }

        Set<ProductCategory> categories = category.getProductCategories();
        productCategoryService.deleteAll(categories);
        categoryService.deleteCategory(id);

        return new ResponseEntity<>("Category deleted",HttpStatus.OK);
    }
}
