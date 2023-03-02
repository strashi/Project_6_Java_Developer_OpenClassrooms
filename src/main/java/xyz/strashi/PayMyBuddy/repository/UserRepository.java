package xyz.strashi.PayMyBuddy.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import xyz.strashi.PayMyBuddy.model.User;

public interface UserRepository extends JpaRepository<User,Long>{

	User findByEmail(String email);

}
