package dev.indranil.idb.service;

import dev.indranil.idb.dto.TransactionDto;

public interface TransactionalService {
	void saveTransaction(TransactionDto transactionDto);
}
