package xyz.strashi.PayMyBuddy.model;

import java.math.BigInteger;
import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Relationship {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long relationshipId;
	
	private Long userIdFriend;
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	LocalDateTime creationRelationshipDate;
	
	public Relationship(Long userIdFriend,LocalDateTime creationRelationshipDate) {
		this.userIdFriend = userIdFriend;
		this.creationRelationshipDate = creationRelationshipDate; 
	}
}
