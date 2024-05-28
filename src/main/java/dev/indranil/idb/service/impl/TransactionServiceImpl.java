package dev.indranil.idb.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dev.indranil.idb.dto.TransactionDto;
import dev.indranil.idb.entity.Transaction;
import dev.indranil.idb.repository.TransactionRepository;
import dev.indranil.idb.service.TransactionalService;

@Service
public class TransactionServiceImpl implements TransactionalService {
	@Autowired
	TransactionRepository transactionRepository;
	
	@Override
	public void saveTransaction(TransactionDto transactionDto) {
		Transaction transaction = Transaction.builder()
				.transactionType(transactionDto.getTransactionType())
				.accountNumber(transactionDto.getAccountNumber())
				.amount(transactionDto.getAmount())
				.status("SUCCESS")
				.build();
		
		transactionRepository.save(transaction);
		System.out.println("Transaction saved successfully");		
	}

}
