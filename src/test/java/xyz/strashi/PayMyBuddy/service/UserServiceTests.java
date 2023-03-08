package xyz.strashi.PayMyBuddy.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import xyz.strashi.PayMyBuddy.model.BankAccount;
import xyz.strashi.PayMyBuddy.model.Relationship;
import xyz.strashi.PayMyBuddy.model.User;
import xyz.strashi.PayMyBuddy.repository.UserRepository;

@SpringBootTest
public class UserServiceTests {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private UserRepository userRepository;
	
	@BeforeEach
	public void delete() {
		userRepository.deleteAll();
		
	}
	
	
	@Test
	public void createUserTest() {
		List<BankAccount> bankAccounts = null;
		List<Relationship> relationships = null;
		User user = new User(0L,"email@xyz","password","firstName","lastName",50.0f, bankAccounts, relationships);
		
		//User responseUser = userRepository.save(user);
		User responseUser = userService.createUser(user);
				
		assertThat(responseUser.equals(user));
	}
	
	@Test
	public void addRelationshipTest() {
		List<BankAccount> bankAccounts = null;
		List<Relationship> relationships = new ArrayList<>();
		User user1 = new User(0L,"email1@xyz","password","firstName","lastName",50.0f, null, relationships);
		User user2 = new User(0L,"email2@xyz","password","firstName2","lastName2",50.0f, null, relationships);
		User user3 = new User(0L,"email3@xyz","password","firstName3","lastName3",50.0f, null, relationships);
		user1 = userService.createUser(user1);
		user2 = userService.createUser(user2);
		user3 = userService.createUser(user3);
	
		userService.addRelationship(user1, user2);
	
		userService.addRelationship(user1, user3);
		
		Optional<User> opt = userRepository.findById(user1.getUserId());
		User responseUser = opt.get();
		
		List<Relationship> relationsList = responseUser.getFriends();
		
		Long long1 = relationsList.get(0).getUserIdFriend();
		Long long2 = relationsList.get(1).getUserIdFriend();
				
		assertThat(long1 == user2.getUserId());
		assertThat(long2 == user3.getUserId());
		
	}
	
	@Test
	public void getRelationshipTest() {
		
		List<BankAccount> bankAccounts = null;
		List<Relationship> relationships = new ArrayList<>();
		User user1 = new User(0L,"email1@xyz","password","firstName","lastName",50.0f, null, relationships);
		User user2 = new User(0L,"email2@xyz","password","firstName2","lastName2",50.0f, null, relationships);
		User user3 = new User(0L,"email3@xyz","password","firstName3","lastName3",50.0f, null, relationships);
		user1 = userService.createUser(user1);
		user2 = userService.createUser(user2);
		user3 = userService.createUser(user3);
	
		userService.addRelationship(user1, user2);
	
		userService.addRelationship(user1, user3);
		
		List<Relationship> relationshipsList = userService.getRelationships(user1);
		
		Long long2 = relationshipsList.get(0).getUserIdFriend();
		Long long3 = relationshipsList.get(1).getUserIdFriend();
		
		assertThat(long2 == user2.getUserId());
		assertThat(long3 == user3.getUserId());

	}
}
