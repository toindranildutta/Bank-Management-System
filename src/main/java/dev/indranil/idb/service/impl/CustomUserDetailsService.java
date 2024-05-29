package dev.indranil.idb.service.impl;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import dev.indranil.idb.dto.BankResponse;
import dev.indranil.idb.dto.CreditDebitRequest;
import dev.indranil.idb.dto.EnquiryRequest;
import dev.indranil.idb.dto.TransferRequest;
import dev.indranil.idb.dto.UserRequest;
import dev.indranil.idb.repository.UserRepository;
import dev.indranil.idb.service.UserService;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
	
	private UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		return userRepository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException(username + "Not Found"));
	}

	

}
