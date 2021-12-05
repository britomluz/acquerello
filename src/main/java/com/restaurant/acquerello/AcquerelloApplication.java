package com.restaurant.acquerello;

import com.restaurant.acquerello.models.Order;
import com.restaurant.acquerello.models.OrderState;
import com.restaurant.acquerello.models.User;
import com.restaurant.acquerello.models.UserType;
import com.restaurant.acquerello.repositories.OrderRepository;
import com.restaurant.acquerello.models.*;
import com.restaurant.acquerello.repositories.CategoryRepository;
import com.restaurant.acquerello.repositories.ProductCategoryRepository;
import com.restaurant.acquerello.repositories.ProductRepository;
import com.restaurant.acquerello.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;

@SpringBootApplication
public class AcquerelloApplication {

	@Autowired
	private PasswordEncoder passwordEncoder;

	public static void main(String[] args) {
		SpringApplication.run(AcquerelloApplication.class, args);
	}

	@Bean
	public CommandLineRunner initData(UserRepository userRepository, OrderRepository orderRepository, ProductRepository productRepository,
									  CategoryRepository categoryRepository,
									  ProductCategoryRepository productCategoryRepository) {
		return (args) -> {
			// Brian: Create User and Order
			Order order1 = new Order(LocalDateTime.now().minusMinutes(20), LocalDateTime.now(), OrderState.IN_PROCESS, 32.43);
			Order order2 = new Order(LocalDateTime.now().minusMinutes(10), LocalDateTime.now(), OrderState.IN_PROCESS, 11.43);

			userRepository.save(new User("Luigi", "number one", "admin@admin.com", passwordEncoder.encode("SafePassword1234"), "Fake Street 123", 5554343L, UserType.ADMIN, "dire"));
			userRepository.save(new User("Brian", "Cuenca", "correo@user.com", passwordEncoder.encode("muzarelle123"), "Anywhere", 5551232L, UserType.USER, "rrewe"));

			orderRepository.save(order1);
			orderRepository.save(order2);

			//GABRIEL: CREATE PRODUCTS TO TEST DE APP
			Product product1 = new Product("Pasta primavera", "tallarin con salsa roja", "hhhhh", 25D, 10);
			productRepository.save(product1);
			Product product2 = new Product("Torta de chocolate", "panque de chocolate, cubierto con salsa de chocolate", "hhh", 5D, 15);
			productRepository.save(product2);
			Product product3 = new Product("Coca-cola", "bebida de cola", "hhh", 2D, 20);
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
