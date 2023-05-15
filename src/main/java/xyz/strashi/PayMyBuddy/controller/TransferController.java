package xyz.strashi.PayMyBuddy.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import xyz.strashi.PayMyBuddy.dto.TransactionDTO;
import xyz.strashi.PayMyBuddy.dto.UserDTO;
import xyz.strashi.PayMyBuddy.model.Transaction;
import xyz.strashi.PayMyBuddy.model.User;
import xyz.strashi.PayMyBuddy.service.TransactionService;
import xyz.strashi.PayMyBuddy.service.UserService;
import xyz.strashi.PayMyBuddy.service.impl.Utility;
/**
* The transaction page. I used the class transactionDTO to change amount from double to String
* @author steve
*
*/
@Controller
public class TransferController {
	
	private static final Logger logger = LoggerFactory.getLogger(TransferController.class);
	
	private UserService userService;
	
	private TransactionService transactionService; 
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private Utility utility;
	
	public TransferController(UserService userService, TransactionService transactionService) {
		this.userService = userService;
		this.transactionService = transactionService;
	}
	
	@GetMapping("/transfer")
	public String transfer(Model model, Principal principal) {
		logger.debug("GetMapping /transfer sollicité de TransferController");
		try {
			User user = userService.findByEmail(principal.getName());
			UserDTO userDTO = modelMapper.map(user, UserDTO.class);
			userDTO.setBalance(utility.amountFormatter(user.getBalance()));
			model.addAttribute("userDTO", userDTO);
		
			List<User> RelationshipsUserList = userService.getRelationshipsUser(user);
			model.addAttribute("friends", RelationshipsUserList);
			List<Transaction> transactionsList = transactionService.getTransactions(user);
			List<TransactionDTO> transactionDTOsList = new ArrayList<>();
			for(Transaction transaction : transactionsList) {
				TransactionDTO transactionDTO = modelMapper.map(transaction, TransactionDTO.class);
				transactionDTO.setAmount(utility.amountFormatter(transaction.getAmount()));
				transactionDTOsList.add(transactionDTO);
			}
			model.addAttribute("transactionDTOsList", transactionDTOsList);
			logger.info("GetMapping /transfer réussi de TransferController");
		  	return "transfer";
		}catch (Exception e) {
			logger.error("Erreur au GetMapping /transfer du TransferController", e);
			return null;
		}
	}
	
	@PostMapping("/transfer")
	public String transfer(Principal principal, String emailCreditor,  double amount, 
			String description, RedirectAttributes redirAttrs) {
		logger.debug("PostMapping /transfer sollicité de TransferController");
		try {
			User debitor = userService.findByEmail(principal.getName());
			User creditor = userService.findByEmail(emailCreditor);
			if((debitor.getBalance() - amount)>= 0) {
				transactionService.executeTransaction(debitor, creditor, amount, description,true);
				logger.info("PostMapping /transfer réussi de TransferController");
			}else {
				logger.info("PostMapping /transfer échoué car balance < 0 de TransferController");
				redirAttrs.addFlashAttribute("error","Solde insuffisant");
			}
			
			return "redirect:/transfer";
		}catch (Exception e) {
			logger.error("Erreur au PostMapping /transfer du TransferController", e);
			return null;
		}
	}
	
	@GetMapping("/addRelationship")
	public String addRelationship() {
		logger.debug("GetMapping /addRelationship sollicité de TransferController");
		try {
			logger.info("GetMapping /addRelationship réussi de TransferController");
			return "addRelationship";
		}catch (Exception e) {
			logger.error("Erreur au PostMapping /addRelationship du TransferController", e);
			return null;
		}
	}
	
	@PostMapping("/addRelationship" )
	public String addRelationship(Principal principal, String emailFriend) {
		logger.debug("PostMapping /addRelationship sollicité de TransferController");
		try{
			String emailUser = principal.getName();
			userService.addRelationship(emailUser,emailFriend);
			logger.info("PostMapping /addRelationship réussi de TransferController");
			return "redirect:/transfer";
		}catch (Exception e) {
			logger.error("Erreur au PostMapping /addRelationship du TransferController", e);
			return null;
		}
	}
}
