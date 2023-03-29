package xyz.strashi.PayMyBuddy.repository;

import java.util.List;

import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import xyz.strashi.PayMyBuddy.model.Transaction;
import xyz.strashi.PayMyBuddy.model.User;

public interface TransactionRepository extends JpaRepository<Transaction,Long>{

	List<Transaction> findByDebitor(User user);
	/*
	@Query(value = "SELECT * FROM transaction t WHERE t.creditor_user_id = :user_id OR t.debitor_user_id = :user_id", nativeQuery = true)
	public List<Transaction> getAllTransactions(@Param("user_id")Long user_id);
	*/
	@Query("SELECT t FROM Transaction t WHERE t.creditor = :user OR t.debitor = :user")
	public List<Transaction> getAllTransactions(@Param("user")User user);

}
