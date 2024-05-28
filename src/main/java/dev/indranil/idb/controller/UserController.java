package dev.indranil.idb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.indranil.idb.dto.BankResponse;
import dev.indranil.idb.dto.CreditDebitRequest;
import dev.indranil.idb.dto.EnquiryRequest;
import dev.indranil.idb.dto.TransferRequest;
import dev.indranil.idb.dto.UserRequest;
import dev.indranil.idb.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@RestController
@RequestMapping("/api/user")
public class UserController {

	@Autowired
	UserService userService;
	
	@PostMapping
	@Operation(
			summary = "Create New User Account",
			description = "Creating new user account and assigning a new accountid"
			)
	@ApiResponse(
			responseCode = "201",
			description = "Http status 201 CREATED"
			)
	public BankResponse createAccount(@RequestBody UserRequest userRequest) {
		return userService.createAccount(userRequest);
	}
	
	@GetMapping("/balanceEnquiry")
	@Operation(
			summary = "Balance Enquiry",
			description = "Get Balance equiry for account"
			)
	@ApiResponse(
			responseCode = "200",
			description = "Http status 200 SUCCESS"
			)
	public BankResponse balanceEnquiry(@RequestBody EnquiryRequest request) {
		return userService.balanceEnquiry(request);
	}
	
	@GetMapping("/nameEnquiry") 
	public String nameEnquiry(@RequestBody EnquiryRequest request) {
		return userService.nameEnquiry(request);
	}
	
	@PostMapping("/credit")
	public BankResponse creditAccount(@RequestBody CreditDebitRequest request) {
		return userService.creditAccount(request);
	}
	
	@PostMapping("/debit")
	public BankResponse debitAccount(@RequestBody CreditDebitRequest request) {
		return userService.debitAccount(request);
	}
	
	@PostMapping("/transfer")
	public BankResponse transfer(@RequestBody TransferRequest request) {
		return userService.transfer(request);
	}
}
