package xyz.strashi.PayMyBuddy.dto;

import lombok.Data;
import xyz.strashi.PayMyBuddy.model.User;
/**
 * DTO Classes are used to convert double to string in the aim to display them with two decimals
 * @author steve
 *
 */
@Data
public class TransactionDTO {
	
	private String amount;
	
	private User creditor;
	
	private String description;

}
