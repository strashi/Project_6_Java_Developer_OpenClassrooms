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
@Table(name="relationship")
public class Relationship {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long relationshipId;
	
	@ManyToOne
	private User friend;
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	Date creationRelationshipDate;
	
	
	public Relationship(User friend, Date creationRelationshipDate) {
		this.friend = friend;
		this.creationRelationshipDate = creationRelationshipDate; 
	}
}
