package dev.indranil.idb.service;

import dev.indranil.idb.dto.BankResponse;
import dev.indranil.idb.dto.UserRequest;

public interface UserService {
	BankResponse createAccount(UserRequest userRequest);
}
