package dev.indranil.idb.service;

import dev.indranil.idb.dto.BankResponse;
import dev.indranil.idb.dto.CreditDebitRequest;
import dev.indranil.idb.dto.EnquiryRequest;
import dev.indranil.idb.dto.TransferRequest;
import dev.indranil.idb.dto.UserRequest;

public interface UserService {
	BankResponse createAccount(UserRequest userRequest);
	BankResponse balanceEnquiry(EnquiryRequest enquiryRequest);
	String nameEnquiry(EnquiryRequest enquiryRequest);
	BankResponse creditAccount(CreditDebitRequest request);
	BankResponse debitAccount(CreditDebitRequest request);
	BankResponse transfer(TransferRequest request);
}
