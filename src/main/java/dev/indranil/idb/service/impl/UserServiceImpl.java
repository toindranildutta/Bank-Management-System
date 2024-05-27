package dev.indranil.idb.service.impl;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dev.indranil.idb.dto.AccountInfo;
import dev.indranil.idb.dto.BankResponse;
import dev.indranil.idb.dto.CreditDebitRequest;
import dev.indranil.idb.dto.EmailDetails;
import dev.indranil.idb.dto.EnquiryRequest;
import dev.indranil.idb.dto.TransferRequest;
import dev.indranil.idb.dto.UserRequest;
import dev.indranil.idb.entity.User;
import dev.indranil.idb.repository.UserRepository;
import dev.indranil.idb.service.EmailService;
import dev.indranil.idb.service.UserService;
import dev.indranil.idb.utils.AccountUtils;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	UserRepository userRepository;
	
	@Autowired
	EmailService emailService;

	/**
	 * Create an account - save the new user in db check if user already have an
	 * account
	 */

	@Override
	public BankResponse createAccount(UserRequest userRequest) {
		if (userRepository.existsByEmail(userRequest.getEmail())) {
			return BankResponse.builder()
					.responseCode(AccountUtils.ACCOUNT_EXISTS_CODE)
					.responseMessage(AccountUtils.ACCOUNT_EXISTS_MESSAGE)
					.accountInfo(null).build();
		};
		
		User newuser = User.builder()
				.firstName(userRequest.getFirstName())
				.lastName(userRequest.getLastName())
				.otherName(userRequest.getOtherName())
				.gender(userRequest.getGender())
				.address(userRequest.getAddress())
				.stateOfOrigin(userRequest.getStateOfOrigin())
				.accountNumber(AccountUtils.generateAccountNumber())
				.accountBalance(BigDecimal.ZERO)
				.email(userRequest.getEmail())
				.phoneNumber(userRequest.getPhoneNumber())
				.alternativePhoneNumber(userRequest.getAlternativePhoneNumber())
				.status("ACTIVE").build();
		
		User savedUser = userRepository.save(newuser);
		
		// Save Email alerts
		EmailDetails emailDetails = EmailDetails.builder()
				.recipient(userRequest.getEmail())
				.messageBody("Congratulations! Your account is successfully created. \nYour Account Details: \n"
						+ "Account Name: " + savedUser.getFirstName() + " " + savedUser.getLastName() + " " + savedUser.getOtherName()
						+ "\nAccount Number: " + savedUser.getAccountNumber())
				.subject("Account Created")
				.build();
		
		emailService.sendEmailAlert(emailDetails);
		
		return BankResponse.builder()
				.responseCode(AccountUtils.ACCOUNT_CREATION_CODE)
				.responseMessage(AccountUtils.ACCOUNT_CREATION_MESSAGE)
				.accountInfo(AccountInfo.builder()
						.accountName(savedUser.getFirstName() + " " + savedUser.getLastName() + " " + savedUser.getOtherName())
						.accountBalance(savedUser.getAccountBalance())
						.accountNumber(savedUser.getAccountNumber())
						.build())
				.build();
	}

	@Override
	public BankResponse balanceEnquiry(EnquiryRequest request) {
		// Check if the provided account number exists
		boolean isAccountExists = userRepository.existsByAccountNumber(request.getAccountNumber());
		if(!isAccountExists) {
			return BankResponse.builder()
					.responseCode(AccountUtils.ACCOUNT_NOT_EXISTS_CODE)
					.responseMessage(AccountUtils.ACCOUNT_NOT_EXISTS_MESSAGE)
					.accountInfo(null)
					.build();
		}
		
		User foundUser = userRepository.findByAccountNumber(request.getAccountNumber());
		return BankResponse.builder()
				.responseCode(AccountUtils.ACCOUNT_FOUND_CODE)
				.responseMessage(AccountUtils.ACCOUNT_FOUND_MESSAGE)
				.accountInfo(AccountInfo.builder()
						.accountBalance(foundUser.getAccountBalance())
						.accountNumber(request.getAccountNumber())
						.accountName(foundUser.getFirstName() + " " + foundUser.getLastName())
						.build()
						)
				.build();				
	}

	@Override
	public String nameEnquiry(EnquiryRequest request) {
		boolean isAccountExists = userRepository.existsByAccountNumber(request.getAccountNumber());
		if(!isAccountExists) {
			return AccountUtils.ACCOUNT_NOT_EXISTS_MESSAGE;
		}
		
		User foundUser = userRepository.findByAccountNumber(request.getAccountNumber());
		return foundUser.getFirstName() + " " + foundUser.getLastName();
		
	}

	@Override
	public BankResponse creditAccount(CreditDebitRequest request) {
		boolean isAccountExists = userRepository.existsByAccountNumber(request.getAccountNumber());
		if(!isAccountExists) {
			return BankResponse.builder()
					.responseCode(AccountUtils.ACCOUNT_NOT_EXISTS_CODE)
					.responseMessage(AccountUtils.ACCOUNT_NOT_EXISTS_MESSAGE)
					.accountInfo(null)
					.build();
		}
		
		User userToCredit = userRepository.findByAccountNumber(request.getAccountNumber());
		userToCredit.setAccountBalance(userToCredit.getAccountBalance().add(request.getAmount()));
		userRepository.save(userToCredit);
		
		return BankResponse.builder()
				.responseCode(AccountUtils.ACCOUNT_CREDITED_SUCCESS_CODE)
				.responseMessage(AccountUtils.ACCOUNT_CREDITED_SUCCESS_MESSAGE)
				.accountInfo(AccountInfo.builder()
						.accountName(userToCredit.getFirstName() + " " + userToCredit.getLastName())
						.accountNumber(request.getAccountNumber())
						.accountBalance(userToCredit.getAccountBalance())
						.build())
				.build();
	}

	@Override
	public BankResponse debitAccount(CreditDebitRequest request) {
		// Check if account exists and amount intend to withdraw is not more than balance amount
		boolean isAccountExists = userRepository.existsByAccountNumber(request.getAccountNumber());
		if(!isAccountExists) {
			return BankResponse.builder()
					.responseCode(AccountUtils.ACCOUNT_NOT_EXISTS_CODE)
					.responseMessage(AccountUtils.ACCOUNT_NOT_EXISTS_MESSAGE)
					.accountInfo(null)
					.build();
		}
		
		User userToDebit = userRepository.findByAccountNumber(request.getAccountNumber());
		
		if(userToDebit.getAccountBalance().compareTo(request.getAmount()) < 0) {
			return BankResponse.builder()
					.responseCode(AccountUtils.INSUFFICIENT_BALANCE_CODE)
					.responseMessage(AccountUtils.INSUFFICIENT_BALANCE_MESSAGE)
					.accountInfo(null)
					.build();
		}
			userToDebit.setAccountBalance(userToDebit.getAccountBalance().subtract(request.getAmount()));
			userRepository.save(userToDebit);
			return BankResponse.builder()
					.responseCode(AccountUtils.ACCOUNT_DEBITED_SUCCESS_CODE)
					.responseMessage(AccountUtils.ACCOUNT_DEBITED_SUCCESS_MESSAGE)
					.accountInfo(AccountInfo.builder()
							.accountBalance(userToDebit.getAccountBalance())
							.accountName(userToDebit.getFirstName() + " " + userToDebit.getLastName())
							.accountNumber(request.getAccountNumber())
							.build())
					.build();
		
	}

	@Override
	public BankResponse transfer(TransferRequest request) {
		boolean isDestinationAccountExists = userRepository.existsByAccountNumber(request.getDestinationAccountNumber());

		if(!isDestinationAccountExists) {
			return BankResponse.builder()
					.responseCode(AccountUtils.ACCOUNT_NOT_EXISTS_CODE)
					.responseMessage(AccountUtils.ACCOUNT_NOT_EXISTS_MESSAGE)
					.accountInfo(null)
					.build();
		}
		
		User sourceAccountUser = userRepository.findByAccountNumber(request.getSourceAccountNumber());
		
		if(sourceAccountUser.getAccountBalance().compareTo(request.getAmount()) < 0) {
			return BankResponse.builder()
					.responseCode(AccountUtils.INSUFFICIENT_BALANCE_CODE)
					.responseMessage(AccountUtils.INSUFFICIENT_BALANCE_MESSAGE)
					.accountInfo(null)
					.build();
		}
		
		sourceAccountUser.setAccountBalance(sourceAccountUser.getAccountBalance().subtract(request.getAmount()));
		userRepository.save(sourceAccountUser);
		EmailDetails debitAlert = EmailDetails.builder()
				.subject("Debit Alert")
				.recipient(sourceAccountUser.getEmail())
				.messageBody("The Sum of " + request.getAmount() + " has been deducted from your account! Your Current Balance is " + sourceAccountUser.getAccountBalance())
				.build();
		
		emailService.sendEmailAlert(debitAlert);
		
		User destinationAccountUser = userRepository.findByAccountNumber(request.getDestinationAccountNumber());
		destinationAccountUser.setAccountBalance(destinationAccountUser.getAccountBalance().add(request.getAmount()));
		userRepository.save(destinationAccountUser);
		
		EmailDetails creditAlert = EmailDetails.builder()
				.subject("Credit Alert")
				.recipient(sourceAccountUser.getEmail())
				.messageBody("The Sum of " + request.getAmount() + " has been credited to your account! Your Current Balance is " + destinationAccountUser.getAccountBalance())
				.build();
		emailService.sendEmailAlert(creditAlert);
		
		return BankResponse.builder()
				.responseCode(AccountUtils.TRANSFER_SUCCESS_CODE)
				.responseMessage(AccountUtils.TRANSFER_SUCCESS_MESSAGE)
				.accountInfo(null)
				.build();
	}

	
}
