package br.com.junit.api.services.impl;

import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.junit.api.domain.User;
import br.com.junit.api.domain.dto.UserDTO;
import br.com.junit.api.domain.repositories.UserRepository;
import br.com.junit.api.services.UserService;
import br.com.junit.api.services.exceptions.DataIntegratyViolationException;
import br.com.junit.api.services.exceptions.ObjectNotFoundException;

@Service
public class UserServiceImpl implements UserService{
	@Autowired
	private UserRepository repository;
	
	@Autowired
	private ModelMapper mapper;
	
	@Override
	public User findById(Integer id) {
		Optional<User> usuario = repository.findById(id);
		return usuario.orElseThrow(() -> new ObjectNotFoundException("Usuario não encontrado"));
	}
	
	public List<User> findAll() {
		return repository.findAll();
	}

	@Override
	public User create(UserDTO obj) {
		findByEmail(obj);
		return repository.save(mapper.map(obj, User.class));
	}
	
	public void findByEmail(UserDTO dto) {
		Optional<User> user = repository.findByEmail(dto.getEmail());
		if(user.isPresent() && !user.get().getId().equals(dto.getId())) {
			throw new DataIntegratyViolationException("E-mail já existe");
		}
	}

	@Override
	public User update(UserDTO obj) {
		findByEmail(obj);
		return repository.save(mapper.map(obj, User.class));
	}

	@Override
	public void delete(Integer id) {		
		findById(id);
		repository.deleteById(id);		
	}

}
