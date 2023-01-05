package br.com.junit.api.resources.exceptions;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class StandartErrors {
	private LocalDate timestamp;
	private Integer status;
	private String error;
	private String path;
}
