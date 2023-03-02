package xyz.strashi.PayMyBuddy.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import xyz.strashi.PayMyBuddy.model.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction,Long>{

}
