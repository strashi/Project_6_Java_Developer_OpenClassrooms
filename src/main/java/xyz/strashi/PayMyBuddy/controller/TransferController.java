package xyz.strashi.PayMyBuddy.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import xyz.strashi.PayMyBuddy.model.Transaction;
import xyz.strashi.PayMyBuddy.model.User;
import xyz.strashi.PayMyBuddy.service.TransactionService;
import xyz.strashi.PayMyBuddy.service.UserService;

@Controller
public class TransferController {
	
	private UserService userService;
	
	private TransactionService transactionService; 
	
	public TransferController(UserService userService, TransactionService transactionService) {
		this.userService = userService;
		this.transactionService = transactionService;
	}
	
	@GetMapping("/transfer")
	public String transfer(Model model, Principal principal) {
		User user = userService.findByEmail(principal.getName());
		model.addAttribute("user", user);
		List<User> RelationshipsUserList = userService.getRelationshipsUser(user);
		model.addAttribute("friends", RelationshipsUserList);
		List<Transaction> transactionsList = transactionService.getTransactions(user);
		model.addAttribute("transactionsList", transactionsList);
	  	return "transfer";
	}
	
	@PostMapping("/transfer")
	public String transfer(Principal principal, String emailCreditor,  float amount, String description) {
		User debitor = userService.findByEmail(principal.getName());
		User creditor = userService.findByEmail(emailCreditor);
		transactionService.executeTransaction(debitor, creditor, amount, description,true);
		return "redirect:/transfer";
	}
	
	@GetMapping("/addRelationship")
	public String addRelationship() {
		return "addRelationship";
	}
	
	@PostMapping("/addRelationship" )
	public String addRelationship(Principal principal, String emailFriend) {
		String emailUser = principal.getName();
		userService.addRelationship(emailUser,emailFriend);
		return "redirect:/transfer";
	}
}
