package com.restaurant.acquerello;

import com.restaurant.acquerello.models.*;
import com.restaurant.acquerello.repositories.CategoryRepository;
import com.restaurant.acquerello.repositories.ProductCategoryRepository;
import com.restaurant.acquerello.repositories.ProductRepository;
import com.restaurant.acquerello.repositories.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class AcquerelloApplication {

	public static void main(String[] args) {
		SpringApplication.run(AcquerelloApplication.class, args);
	}

	@Bean
	public CommandLineRunner initData(UserRepository userRepository, ProductRepository productRepository,
									  CategoryRepository categoryRepository,
									  ProductCategoryRepository productCategoryRepository) {
		return (args) -> {
			User user1 = new User("Luigi", "number one", "admin@admin.com", "SafePassword1234", "Fake Street 123", 5554343L, UserType.ADMIN, "dire");
			userRepository.save(user1);
			User user2 = new User("Brian", "Cuenca", "correo@user.com", "muzarelle123", "Anywhere", 5551232L, UserType.USER, "rrewe");
			userRepository.save(user2);

			//GABRIEL: CREATE PRODUCTS TO TEST DE APP
			Product product1 = new Product("Pasta primavera","tallarin con salsa roja","aaaaaaaaaaaaaaaaaaa", "hhhhh",25D, 10);
        	productRepository.save(product1);
			Product product2 = new Product("Torta de chocolate", "panque de chocolate, cubierto con salsa de chocolate","dddddd", "hhh", 5D, 15);
			productRepository.save(product2);
			Product product3 = new Product("Coca-cola", "bebida de cola", "ccccccccccccccc", "hhh", 2D, 20);
			productRepository.save(product3);

			//GABRIEL: CREATE CATEGORIES TO TEST DE APP
			Category category1 = new Category("Pasta", "platos italianos", "hhhh");
			categoryRepository.save(category1);
			Category category2 = new Category("Postre", "todo tipo", "hhh");
			categoryRepository.save(category2);
			Category category3 = new Category("Bebida", "variedad en sabores", "hhh");
			categoryRepository.save(category3);

			//GABRIEL: CREATE PRODUCTS/CATEGORIES (MANY TO MANY) TO TEST DE APP
			ProductCategory productCategory1 = new ProductCategory(product1, category1);
			productCategoryRepository.save(productCategory1);
			ProductCategory productCategory2 = new ProductCategory(product2, category2);
			productCategoryRepository.save(productCategory2);
			ProductCategory productCategory3 = new ProductCategory(product3, category3);
			productCategoryRepository.save(productCategory3);


		};
	}

}
