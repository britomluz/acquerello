package com.restaurant.acquerello;

import com.restaurant.acquerello.models.Order;
import com.restaurant.acquerello.models.OrderState;
import com.restaurant.acquerello.models.User;
import com.restaurant.acquerello.models.UserType;
import com.restaurant.acquerello.repositories.OrderRepository;
import com.restaurant.acquerello.repositories.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDateTime;

@SpringBootApplication
public class AcquerelloApplication {

	public static void main(String[] args) {
		SpringApplication.run(AcquerelloApplication.class, args);
	}

	@Bean
	public CommandLineRunner initData(UserRepository userRepository, OrderRepository orderRepository) {
		return (args) -> {
			Order order1 = new Order(LocalDateTime.now().minusMinutes(20), LocalDateTime.now(), OrderState.IN_PROCESS, 32.43);
			Order order2 = new Order(LocalDateTime.now().minusMinutes(10), LocalDateTime.now(), OrderState.IN_PROCESS, 11.43);

		userRepository.save(new User("Luigi", "number one", "admin@admin.com", "SafePassword1234", "Fake Street 123", 5554343L, UserType.ADMIN, "dire"));
		userRepository.save(new User("Brian", "Cuenca", "correo@user.com", "muzarelle123", "Anywhere", 5551232L, UserType.USER, "rrewe"));

		orderRepository.save(order1);
		orderRepository.save(order2);
		};
	}

}
