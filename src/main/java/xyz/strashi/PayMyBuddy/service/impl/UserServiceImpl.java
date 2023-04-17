package xyz.strashi.PayMyBuddy.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.annotation.PostConstruct;

import org.hibernate.annotations.DynamicUpdate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import xyz.strashi.PayMyBuddy.controller.LoginController;
import xyz.strashi.PayMyBuddy.model.BankAccount;
import xyz.strashi.PayMyBuddy.model.Relationship;
import xyz.strashi.PayMyBuddy.model.Role;
import xyz.strashi.PayMyBuddy.model.User;
import xyz.strashi.PayMyBuddy.repository.BankAccountRepository;
import xyz.strashi.PayMyBuddy.repository.UserRepository;
import xyz.strashi.PayMyBuddy.service.TransactionService;
import xyz.strashi.PayMyBuddy.service.UserService;

@Service
@DynamicUpdate
public class UserServiceImpl implements UserService{
	
	private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private BankAccountRepository bankAccountRepository;
	
	@Autowired
	private TransactionService transactionService;
	
	@Autowired
	private Utility utility;
	
	@Value("${config.system.admin.email}")
	private String emailAdmin;
	
	@Value("${config.system.admin.firstName}") 
	private String firstNameAdmin;
	 
	@Value("${config.system.admin.lastName}")
	private String lastNameAdmin;
	
	@Value("${config.system.admin.balance}") 
	private double balanceAdmin;
	 
	@Value("${config.system.admin.password}")
	private String passwordAdmin;
	
	@Value("${config.system.admin.role}") 
	private Role roleAdmin;
	 
	@PostConstruct
	private void initAdminSystem() {
		logger.debug("initAdminSystem sollicité de UserServiceImpl");
		try {
			String hashedPassword = utility.encoder(passwordAdmin);
			User adminSystem = new User();
			adminSystem.setEmail(emailAdmin);
			adminSystem.setFirstName(firstNameAdmin);
			adminSystem.setLastName(lastNameAdmin);
			adminSystem.setRole(roleAdmin);
			adminSystem.setBalance(balanceAdmin);
			adminSystem.setPassword(hashedPassword);
			if (!userRepository.findByEmail(emailAdmin).isPresent()) {
				userRepository.save(adminSystem);
				logger.info("Création Admin effectuée");
			}else {
				logger.info("Admin déjà créé");
			}
		}catch (Exception e) {
			logger.error("Erreur au initAdminSystem", e);
			
		}
	}
	
	@Override
	public User createUser(User user) {
	 
	 	String hashedPassword = utility.encoder(user.getPassword());
        Optional<User> optionalUser = userRepository.findByEmail(user.getEmail());
        if(optionalUser.isPresent()) {
        	User userToUpdate = optionalUser.get();
        	user.setUserId(userToUpdate.getUserId());
        	if(user.getPassword().isEmpty()) {
        		user.setPassword(userToUpdate.getPassword());
        	}else {
        		user.setPassword(hashedPassword);
        	}
        	
        }else {
        	user.setPassword(hashedPassword);
        }
	        
		return userRepository.save(user);
	}
	
	@Override
	public void depositMoney(User user, String ibanNumber, double amount) {
		BankAccount bankAccount = bankAccountRepository.findByIbanNumber(ibanNumber);
		String description = "Reload from "+bankAccount.getAccountDescription();
		User admin = userRepository.findByEmail("admin@paymybuddy.com").orElseThrow(() -> new UsernameNotFoundException("User not present"));

		transactionService.executeTransaction(admin, user, amount, description, false);
		
	}
		
	@Override
	public User addRelationship(String emailUser, String emailFriend) {
		User user = userRepository.findByEmail(emailUser).orElseThrow(() -> new UsernameNotFoundException("User not present"));
		User friend = userRepository.findByEmail(emailFriend).orElseThrow(() -> new UsernameNotFoundException("User not present"));
		Date creationDate = new Date();
		Relationship rel = new Relationship(friend,creationDate);
		
		user.getFriends().add(rel);
		
		return userRepository.save(user);
	}

	
	@Override
	public List<Relationship> getRelationships(User user) {

		Optional<User> opt = userRepository.findById(user.getUserId());
		User responseUser = opt.get();
		
		List<Relationship> relationshipsList = responseUser.getFriends();
		return relationshipsList;
	}
		
	public List<User> getRelationshipsUser(User user) {
		List<Relationship>friendsList = this.getRelationships(user);
		List<User> RelationshipUserList = new ArrayList<>();
		for(Relationship relationship : friendsList) {
			RelationshipUserList.add(relationship.getFriend());
		}
		return RelationshipUserList;
	}
	
	@Override
	public User addBankAccount(User user,String accountDescription, String ibanNumber) {
		BankAccount bankAccount = new BankAccount(accountDescription, ibanNumber);
		user.getBankAccounts().add(bankAccount);
		return userRepository.save(user);
	}
	
	@Override
	public List<BankAccount> getBankAccounts(User user) {
		Optional<User> opt = userRepository.findById(user.getUserId());
		User responseUser = opt.get();
		
		 List<BankAccount> bankAccountsList = responseUser.getBankAccounts();
		return bankAccountsList;
	}
	

	public User findByEmail(String email) {
		return userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User not present"));
	}

	@Override
	public void bankDeposit(User user, String ibanNumber, double amount) {
		BankAccount bankAccount = bankAccountRepository.findByIbanNumber(ibanNumber);
		String description = "Deposit to "+bankAccount.getAccountDescription();
		User admin = userRepository.findByEmail("admin@paymybuddy.com").orElseThrow(() -> new UsernameNotFoundException("User not present"));

		transactionService.executeTransaction(user, admin, amount, description, false);
		
	}

	

	

}
