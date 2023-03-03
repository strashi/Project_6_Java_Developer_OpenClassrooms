package xyz.strashi.PayMyBuddy.model;

import java.util.Date;

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
@Table(name ="transactions")
public class Transaction {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long transactionId;
	@ManyToOne
	private User debitor;
	@ManyToOne
	private User creditor;
	private float amount;
	private String description;
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date date;
	private Enum<Status> status;
	private float balanceDebitorBefore;
	private float balanceDebitorAfter;
	private float balanceCreditorBefore;
	private float balanceCreditorAfter;



}
