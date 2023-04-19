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
				logger.info("Création Admin effectuée de UserServiceImpl");
			}else {
				logger.info("Admin déjà créé de UserServiceImpl");
			}
		}catch (Exception e) {
			logger.error("Erreur au initAdminSystem de UserServiceImpl", e);
			
		}
	}
	
	@Override
	public User createUser(User user) {
		logger.debug("createUser sollicité de UserServiceImpl");
		try {
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
			logger.info("CreateUser effectuée de UserServiceImpl");
   
			return userRepository.save(user);
		}catch (Exception e) {
			logger.error("Erreur au createUser de UserServiceImpl", e);
			return null;
		}
	}
	
	@Override
	public void depositMoney(User user, String ibanNumber, double amount) {
		logger.debug("depositMoney sollicité de UserServiceImpl");
		try {
			BankAccount bankAccount = bankAccountRepository.findByIbanNumber(ibanNumber);
			String description = "Reload from "+bankAccount.getAccountDescription();
			User admin = userRepository.findByEmail("admin@paymybuddy.com").orElseThrow(() -> new UsernameNotFoundException("User not present"));
			transactionService.executeTransaction(admin, user, amount, description, false);
			logger.info("depositMoney effectuée de UserServiceImpl");

		}catch (Exception e) {
			logger.error("Erreur au depositMoney de UserServiceImpl", e);
		}
	}
		
	@Override
	public User addRelationship(String emailUser, String emailFriend) {
		logger.debug("addRelationship sollicité de UserServiceImpl");
		try {
			User user = userRepository.findByEmail(emailUser).orElseThrow(() -> new UsernameNotFoundException("User not present"));
			User friend = userRepository.findByEmail(emailFriend).orElseThrow(() -> new UsernameNotFoundException("User not present"));
			Date creationDate = new Date();
			Relationship rel = new Relationship(friend,creationDate);
			user.getFriends().add(rel);
			logger.info("addRelationship effectuée de UserServiceImpl");
			return userRepository.save(user);
			
		}catch (Exception e) {
			logger.error("Erreur au addRelationship de UserServiceImpl", e);
			return null;
		}
	}

	
	@Override
	public List<Relationship> getRelationships(User user) {
		logger.debug("getRelationships sollicité de UserServiceImpl");
		try {
			Optional<User> opt = userRepository.findById(user.getUserId());
			User responseUser = opt.get();
			List<Relationship> relationshipsList = responseUser.getFriends();
			logger.info("getRelationships effectuée de UserServiceImpl");
			return relationshipsList;
			
		}catch (Exception e) {
			logger.error("Erreur au getRelationships de UserServiceImpl", e);
			return null;
		}
	}
		
	public List<User> getRelationshipsUser(User user) {
		logger.debug("getRelationshipsUser sollicité de UserServiceImpl");
		try {
			List<Relationship>friendsList = this.getRelationships(user);
			List<User> RelationshipUserList = new ArrayList<>();
			for(Relationship relationship : friendsList) {
				RelationshipUserList.add(relationship.getFriend());
			}
			logger.info("getRelationshipsUser effectuée de UserServiceImpl");
			return RelationshipUserList;
		}catch (Exception e) {
			logger.error("Erreur au getRelationshipsUser de UserServiceImpl", e);
			return null;
		}
	}
	
	@Override
	public User addBankAccount(User user,String accountDescription, String ibanNumber) {
		logger.debug("addBankAccount sollicité de UserServiceImpl");
		try {
			BankAccount bankAccount = new BankAccount(accountDescription, ibanNumber);
			user.getBankAccounts().add(bankAccount);
			logger.info("addBankAccount effectuée de UserServiceImpl");
			return userRepository.save(user);
			
		}catch (Exception e) {
			logger.error("Erreur au addBankAccount de UserServiceImpl", e);
			return null;
		}
	}
	
	@Override
	public List<BankAccount> getBankAccounts(User user) {
		logger.debug("getBankAccounts sollicité de UserServiceImpl");
		try {
			Optional<User> opt = userRepository.findById(user.getUserId());
			User responseUser = opt.get();
			List<BankAccount> bankAccountsList = responseUser.getBankAccounts();
			logger.info("getBankAccounts effectuée de UserServiceImpl");
			return bankAccountsList;
			
		}catch (Exception e) {
			logger.error("Erreur au getBankAccounts de UserServiceImpl", e);
			return null;
		}
	}
	

	public User findByEmail(String email) {
		logger.debug("findByEmail sollicité de UserServiceImpl");
		try {
			logger.info("findByEmail effectuée de UserServiceImpl");
			return userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User not present"));
		}catch (Exception e) {
			logger.error("Erreur au findByEmail de UserServiceImpl", e);
			return null;
		}
	}

	@Override
	public void bankDeposit(User user, String ibanNumber, double amount) {
		logger.debug("bankDeposit sollicité de UserServiceImpl");
		try {
			BankAccount bankAccount = bankAccountRepository.findByIbanNumber(ibanNumber);
			String description = "Deposit to "+bankAccount.getAccountDescription();
			User admin = userRepository.findByEmail("admin@paymybuddy.com").orElseThrow(() -> new UsernameNotFoundException("User not present"));
			transactionService.executeTransaction(user, admin, amount, description, false);
			logger.info("bankDeposit effectuée de UserServiceImpl");

		}catch (Exception e) {
			logger.error("Erreur au bankDeposit de UserServiceImpl", e);
			
		}
		
	}

	

	

}
