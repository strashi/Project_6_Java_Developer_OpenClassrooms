package xyz.strashi.PayMyBuddy.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import xyz.strashi.PayMyBuddy.model.User;

public interface UserRepository extends JpaRepository<User,Long>{

	Optional<User> findByEmail(String email);

}
