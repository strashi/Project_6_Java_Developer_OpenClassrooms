package xyz.strashi.PayMyBuddy.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import xyz.strashi.PayMyBuddy.model.BankAccount;

public interface BankAccountRepository extends JpaRepository<BankAccount,Long>{

}
