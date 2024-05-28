package dev.indranil.idb.dto;

import java.math.BigDecimal;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccountInfo {
	@Schema(name = "User account name")
	private String accountName;
	@Schema(name = "User account balance")
	private BigDecimal accountBalance;
	@Schema(name = "User account number")
	private String accountNumber;
}
