package br.com.junit.api.config;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import br.com.junit.api.domain.User;
import br.com.junit.api.domain.repositories.UserRepository;

@Configuration
@Profile("local")
public class LocalConfig {
	
	@Autowired
	private UserRepository repository; 
	
	@Bean
	public void startDB() {
		User u1 = new User(null, "Francisco", "francisco@email.com", "123");
		User u2 = new User(null, "Xavier", "xavier@email.com", "123");
		List<User> list = new ArrayList<User>();
		list.add(u1);
		list.add(u2);
		repository.saveAll(list);
	}
}
