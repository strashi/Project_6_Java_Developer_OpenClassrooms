package xyz.strashi.PayMyBuddy.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name ="transactions")
public class Transaction {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long transactionId;
	@ManyToOne
	private User debtor;
	@ManyToOne
	private User creditor;
	private float amount;
	private String description;
	private Date date;
	private String status;
	private float balanceDebtorBefore;
	private float balanceDebtorAfter;
	private float balanceCreditorBefore;
	private float balanceCreditorAfter;



}
