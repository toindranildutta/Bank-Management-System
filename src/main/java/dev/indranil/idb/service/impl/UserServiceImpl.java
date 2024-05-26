package dev.indranil.idb.service.impl;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dev.indranil.idb.dto.AccountInfo;
import dev.indranil.idb.dto.BankResponse;
import dev.indranil.idb.dto.EmailDetails;
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

}
