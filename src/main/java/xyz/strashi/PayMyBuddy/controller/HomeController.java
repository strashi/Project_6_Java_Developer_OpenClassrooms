package xyz.strashi.PayMyBuddy.controller;

import java.security.Principal;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import xyz.strashi.PayMyBuddy.dto.UserDTO;
import xyz.strashi.PayMyBuddy.model.BankAccount;
import xyz.strashi.PayMyBuddy.model.User;
import xyz.strashi.PayMyBuddy.service.UserService;
import xyz.strashi.PayMyBuddy.service.impl.Utility;

@Controller
public class HomeController {
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

	private UserService userService;
	
		
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private Utility utility;
	
	public HomeController(UserService userService) {
		this.userService = userService;
		
	}
	
	@GetMapping("/")
	public String home(Model model, Principal principal) {
		logger.debug("GetMapping/ sollicité de HomeController");
		try {
			User user = userService.findByEmail(principal.getName());
			UserDTO userDTO = modelMapper.map(user, UserDTO.class);
			userDTO.setBalance(utility.amountFormatter(user.getBalance()));
			model.addAttribute("userDTO", userDTO);
							
			List<BankAccount> bankAccountsList= userService.getBankAccounts(user);
			model.addAttribute("bankAccounts", bankAccountsList);
			logger.info("GetMapping / réussi de HomeController");
			return "home";
		}catch (Exception e) {
			logger.error("Erreur au GetMapping / du HomeController", e);
			return null;
		}
	}
	
	@PostMapping("/")
	public String depositMoney(Principal principal, String bankAccount, double amount) {
		logger.debug("PostMapping / sollicité de HomeController");
		try {
			User user = userService.findByEmail(principal.getName());
			userService.depositMoney(user, bankAccount, amount);
			logger.info("PostMapping / réussi de HomeController");
			return "redirect:/";
		}catch (Exception e) {
			logger.error("Erreur au PostMapping / du HomeController", e);
			return null;
		}
	}
	
	@PostMapping("/bankDeposit")
	public String bankDeposit(Principal principal, String bankAccount, double amount) {
		logger.debug("PostMapping /bankDeposit sollicité de HomeController");
		try {
			User user = userService.findByEmail(principal.getName());
			userService.bankDeposit(user, bankAccount, amount);
			logger.info("PostMapping /bankDeposit réussi de HomeController");

			return "redirect:/";
		}catch (Exception e) {
			logger.error("Erreur au PostMapping /bankDeposit du HomeController", e);
			return null;
		}
	}
		
	@GetMapping("/contact")
	public String contact() {
		logger.debug("GetMapping /contact sollicité de HomeController");
		try {
			logger.info("GetMapping /contact réussi de HomeController");
			return "contact";
		}catch (Exception e) {
			logger.error("Erreur au GetMapping /contact du HomeController", e);
			return null;
		}
			
	}
		
	@GetMapping("/createUser")
	public String createUser() {
		logger.debug("GetMapping /createUser sollicité de HomeController");
		try {
			logger.info("GetMapping /createUser réussi de HomeController");
			return "createUser";
		}catch (Exception e) {
			logger.error("Erreur au GetMapping /createUser du HomeController", e);
			return null;
		}
		
	}
		
	@PostMapping("/createUser" )
	public String createUser(User user) {
		logger.debug("PostMapping /createUser sollicité de HomeController");
		try {
			userService.createUser(user);
			logger.info("PostMapping /createUser réussi de HomeController");
			return "redirect:/login";
		}catch (Exception e) {
			logger.error("Erreur au PostMapping /createUser du HomeController", e);
			return null;
		}
	}
		
	@GetMapping("/addBankAccount")
	public String addBankAccount() {
		logger.debug("GetMapping /addBankAccount sollicité de HomeController");
		try {
			logger.info("GetMapping /addBankAccount réussi de HomeController");
			return "addBankAccount";

		}catch (Exception e) {
			logger.error("Erreur au GetMapping /addBankAccount du HomeController", e);
			return null;
		}
	}
	
	@PostMapping("/addBankAccount" )
	public String addBanKAccount(Principal principal, String accountDescription, String ibanNumber) {
		logger.debug("PostMapping /addBankAccount sollicité de HomeController");
		try {
			
			User user = userService.findByEmail(principal.getName());
			userService.addBankAccount(user, accountDescription, ibanNumber);
			logger.info("PostMapping /addBankAccount réussi de HomeController");
			return "redirect:/";
		}catch (Exception e) {
			logger.error("Erreur au PostMapping /addBankAccount du HomeController", e);
			return null;
		}
		
	}
		
}
