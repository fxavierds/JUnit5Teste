package br.com.junit.api.services;

import java.util.List;

import org.springframework.stereotype.Service;

import br.com.junit.api.domain.User;
import br.com.junit.api.domain.dto.UserDTO;

@Service
public interface UserService {
	User findById(Integer id);
	List<User> findAll();
	User create(UserDTO obj);
}
