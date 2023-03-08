package xyz.strashi.PayMyBuddy.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import xyz.strashi.PayMyBuddy.model.Relationship;
import xyz.strashi.PayMyBuddy.model.User;

public interface UserRepository extends JpaRepository<User,Long>{

	User findByEmail(String email);

	//List<Relationship> findByUserId(Long userId);

}
