package br.com.junit.api.services.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mockitoSession;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;

import br.com.junit.api.domain.User;
import br.com.junit.api.domain.dto.UserDTO;
import br.com.junit.api.domain.repositories.UserRepository;
import br.com.junit.api.services.exceptions.DataIntegratyViolationException;
import br.com.junit.api.services.exceptions.ObjectNotFoundException;

@SpringBootTest
class UserServiceImplTest {
	
	private static final String MENSAGEM = "Objeto não encontrado";

	private static final int INDEX = 0;

	private static final Integer ID = 1;

	private static final String NOME = "Xavier";

	private static final String EMAIL = "xavier@gmail.com";

	private static final String SENHA = "123";

	@InjectMocks
	private UserServiceImpl service;
	
	@Mock
	private UserRepository repository;
	
	@Mock
	private ModelMapper mapper;
		
	private User user;
	private UserDTO userDTO;
	private Optional<User> optionalUser;
	
	@BeforeEach
	void setUp() throws Exception {
		MockitoAnnotations.openMocks(this);
		startUser();
	}

	@Test
	void testFindById() {
		when(repository.findById(anyInt())).thenReturn(optionalUser);
		
		User response = service.findById(ID);
		assertNotNull(response);
		assertEquals(User.class, response.getClass());
		assertEquals(ID, response.getId());
		assertEquals(NOME, response.getName());
		assertEquals(EMAIL, response.getEmail());
	}
	
	@Test
	void testFindByIdNotFound() {
		when(repository.findById(anyInt())).thenThrow(new ObjectNotFoundException(MENSAGEM));
		try {
			service.findById(ID);
		} catch (Exception ex) {
			assertEquals(ObjectNotFoundException.class, ex.getClass());
			assertEquals(MENSAGEM, ex.getMessage());
		}
	}

	@Test
	void testFindAll() {
		List<User> lista = new ArrayList<User>();
		lista.add(user);
		
		when(repository.findAll()).thenReturn(lista);
		
		List<User> response = service.findAll();
		assertNotNull(response);
		assertEquals(User.class, response.get(INDEX).getClass());
		assertEquals(ID, response.get(INDEX).getId());
		assertEquals(NOME, response.get(INDEX).getName());
		assertEquals(EMAIL, response.get(INDEX).getEmail());
	}

	@Test
	void testCreateWithSucess() {
		when(repository.save(any())).thenReturn(user);
		
		User response = service.create(userDTO);
		assertNotNull(response);
		assertEquals(User.class, response.getClass());
		assertEquals(ID, response.getId());
		assertEquals(NOME, response.getName());
		assertEquals(EMAIL, response.getEmail());
	}
	
	@Test
	void testCreateWithIntegrationError() {
		when(repository.findByEmail(anyString())).thenReturn(optionalUser);
		
		try {
			optionalUser.get().setId(2);
			service.create(userDTO);
		} catch (Exception ex) {
			assertEquals(DataIntegratyViolationException.class, ex.getClass());
			assertEquals("E-mail já existe", ex.getMessage());
		}
	}

	@Test
	void testUpdateWithSucess() {
	when(repository.save(any())).thenReturn(user);
		
		User response = service.update(userDTO);
		assertNotNull(response);
		assertEquals(User.class, response.getClass());
		assertEquals(ID, response.getId());
		assertEquals(NOME, response.getName());
		assertEquals(EMAIL, response.getEmail());
	}
	
	@Test
	void testUpdateWithIntegrationError() {
		when(repository.findByEmail(anyString())).thenReturn(optionalUser);
		
		try {
			optionalUser.get().setId(2);
			service.update(userDTO);
		} catch (Exception ex) {
			assertEquals(DataIntegratyViolationException.class, ex.getClass());
			assertEquals("E-mail já existe", ex.getMessage());
		}
	}

	@Test
	void testDeleteWithSucess() {
		when(repository.findById(anyInt())).thenReturn(optionalUser);
		doNothing().when(repository).deleteById(anyInt());
		
		service.delete(ID);
		verify(repository, times(1)).deleteById(anyInt());
	}
	
	@Test
	void testDeleteWithNotFoundError() {
		when(repository.findById(anyInt())).thenThrow(new ObjectNotFoundException(MENSAGEM));
		try {
			service.delete(ID);
		} catch (Exception ex) {
			assertEquals(ObjectNotFoundException.class, ex.getClass());
			assertEquals(MENSAGEM, ex.getMessage());
		}
	}
	
	private void startUser() {
		user = new User(ID, NOME, EMAIL, SENHA);
		userDTO = new UserDTO(ID, NOME, EMAIL, SENHA);
		optionalUser = Optional.of(new User(ID, NOME, EMAIL, SENHA));
	}

}
