package xyz.strashi.PayMyBuddy.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import xyz.strashi.PayMyBuddy.model.BankAccount;
import xyz.strashi.PayMyBuddy.model.Transaction;
import xyz.strashi.PayMyBuddy.model.User;
import xyz.strashi.PayMyBuddy.service.TransactionService;
import xyz.strashi.PayMyBuddy.service.UserService;

@Controller
public class HomeController {
	
	private UserService userService;
	
	private TransactionService transactionService; 
	
	public HomeController(UserService userService, TransactionService transactionService) {
		this.userService = userService;
		this.transactionService = transactionService;
	}
	
	@GetMapping("/")
	public String home(Model model, Principal principal) {
		User user = userService.findByEmail(principal.getName());
		model.addAttribute("user", user);
		
		//List<Transaction> transactionsList = transactionService.getTransactions(user);
		List<Transaction> transactionsList = transactionService.getTransactions(user);
		model.addAttribute("transactionsList", transactionsList);
		
		List<BankAccount> bankAccountsList= userService.getBankAccounts(user);
		model.addAttribute("bankAccounts", bankAccountsList);
		return "home";
	}
	
	@PostMapping("/")
	public String depositMoney(Principal principal, String bankAccount, float amount) {
		User user = userService.findByEmail(principal.getName());
		userService.depositMoney(user, bankAccount, amount);
		
		return "redirect:/";
	}
	
	@PostMapping("/bankDeposit")
	public String bankDeposit(Principal principal, String bankAccount, float amount) {
		User user = userService.findByEmail(principal.getName());
		userService.bankDeposit(user, bankAccount, amount);
		
		return "redirect:/";
	}
		
	@GetMapping("/contact")
	public String contact() {
			return "contact";
	}
	/*
	@GetMapping("/logoff")
	public String logoff() {
			return "logoff";
	}*/
	
	@GetMapping("/createUser")
	public String createUser() {
		return "createUser";
	}
		
	@PostMapping("/createUser" )
	public String createUser(User user) {
		
		userService.createUser(user);
		return "redirect:/login";
	}
	/*
	@GetMapping("/getRelationships")
	public String getRelationships(Model model, User user) {
		userService.getRelationships(user);
		return "getRelationships";
	}*/
	
	@GetMapping("/addBankAccount")
	public String addBankAccount() {
		return "addBankAccount";
	}
	
	@PostMapping("/addBankAccount" )
	public String addBanKAccount(Principal principal, String accountDescription, String ibanNumber) {
		User user = userService.findByEmail(principal.getName());
		userService.addBankAccount(user, accountDescription, ibanNumber);
		return "redirect:/";
	}
		
}
