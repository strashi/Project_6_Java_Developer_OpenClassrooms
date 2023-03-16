package xyz.strashi.PayMyBuddy.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import xyz.strashi.PayMyBuddy.model.BankAccount;
import xyz.strashi.PayMyBuddy.model.Relationship;
import xyz.strashi.PayMyBuddy.model.User;

public interface UserRepository extends JpaRepository<User,Long>{

	User findByEmail(String email);
	/*
	@Query(value="START TRANSACTION;"
			+ "UPDATE users "
			+ "SET balance = balance - :amount "
			+ "WHERE email = :emailDebitor; "
			+ "UPDATE users " 
			+ "SET balance = balance + :amount "
			+ "WHERE email = :emailCreditor; "
			+ "commit;", nativeQuery = true)
	void executeTransaction(@Param("emailDebitor") String emailDebitor, @Param("emailCreditor") String emailCreditor,
			@Param("amount") float amount);*/
	/*
	@Query(value="SELECT p.phone "
			+ "FROM persons p "
			+ "JOIN firestations f "
			+ "WHERE f.station= :station "
			+ "AND p.address = f.address", nativeQuery = true)
	public List<String> phoneAlert(@Param("station") Integer station);
	*/
	//List<Relationship> findByUserId(Long userId);

	User findByFirstName(String emailUser);

}
