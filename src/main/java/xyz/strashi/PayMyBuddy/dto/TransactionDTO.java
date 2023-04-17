package xyz.strashi.PayMyBuddy.dto;

import lombok.Data;
import xyz.strashi.PayMyBuddy.model.User;

@Data
public class TransactionDTO {
	
		
	private String amount;
	
	//@ManyToOne
	private User creditor;
	
	private String description;

}
