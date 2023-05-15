package xyz.strashi.PayMyBuddy.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.security.RolesAllowed;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import xyz.strashi.PayMyBuddy.dto.TransactionDTO;
import xyz.strashi.PayMyBuddy.dto.UserDTO;
import xyz.strashi.PayMyBuddy.model.BankAccount;
import xyz.strashi.PayMyBuddy.model.Transaction;
import xyz.strashi.PayMyBuddy.model.User;
import xyz.strashi.PayMyBuddy.service.TransactionService;
import xyz.strashi.PayMyBuddy.service.UserService;
import xyz.strashi.PayMyBuddy.tools.Utility;

@Controller
public class AdminController {
	
	private static final Logger logger = LoggerFactory.getLogger(AdminController.class);
	
	private UserService userService;
	
	private TransactionService transactionService; 
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private Utility utility;
	
	public AdminController(UserService userService, TransactionService transactionService) {
		this.userService = userService;
		this.transactionService = transactionService;
	}
	@RolesAllowed("ADMIN")
	@GetMapping("/admin")
	public String admin(Model model, Principal principal) {
		logger.debug("GetMapping sollicité de AdminController");
		try {
			User user = userService.findByEmail(principal.getName());
			UserDTO userDTO = modelMapper.map(user, UserDTO.class);
			userDTO.setBalance(utility.amountFormatter(user.getBalance()));
			model.addAttribute("userDTO", userDTO);
			
			List<Transaction> transactionsList = transactionService.getTransactions(user);
			List<TransactionDTO> transactionDTOsList = new ArrayList<>();
			for(Transaction transaction : transactionsList) {
				TransactionDTO transactionDTO = modelMapper.map(transaction, TransactionDTO.class);
				transactionDTO.setAmount(utility.amountFormatter(transaction.getAmount()));
				transactionDTOsList.add(transactionDTO);
			}
			model.addAttribute("transactionDTOsList", transactionDTOsList);
			
			List<BankAccount> bankAccountsList= userService.getBankAccounts(user);
			model.addAttribute("bankAccounts", bankAccountsList);
			logger.info("GetMapping réussi de AdminController");
			return "admin";
			
		}catch (Exception e) {
			logger.error("Erreur au AdminController", e);
			return null;
		}
	}

}
