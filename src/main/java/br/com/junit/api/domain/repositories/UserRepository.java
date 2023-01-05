package br.com.junit.api.domain.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.junit.api.domain.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer>{

}
