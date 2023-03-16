package xyz.strashi.PayMyBuddy.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import xyz.strashi.PayMyBuddy.dto.CreateRelationshipDTO;
import xyz.strashi.PayMyBuddy.model.Transaction;
import xyz.strashi.PayMyBuddy.model.User;
import xyz.strashi.PayMyBuddy.service.TransactionService;
import xyz.strashi.PayMyBuddy.service.UserService;

@Controller
public class UserController {
	
	private UserService userService;
	
	private TransactionService transactionService; 
	
	public UserController(UserService userService, TransactionService transactionService) {
		this.userService = userService;
		this.transactionService = transactionService;
	}
	
	@GetMapping("/")
	public String home(Model model) {
		User user = userService.getUser();
		model.addAttribute("user", user);
		List<Transaction> transactionsList = transactionService.getTransactions(user);
		model.addAttribute("transactionsList", transactionsList);
		return "home";
	}
		
	@GetMapping("/transfer")
	public String transfer(Model model) {
		User user = userService.getUser();
		model.addAttribute("user", user);
		List<User> RelationshipsUserList = userService.getRelationshipsUser(user);
		model.addAttribute("friends", RelationshipsUserList);
		List<Transaction> transactionsList = transactionService.getTransactions(user);
		model.addAttribute("transactionsList", transactionsList);
	  	return "transfer";
	}
	
	@PostMapping("/transfer")
	public String transfer(String emailCreditor,  float amount) {
		System.out.println("le crediteur est :" +emailCreditor);
		System.out.println("le montant est :" +amount);
		User debitor = userService.getUser();
		User creditor = userService.findByEmail(emailCreditor);
		transactionService.executeTransaction(debitor, creditor, amount, null);
		return "redirect:/transfer";
	}
		
	@GetMapping("/profile")
	public String profile() {
			return "profile";
	}
	
	@GetMapping("/contact")
	public String contact() {
			return "contact";
	}
	
	@GetMapping("/logoff")
	public String logoff() {
			return "logoff";
	}
	
	@GetMapping("/createUser")
	public String createUser() {
		//model.addAttribute("user", new User());
		return "createUser";
	}
		
	@PostMapping("/createUser" )
	public String createUser(User user) {
		
		userService.createUser(user);
		return "redirect:/createUser";
	}
	
	@GetMapping("/addRelationship")
	public String addRelationship() {
		//model.addAttribute("createRelationshipDTO", new CreateRelationshipDTO());
		return "addRelationship";
	}
	
	@PostMapping("/addRelationship" )
	public String addRelationship( String emailUser, String emailFriend) {
		
		userService.addRelationship(emailUser,emailFriend);
		return "redirect:/addRelationship";
	}
		
	@GetMapping("/getRelationships")
	public String getRelationships(Model model, User user) {
		userService.getRelationships(user);
		return "getRelationships";
	}
	
		
}
