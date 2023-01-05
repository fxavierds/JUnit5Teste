package br.com.junit.api.services.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.junit.api.domain.User;
import br.com.junit.api.domain.repositories.UserRepository;
import br.com.junit.api.services.UserService;
import br.com.junit.api.services.exceptions.ObjectNotFoundException;

@Service
public class UserServiceImpl implements UserService{
	@Autowired
	private UserRepository repository;
	
	@Override
	public User findById(Integer id) {
		Optional<User> usuario = repository.findById(id);
		return usuario.orElseThrow(() -> new ObjectNotFoundException("Usuario não encontrado"));
	}

}
