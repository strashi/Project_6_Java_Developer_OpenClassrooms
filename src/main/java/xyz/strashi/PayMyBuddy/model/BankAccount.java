package xyz.strashi.PayMyBuddy.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;

@Entity
@Data
public class BankAccount {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long bankAccountId;
	
	private String ibanNumber;
	private String accountType;
	private float balance;

}
