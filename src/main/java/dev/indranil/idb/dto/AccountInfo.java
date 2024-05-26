package dev.indranil.idb.dto;

import java.math.BigDecimal;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccountInfo {
	private String accountName;
	private BigDecimal accountBalance;
	private String accountNumber;
}
