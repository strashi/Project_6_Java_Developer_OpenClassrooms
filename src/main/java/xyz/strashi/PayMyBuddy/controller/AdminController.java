package xyz.strashi.PayMyBuddy.controller;

import java.security.Principal;
import java.util.List;

import javax.annotation.security.RolesAllowed;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import xyz.strashi.PayMyBuddy.model.BankAccount;
import xyz.strashi.PayMyBuddy.model.Transaction;
import xyz.strashi.PayMyBuddy.model.User;
import xyz.strashi.PayMyBuddy.service.TransactionService;
import xyz.strashi.PayMyBuddy.service.UserService;

@Controller
public class AdminController {
	
private UserService userService;
	
	private TransactionService transactionService; 
	
	public AdminController(UserService userService, TransactionService transactionService) {
		this.userService = userService;
		this.transactionService = transactionService;
	}
	@RolesAllowed("ADMIN")
	@GetMapping("/admin")
	public String admin(Model model, Principal principal) {
		User user = userService.findByEmail(principal.getName());
		model.addAttribute("user", user);
		
		List<Transaction> transactionsList = transactionService.getTransactions(user);
		model.addAttribute("transactionsList", transactionsList);
		
		List<BankAccount> bankAccountsList= userService.getBankAccounts(user);
		model.addAttribute("bankAccounts", bankAccountsList);
		return "admin";
	}

}
