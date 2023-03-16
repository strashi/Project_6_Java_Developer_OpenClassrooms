package xyz.strashi.PayMyBuddy.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import xyz.strashi.PayMyBuddy.model.Transaction;
import xyz.strashi.PayMyBuddy.model.User;

public interface TransactionRepository extends JpaRepository<Transaction,Long>{

	List<Transaction> findByDebitor(User user);

}
