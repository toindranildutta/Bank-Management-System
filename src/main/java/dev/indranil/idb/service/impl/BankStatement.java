package dev.indranil.idb.service.impl;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.stereotype.Component;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import dev.indranil.idb.dto.EmailDetails;
import dev.indranil.idb.entity.Transaction;
import dev.indranil.idb.entity.User;
import dev.indranil.idb.repository.TransactionRepository;
import dev.indranil.idb.repository.UserRepository;
import dev.indranil.idb.service.EmailService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@AllArgsConstructor
@Slf4j
public class BankStatement {
	
	private TransactionRepository transactionRepository;
	
	private UserRepository userRepository;
	private EmailService emailService;
	
	private static final String FILE = "/home/indranil_dutta/statement/mystatement.pdf";
	
	/**
	 * Retrieve list of transactions within a date range given an account number
	 * Generate a pdf file of the transaction
	 * Send the file via email
	 */
		public List<Transaction> generateStatement(String accountNumber, String startDate, String endDate) throws FileNotFoundException, DocumentException {
		LocalDate start = LocalDate.parse(startDate, DateTimeFormatter.ISO_DATE);
		LocalDate end = LocalDate.parse(endDate, DateTimeFormatter.ISO_DATE);
		List<Transaction> transactionList = transactionRepository.findAll().stream()
											.filter(transaction -> transaction.getAccountNumber().equals(accountNumber))
											.filter(transaction -> transaction.getCreatedAt().isAfter(start))
											.filter(transaction -> transaction.getCreatedAt().isBefore(end)).toList();
		User user = userRepository.findByAccountNumber(accountNumber);
		String customerName = user.getFirstName() + " " + user.getLastName();
		Rectangle statementSize = new Rectangle(PageSize.A4);
		Document  document = new Document(statementSize);
		log.info("Setting Size of the document");
		OutputStream outputStream = new FileOutputStream(FILE);
		PdfWriter.getInstance(document, outputStream); 
		document.open();
		
		PdfPTable bankInfoTable = new PdfPTable(1);
		PdfPCell bankName = new PdfPCell(new Phrase("ID Bank"));
		bankName.setBorder(0);
		bankName.setBackgroundColor(BaseColor.BLUE);
		bankName.setPadding(20f);
		
		PdfPCell bankAddress = new PdfPCell(new Phrase("Bankura, West Bengal, India"));
		bankAddress.setBorder(0);
		
		bankInfoTable.addCell(bankName);
		bankInfoTable.addCell(bankAddress);
		
		PdfPTable statementInfo = new PdfPTable(2);
		PdfPCell customerInfo = new PdfPCell(new Phrase("Start date: " + startDate));
		customerInfo.setBorder(0);
		
		PdfPCell statementOfAccount = new PdfPCell(new Phrase("STATEMENT OF ACCOUTN"));
		statementOfAccount.setBorder(0);
		
		PdfPCell stopDate = new PdfPCell(new Phrase("End date: " + endDate));
		stopDate.setBorder(0);
		
		PdfPCell name = new PdfPCell(new Phrase("Customer Name: " + customerName));
		name.setBorder(0);
		PdfPCell space = new PdfPCell();
		PdfPCell address = new PdfPCell(new Phrase("Customer Address: " + user.getAddress()));
		address.setBorder(0);
		
		PdfPTable transactionTable = new PdfPTable(4);
		PdfPCell date = new PdfPCell(new Phrase("DATE"));
		date.setBackgroundColor(BaseColor.BLUE);
		date.setBorder(0);
		PdfPCell transactionType = new PdfPCell(new Phrase("TRANSACTION TYPE"));
		transactionType.setBackgroundColor(BaseColor.BLUE);
		transactionType.setBorder(0);
		PdfPCell transactionaAmount = new PdfPCell(new Phrase("TRANSACTION AMOUNT"));
		transactionaAmount.setBackgroundColor(BaseColor.BLUE);
		transactionaAmount.setBorder(0);
		PdfPCell status = new PdfPCell(new Phrase("STATUS"));
		status.setBackgroundColor(BaseColor.BLUE);
		status.setBorder(0);
		
		transactionTable.addCell(date);
		transactionTable.addCell(transactionType);
		transactionTable.addCell(transactionaAmount);
		transactionTable.addCell(status);
		
		transactionList.forEach(transaction -> {
			transactionTable.addCell(new Phrase(transaction.getCreatedAt().toString()));
			transactionTable.addCell(new Phrase(transaction.getTransactionType()));
			transactionTable.addCell(new Phrase(transaction.getAmount().toString()));
			transactionTable.addCell(new Phrase(transaction.getStatus()));
		});
		
		statementInfo.addCell(customerInfo);
		statementInfo.addCell(statementOfAccount);
		statementInfo.addCell(stopDate);
		statementInfo.addCell(name);
		statementInfo.addCell(space);
		statementInfo.addCell(address);
		
		document.add(bankInfoTable);
		document.add(statementInfo);
		document.add(transactionTable);
		
		document.close();
		
		EmailDetails emailDetails = EmailDetails.builder()
				.subject("STATEMENT OF ACCOUNT")
				.recipient(user.getEmail())
				.messageBody("Kindly find your account statement attached")
				.attachment(FILE)
				.build();
		
		emailService.sendEmailWithAttachment(emailDetails);
			return transactionList;
		}

}
