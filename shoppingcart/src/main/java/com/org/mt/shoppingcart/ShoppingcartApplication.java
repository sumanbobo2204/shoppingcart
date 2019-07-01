package com.org.mt.shoppingcart;

import com.org.shoppingcart.repository.CartRepository;
import com.org.shoppingcart.repository.UserRepository;
import com.org.shoppingcart.service.CartService;
import com.org.shoppingcart.service.CartServiceImpl;
import com.org.shoppingcart.service.UserService;
import com.org.shoppingcart.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@ComponentScan(basePackages = {"com.org.shoppingcart.*"})
@EnableJpaRepositories(basePackages = {"com.org.shoppingcart.repository"})
@EntityScan(basePackages = {"com.org.shoppingcart.entity"})
@EnableTransactionManagement(proxyTargetClass = true)
public class ShoppingcartApplication {

	public static void main(String[] args) {

		SpringApplication.run(ShoppingcartApplication.class, args);
	}
    @Autowired
	UserRepository userRepository;
	@Autowired
	CartRepository cartRepository;

	@Bean
	CartService cartService(){
		return new CartServiceImpl(this.cartRepository);
	}

	@Bean
	UserService userService(){
		return new UserServiceImpl(this.userRepository);
	}




}
