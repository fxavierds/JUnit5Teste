package br.com.junit.api.services;

import org.springframework.stereotype.Service;

import br.com.junit.api.domain.User;

@Service
public interface UserService {
	public User findById(Integer id);
}
