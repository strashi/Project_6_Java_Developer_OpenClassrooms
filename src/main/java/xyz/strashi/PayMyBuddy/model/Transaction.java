package xyz.strashi.PayMyBuddy.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name ="transaction")
public class Transaction {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long transactionId;
	@ManyToOne
	private User debitor;
	@ManyToOne
	private User creditor;
	private double amount;
	private String description;
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date transactionDate;
	
	private String status;
	@Column(name="bal_deb_before")
	private double balanceDebitorBefore;
	@Column(name="bal_deb_after")
	private double balanceDebitorAfter;
	@Column(name="bal_cred_before")
	private double balanceCreditorBefore;
	@Column(name="bal_cred_after")
	private double balanceCreditorAfter;
	
	public Transaction(User debitor,User creditor, float amount, String description) {
		this.debitor = debitor;
		this.creditor = creditor;
		this.amount = amount;
		this.description = description;
	}



}
