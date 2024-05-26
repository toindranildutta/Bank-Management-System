package dev.indranil.idb.dto;

import java.math.BigDecimal;

import lombok.*;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreditDebitRequest {
	private String accountNumber;
	private BigDecimal amount;
}
