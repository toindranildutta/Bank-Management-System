package dev.indranil.idb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import dev.indranil.idb.entity.Transaction;
@Repository
public interface TransactionRepository extends JpaRepository<Transaction, String> {

}
