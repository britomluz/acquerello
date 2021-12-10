package com.restaurant.acquerello;

import com.restaurant.acquerello.models.Order;
import com.restaurant.acquerello.models.OrderState;
import com.restaurant.acquerello.models.User;
import com.restaurant.acquerello.models.UserType;
import com.restaurant.acquerello.repositories.*;
import com.restaurant.acquerello.models.*;
import com.restaurant.acquerello.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@SpringBootApplication
public class AcquerelloApplication {

	@Autowired
	private PasswordEncoder passwordEncoder;

	public static void main(String[] args) {
		SpringApplication.run(AcquerelloApplication.class, args);
	}

	@Bean
	public CommandLineRunner initData(UserRepository userRepository, OrderRepository orderRepository,
									  OrderDetailsRepository orderDetailsRepository, ProductRepository productRepository,
									  CategoryRepository categoryRepository, ProductCategoryRepository productCategoryRepository,
									  AddressRepository addressRepository, CardRepository cardRepository, BookingRepository bookingRepository) {
		return (args) -> {

			Address address1 = new Address("Bids Hills", 332, "HI-3232", "California", "A white house");
			Address address2 = new Address("Bids Hills", 113, "HI-3110", "California", "A house gray");


			// Brian: Create User and Order
			User user1 = new User("Luigi", "number one", "admin@admin.com", passwordEncoder.encode("admin1234"), 5554343L, UserType.ADMIN, "https://res.cloudinary.com/luz-brito/image/upload/v1638657510/Acquerello/imgUser_sps9k8.jpg");
			User user2 = new User("Brian", "Cuenca", "gabriel.torrealba33@gmail.com", passwordEncoder.encode("user1234"), 5551232L, UserType.USER, "https://res.cloudinary.com/luz-brito/image/upload/v1638657510/Acquerello/imgUser_sps9k8.jpg");



			// Brian: Creating bookings
			Booking booking1 = new Booking(LocalDate.now(), LocalTime.now(), LocalTime.now().plusHours(4), SectorTables.GOLDEN, 2, 2, TableState.ACCEPTED, TableAvailability.NOTAVAILABLE);

			user1.addAddress(address1);
			user2.addAddress(address2);

			// Brian: Adding bookings to User
			user2.addBooking(booking1);

			userRepository.save(user1);
			userRepository.save(user2);

			addressRepository.save(address1);
			addressRepository.save(address2);

			bookingRepository.save(booking1);

			//GABRIEL: CREATE CARDS TO TEST THE APP
			Card card1 = new Card("1234-5654-9874-6321", 315, LocalDateTime.now(), 0D, 20, 1, CardType.ACTIVE, user2);
			cardRepository.save(card1);

			//GABRIEL: CREATE PRODUCTS TO TEST THE APP
			Product product1 = new Product("Bruschetta", 2, "Tomato Reduction base, Mozzarella, Torn Basil, Olive Oil", "https://res.cloudinary.com/luz-brito/image/upload/v1638657514/Acquerello/bruschetta_bbzjzr.jpg", 17D, 25);
			productRepository.save(product1);
			Product product2 = new Product("Capresse", 3, "Escarole, Cannellini Beans, Meatballs, Parmigiano", "https://res.cloudinary.com/luz-brito/image/upload/v1638657514/Acquerello/capresse_jp0bcd.jpg", 23D, 25);
			productRepository.save(product2);
			Product product3 = new Product("Vitello Tonato", 4, "Mushrooms, Ruccola, Pomodoro, Mozzarella, Olives", "https://res.cloudinary.com/luz-brito/image/upload/v1638657513/Acquerello/vitello_tonato_hlwouz.jpg", 22D, 25);
			productRepository.save(product3);
			Product product4 = new Product("AntiPasti Mixed", 5, "Mixed Greens, peppers, Anchoivies, Mozzarella, Artichokes","https://res.cloudinary.com/luz-brito/image/upload/v1638657513/Acquerello/antipasti_s7jfku.jpg", 23D, 25);
			productRepository.save(product4);
			Product product5 = new Product("Insalata Rucula", 0, "Tuscan Fried Chicken, Spicy Honey Butter","https://res.cloudinary.com/luz-brito/image/upload/v1638657511/Acquerello/insalataRusa_xakkbm.jpg", 12D, 25);
			productRepository.save(product5);
			Product product6 = new Product("Carpacio", 0, "Prosciutto ham, Baby Spinach, Garlic, Gorgonzola, Olives","https://res.cloudinary.com/luz-brito/image/upload/v1638657514/Acquerello/carpacio_sqxv4q.jpg", 16D, 25);
			productRepository.save(product6);
			Product product7 = new Product("Crema Di Pomodoro", 0, "Mix of mussels, Prawns, Octopus, Mozzarella, Basil, Red Souce", "https://res.cloudinary.com/luz-brito/image/upload/v1638657511/Acquerello/crema_di_pomodoro_v9k2wx.jpg", 19D, 25);
			productRepository.save(product7);
			Product product8 = new Product("Pizza Pane", 0, "Baby Kale, Apple,Cherry, Mozzarella, Pistachios, Basil", "https://res.cloudinary.com/luz-brito/image/upload/v1638657512/Acquerello/pizza_pane_yno3pe.jpg", 14D, 25);
			productRepository.save(product8);
			Product product9 = new Product("Stinco di Vitello", 0, "Red Chilli Flakes, Fennel Seeds, Carrot, Celery, Onion", "https://res.cloudinary.com/luz-brito/image/upload/v1638657513/Acquerello/stinco_divitello_a8xa5i.jpg",19D, 25);
			productRepository.save(product9);
			Product product10 = new Product("Gallo Cedrone", 0, "Olive Oil, Cinnamon, Saffron, Drumsticks, Tomatoes", "https://res.cloudinary.com/luz-brito/image/upload/v1638657511/Acquerello/gallo_cedrone_bcvglk.jpg", 17D, 25);
			productRepository.save(product10);
			Product product11 = new Product("Fritto Misto", 0, "Fried baby squid, smelt, shrimp, fennel, Calabrese peppers, served with mustard sauce", "https://res.cloudinary.com/luz-brito/image/upload/v1638657511/Acquerello/fritto_mixto_csxkjk.jpg", 18D, 25);
			productRepository.save(product11);
			Product product12 = new Product("Sogliola al Forno", 0, "Red Chilli Flakes, Fennel Seeds, Carrot, Celery, Onion", "https://res.cloudinary.com/luz-brito/image/upload/v1638658788/Acquerello/sogliola-alforno_ddmjgv.jpg", 12D, 25);
			productRepository.save(product12);
			Product product13 = new Product("Linguine con Granchio", 0, "Mushrooms, Ruccola, Pomodoro, Mozzarella, Olives", "https://res.cloudinary.com/luz-brito/image/upload/v1638657511/Acquerello/linguine_veodru.jpg", 23D, 25);
			productRepository.save(product13);
			Product product14 = new Product("Risotto Verde", 0, "Fresh Basil, Beans, Onion, Extra Virgin Olive Oil", "https://res.cloudinary.com/luz-brito/image/upload/v1638657512/Acquerello/risotto_verde_jwbrn5.jpg", 25D, 25);
			productRepository.save(product14);
			Product product15 = new Product("Panzotti", 0, "Onion, Carrot, Stick of Celery, Unsalted Butter, Beef", "https://res.cloudinary.com/luz-brito/image/upload/v1638657512/Acquerello/panzotti_rrpets.jpg", 15D, 25);
			productRepository.save(product15);
			Product product16 = new Product("Blue Cheese Crackers with Grapes", 0, "red grapes, olive oil, blue cheese, honey, pear", "https://res.cloudinary.com/luz-brito/image/upload/v1638657514/Acquerello/blue_cheese_oppto7.jpg", 12D, 25);
			productRepository.save(product16);
			Product product17 = new Product("Creamy Dorblu Appetizer with Honey", 0, "olive oil, dorblu, honey, pear", "https://res.cloudinary.com/luz-brito/image/upload/v1638662291/Acquerello/creemy_dorbiu_zapuve.jpg", 15D, 25);
			productRepository.save(product17);
			Product product18 = new Product("Beet Salad", 0, "Juicy Olives", "https://res.cloudinary.com/luz-brito/image/upload/v1638657514/Acquerello/category_salads_s5oynp.jpg", 10D, 25);
			productRepository.save(product18);
			Product product19 = new Product("Crab and Avocado", 0, "avocados, mayonnaise, lemon juice, lump crabmeat, cilantro, chives,  pepper, paprika", "https://res.cloudinary.com/luz-brito/image/upload/v1638657514/Acquerello/crab_avocado_x4tszw.jpg", 20D, 25);
			productRepository.save(product19);
			Product product20 = new Product("Skewers", 0, "Tomato, 0, mozarella, skewers", "https://res.cloudinary.com/luz-brito/image/upload/v1638657512/Acquerello/skewers_y8qyip.jpg", 24D, 25);
			productRepository.save(product20);
			Product product21 = new Product("Spaghetti Aglio E Olio", 0, "Whole roasted garlic, Calabrese pepper, parmigiano Reggiano", "https://res.cloudinary.com/luz-brito/image/upload/v1638657513/Acquerello/spaghettini-aglio-olio_jemk6t.jpg", 18D, 25);
			productRepository.save(product21);
			Product product22 = new Product("Minestrone Soup", 0, "This original classic Italian soup", "https://res.cloudinary.com/luz-brito/image/upload/v1638657512/Acquerello/minestrone-soup_oqdaaa.jpg", 20D, 25);
			productRepository.save(product22);
			Product product23 = new Product("Soupe à l’Oignon", 0, "onion soup gratineed with swiss cheese", "https://res.cloudinary.com/luz-brito/image/upload/v1638657513/Acquerello/soupe-a-loignon_nrwj9i.jpg", 18D, 25);
			productRepository.save(product23);
			Product product24 = new Product("Lasagna Alla Gino", 0, "Bolognese sauce, Bechamel sauce, ricotta cheese and tomato sauce", "https://res.cloudinary.com/luz-brito/image/upload/v1638657511/Acquerello/lasagna_alla_gino_kpjovx.jpg", 17D, 25);
			productRepository.save(product24);
			Product product25 = new Product("Pappardelle Mimmo", 0, "Long wide pasta with scallops, lobster, asparagus, butter, sage, truffle", "https://res.cloudinary.com/luz-brito/image/upload/v1638657512/Acquerello/pappardelle_mimmo_xjqwrf.jpg", 18D, 25);
			productRepository.save(product25);
			Product product26 = new Product("Rapini", 0, "Broccoli rabe, garlic", "https://res.cloudinary.com/luz-brito/image/upload/v1638657512/Acquerello/rapini_pgxvot.jpg", 13D, 25);
			productRepository.save(product26);
			Product product27 = new Product("Malloreddus", 0, "Saffron sardinian semolina gnocchetti, wild boar ragu, fiore sardo cheese", "https://res.cloudinary.com/luz-brito/image/upload/v1638657511/Acquerello/malloreddus_kmfwoo.jpg", 15D, 25);
			productRepository.save(product27);
			Product product28 = new Product("Salade d’ Epinards", 0, "field greens with prosciutto", "https://res.cloudinary.com/luz-brito/image/upload/v1638657513/Acquerello/salade_epinards_qdqgzm.jpg", 14D, 25);
			productRepository.save(product28);
			Product product29 = new Product("Salade Douce et Amère", 0, "Salade d’ Epinards", "https://res.cloudinary.com/luz-brito/image/upload/v1638657512/Acquerello/poached_eggsalad_tepfuv.jpg", 18D, 25);
			productRepository.save(product29);
			Product product30 = new Product("Poached Egg Salad", 0, "Savory, easy to make Poached Egg & Avocado Breakfast Salad that′s vegetarian, gluten free, and SO delicious!", "https://res.cloudinary.com/luz-brito/image/upload/v1638657512/Acquerello/poached_eggsalad_tepfuv.jpg", 12D, 25);
			productRepository.save(product30);
			Product product31 = new Product("Spinach Cream Soup", 0, "Sauteed chopped spinach is added to a roux-thickened mixture of milk and chicken bouillon", "https://res.cloudinary.com/luz-brito/image/upload/v1638658698/Acquerello/spinach-cream-soup_uo9ay9.jpg", 15D, 25);
			productRepository.save(product31);
			Product product32 = new Product("Layered Pasta Salad", 0, "With layers of ham, eggs, salami, veggies, and cheese, it is hearty and delicious!", "https://res.cloudinary.com/luz-brito/image/upload/v1638657511/Acquerello/layered_pastasalad_jgetre.jpg", 18D, 25);
			productRepository.save(product32);
			Product product33 = new Product("Strawberry Lemonade", 0, "It′s super refreshing, incredibly delicious and truly the “essence” of summer!", "https://res.cloudinary.com/luz-brito/image/upload/v1638658661/Acquerello/strawberry_lemonade_gm0zr0.jpg", 7D, 25);
			productRepository.save(product33);
			Product product34 = new Product("Classic Lemonade", 0, "There are few things more refreshing than a glass of lemonade, especially when it′s homemade", "https://res.cloudinary.com/luz-brito/image/upload/v1638657514/Acquerello/classic_lemonade_glwu6u.jpg", 5D, 25);
			productRepository.save(product34);
			Product product35 = new Product("Gnocchi Pomodoro", 0, "Potato dumplings, Campari tomato sauce, basil, extra virgin olive oil", "https://res.cloudinary.com/luz-brito/image/upload/v1638658555/Acquerello/gnocchi-pomodoro_j6zmkf.jpg", 16D, 25);
			productRepository.save(product35);
			Product product36 = new Product("Filetto Di Manzo", 0, "Seared tenderloin, squash-potato pavé, onion texture, oxtail reduction", "https://res.cloudinary.com/luz-brito/image/upload/v1638658643/Acquerello/filetto-di-manzo_ewicqc.jpg", 28D, 25);
			productRepository.save(product36);
			Product product37 = new Product("Orange Apricot", 0, "Refreshing with ice cubes and a round slice of orange", "https://res.cloudinary.com/luz-brito/image/upload/v1638657512/Acquerello/orange_apricot_xptr70.jpg", 7D, 25);
			productRepository.save(product37);
			Product product38 = new Product("Pumpkin soup", 0, "Made with pumpkin puree, chicken stock, onion, and fresh herbs. Cream is stirred in at the end for added richness.", "https://res.cloudinary.com/luz-brito/image/upload/v1638657513/Acquerello/pumpkin_soup_w7xtgb.jpg", 15D, 25);
			productRepository.save(product38);
			Product product39 = new Product("Garden Salad Tacos", 0, "Great snack or light vegetarian lunch", "https://res.cloudinary.com/luz-brito/image/upload/v1638657511/Acquerello/garden_saladtacos_wdpdiv.jpg", 20D, 25);
			productRepository.save(product39);


			//GABRIEL: CREATE CATEGORIES TO TEST THEE APP
			Category category1 = new Category("Entries & Snacks", "Whether you need healthy snacks that are gluten free, low calorie, low sugar, high protein, vegan, or more, there's something on this list for you.", "https://res.cloudinary.com/luz-brito/image/upload/v1638657511/Acquerello/linguine_veodru.jpg");
			categoryRepository.save(category1);
			Category category2 = new Category("Specials", "xxxxxxx", "https://res.cloudinary.com/luz-brito/image/upload/v1638657511/Acquerello/lasagna_alla_gino_kpjovx.jpg");
			categoryRepository.save(category2);
			Category category3 = new Category("Chef Picks", "xxxxxx", "https://res.cloudinary.com/luz-brito/image/upload/v1638657512/Acquerello/skewers_y8qyip.jpg");
			categoryRepository.save(category3);
			Category category4 = new Category("Main Course", "xxxxx", "https://res.cloudinary.com/luz-brito/image/upload/v1638657513/Acquerello/stinco_divitello_a8xa5i.jpg");
			categoryRepository.save(category4);
			Category category5 = new Category("Soup", "xxxxx", "https://res.cloudinary.com/luz-brito/image/upload/v1638657513/Acquerello/pumpkin_soup_w7xtgb.jpg");
			categoryRepository.save(category5);
			Category category6 = new Category("Drinks", "xxxxx", "https://res.cloudinary.com/luz-brito/image/upload/v1638657514/Acquerello/category_drinks_xklgfs.jpg");
			categoryRepository.save(category6);
			Category category7 = new Category("Pasta", "xxxx", "https://res.cloudinary.com/luz-brito/image/upload/v1638657512/Acquerello/panzotti_rrpets.jpg");
			categoryRepository.save(category7);
			Category category8 = new Category("Vegetarian", "xxxxxxxx", "https://res.cloudinary.com/luz-brito/image/upload/v1638657511/Acquerello/garden_saladtacos_wdpdiv.jpg");
			categoryRepository.save(category8);
			Category category9 = new Category("Salads", "xxxxxxxxx", "https://res.cloudinary.com/luz-brito/image/upload/v1638657512/Acquerello/rapini_pgxvot.jpg");
			categoryRepository.save(category9);

			//GABRIEL: CREATE PRODUCTS/CATEGORIES (MANY TO MANY) TO TEST THE APP
			ProductCategory productCategory1 = new ProductCategory(product1, category1);
			productCategoryRepository.save(productCategory1);
			ProductCategory productCategory2 = new ProductCategory(product2, category4);
			productCategoryRepository.save(productCategory2);
			ProductCategory productCategory3 = new ProductCategory(product3, category1);
			productCategoryRepository.save(productCategory3);
			ProductCategory productCategory4 = new ProductCategory(product4,category1);
			productCategoryRepository.save(productCategory4);
			ProductCategory productCategory5 = new ProductCategory(product5, category5);
			productCategoryRepository.save(productCategory5);
			ProductCategory productCategory6 = new ProductCategory(product5,category9);
			productCategoryRepository.save(productCategory6);
			ProductCategory productCategory7 = new ProductCategory(product6, category4);
			productCategoryRepository.save(productCategory7);
			ProductCategory productCategory8 = new ProductCategory(product7, category3);
			productCategoryRepository.save(productCategory8);
			ProductCategory productCategory9 = new ProductCategory(product7, category1);
			productCategoryRepository.save(productCategory9);
			ProductCategory productCategory10 = new ProductCategory(product8, category4);
			productCategoryRepository.save(productCategory10);
			ProductCategory productCategory11 = new ProductCategory(product9, category4);
			productCategoryRepository.save(productCategory11);
			ProductCategory productCategory12 = new ProductCategory(product10, category4);
			productCategoryRepository.save(productCategory12);
			ProductCategory productCategory13 = new ProductCategory(product10, category3);
			productCategoryRepository.save(productCategory13);
			ProductCategory productCategory14 = new ProductCategory(product11, category4);
			productCategoryRepository.save(productCategory14);
			ProductCategory productCategory15 = new ProductCategory(product11, category8);
			productCategoryRepository.save(productCategory15);
			ProductCategory productCategory16 = new ProductCategory(product12, category4);
			productCategoryRepository.save(productCategory16);
			ProductCategory productCategory17 = new ProductCategory(product13, category1);
			productCategoryRepository.save(productCategory17);
			ProductCategory productCategory18 = new ProductCategory(product14, category4);
			productCategoryRepository.save(productCategory18);
			ProductCategory productCategory19 = new ProductCategory(product14, category7);
			productCategoryRepository.save(productCategory19);
			ProductCategory productCategory20 = new ProductCategory(product15,category7);
			productCategoryRepository.save(productCategory20);
			ProductCategory productCategory22 = new ProductCategory(product16, category2);
			productCategoryRepository.save(productCategory22);
			ProductCategory productCategory23 = new ProductCategory(product17, category2);
			productCategoryRepository.save(productCategory23);
			ProductCategory productCategory25 = new ProductCategory(product18, category9);
			productCategoryRepository.save(productCategory25);
			ProductCategory productCategory26 = new ProductCategory(product19, category4);
			productCategoryRepository.save(productCategory26);
			ProductCategory productCategory27 = new ProductCategory(product20, category3);
			productCategoryRepository.save(productCategory27);
			ProductCategory productCategory28 = new ProductCategory(product21, category7);
			productCategoryRepository.save(productCategory28);
			ProductCategory productCategory29 = new ProductCategory(product22, category5);
			productCategoryRepository.save(productCategory29);
			ProductCategory productCategory31 = new ProductCategory(product23, category5);
			productCategoryRepository.save(productCategory31);
			ProductCategory productCategory32 = new ProductCategory(product23, category9);
			productCategoryRepository.save(productCategory32);
			ProductCategory productCategory34 = new ProductCategory(product24, category3);
			productCategoryRepository.save(productCategory34);
			ProductCategory productCategory36 = new ProductCategory(product26, category9);
			productCategoryRepository.save(productCategory36);
			ProductCategory productCategory37 = new ProductCategory(product27, category1);
			productCategoryRepository.save(productCategory37);
			ProductCategory productCategory38 = new ProductCategory(product28, category9);
			productCategoryRepository.save(productCategory38);
			ProductCategory productCategory39 = new ProductCategory(product29, category9);
			productCategoryRepository.save(productCategory39);
			ProductCategory productCategory40 = new ProductCategory(product30, category9);
			productCategoryRepository.save(productCategory40);
			ProductCategory productCategory41 = new ProductCategory(product31, category5);
			productCategoryRepository.save(productCategory41);
			ProductCategory productCategory43 = new ProductCategory(product32, category4);
			productCategoryRepository.save(productCategory43);
			ProductCategory productCategory45 = new ProductCategory(product33, category6);
			productCategoryRepository.save(productCategory45);
			ProductCategory productCategory46 = new ProductCategory(product34, category6);
			productCategoryRepository.save(productCategory46);
			ProductCategory productCategory47 = new ProductCategory(product35, category8);
			productCategoryRepository.save(productCategory47);
			ProductCategory productCategory48 = new ProductCategory(product36, category4);
			productCategoryRepository.save(productCategory48);
			ProductCategory productCategory49 = new ProductCategory(product37, category6);
			productCategoryRepository.save(productCategory49);
			ProductCategory productCategory50 = new ProductCategory(product38, category5);
			productCategoryRepository.save(productCategory50);
			ProductCategory productCategory51 = new ProductCategory(product39, category8);
			productCategoryRepository.save(productCategory51);

			// Brian: Creating order
			Order order1 = new Order(LocalDateTime.now().minusMinutes(20), LocalDateTime.now(), OrderState.PENDING, 32.43);
			Order order2 = new Order(LocalDateTime.now().minusMinutes(10), LocalDateTime.now(), OrderState.PENDING, 11.43);
			// Brian: Adding order to Users
			user1.addOrder(order1);
			user2.addOrder(order2);
			orderRepository.save(order1);
			orderRepository.save(order2);
			userRepository.save(user1);
			userRepository.save(user2);

			// Brian : Creating order details
			OrderDetails orderDetails1 = new OrderDetails(2,product1,order1);
			OrderDetails orderDetails2 = new OrderDetails(3,product2,order1);
			OrderDetails orderDetails3 = new OrderDetails(4,product3,order2);
			OrderDetails orderDetails4 = new OrderDetails(5,product4,order2);
			orderDetailsRepository.save(orderDetails1);
			orderDetailsRepository.save(orderDetails2);
			orderDetailsRepository.save(orderDetails3);
			orderDetailsRepository.save(orderDetails4);
		};
	}
}
