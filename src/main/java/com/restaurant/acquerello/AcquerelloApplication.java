package com.restaurant.acquerello;

import com.restaurant.acquerello.models.Order;
import com.restaurant.acquerello.models.OrderState;
import com.restaurant.acquerello.models.User;
import com.restaurant.acquerello.models.UserType;
import com.restaurant.acquerello.repositories.*;
import com.restaurant.acquerello.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.List;

@SpringBootApplication
public class AcquerelloApplication {

	@Autowired
	private PasswordEncoder passwordEncoder;

	public static void main(String[] args) {
		SpringApplication.run(AcquerelloApplication.class, args);
	}

	@Bean
	public CommandLineRunner initData(UserRepository userRepository, OrderRepository orderRepository, OrderDetailsRepository orderDetailsRepository, ProductRepository productRepository,
									  CategoryRepository categoryRepository,
									  ProductCategoryRepository productCategoryRepository) {
		return (args) -> {
			// Brian: Create User and Order
			User user1 = new User("Luigi", "number one", "admin@admin.com", passwordEncoder.encode("SafePassword1234"), List.of("Fake Street 123"), 5554343L, UserType.ADMIN, "https://discord.com/channels/913867704660787211/913868768881553468/917153794817683456");
			User user2 = new User("Brian", "Cuenca", "correo@user.com", passwordEncoder.encode("muzarelle123"), List.of("Hills 123"), 5551232L, UserType.USER, "rrewe");


			// Brian: Creating orders
			Order order1 = new Order(LocalDateTime.now().minusMinutes(20), LocalDateTime.now(), OrderState.IN_PROCESS, 32.43);
			Order order2 = new Order(LocalDateTime.now().minusMinutes(10), LocalDateTime.now(), OrderState.IN_PROCESS, 11.43);

			// Brian : Creating order details
			OrderDetails orderDetails1 = new OrderDetails("Pancho a la muzarelle", 1, 15.99, 15.99);
			OrderDetails orderDetails2 = new OrderDetails("Champiñone en caja", 2, 29.99, 29.99);

			// Brian : Adding order details to orders
			orderDetails1.addOrders(order1);
			orderDetails2.addOrders(order2);

			// Brian: Adding orders to Users
			user1.addOrder(order1);
			user2.addOrder(order2);

			userRepository.save(user1);
			userRepository.save(user2);

			orderDetailsRepository.save(orderDetails1);
			orderDetailsRepository.save(orderDetails2);

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
