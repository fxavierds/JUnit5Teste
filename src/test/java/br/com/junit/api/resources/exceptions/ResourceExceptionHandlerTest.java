package br.com.junit.api.resources.exceptions;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;

import br.com.junit.api.domain.dto.UserDTO;
import br.com.junit.api.services.exceptions.DataIntegratyViolationException;
import br.com.junit.api.services.exceptions.ObjectNotFoundException;

@SpringBootTest
class ResourceExceptionHandlerTest {
	@InjectMocks
	private ResourceExceptionHandler exceptionHandler;

	@BeforeEach
	void setUp() throws Exception {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void testnotFoundException() {
		ResponseEntity<StandartErrors> response = exceptionHandler.objectNotFound(new ObjectNotFoundException("Objeto não encontrado"), new MockHttpServletRequest());
		
		assertNotNull(response);
		assertNotNull(response.getBody());
		assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
		assertEquals(ResponseEntity.class, response.getClass());
		assertEquals(StandartErrors.class, response.getBody().getClass());
		assertEquals("Objeto não encontrado", response.getBody().getError());
		assertEquals(404, response.getBody().getStatus());	
		assertNotEquals("user/2", response.getBody().getPath());
		assertNotEquals(LocalDateTime.now(), response.getBody().getTimestamp());
	}
	
	@Test
	void testDataIntegratyViolationException() {
	ResponseEntity<StandartErrors> response = exceptionHandler.dataIntegratyViolation(new DataIntegratyViolationException("Objeto não encontrado"), new MockHttpServletRequest());
		
		assertNotNull(response);
		assertNotNull(response.getBody());
		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
		assertEquals(ResponseEntity.class, response.getClass());
		assertEquals(StandartErrors.class, response.getBody().getClass());
		assertEquals("Objeto não encontrado", response.getBody().getError());
		assertEquals(400, response.getBody().getStatus());
	}

}
