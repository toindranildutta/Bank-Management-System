package dev.indranil.idb.service;

import dev.indranil.idb.dto.EmailDetails;

public interface EmailService {
	void sendEmailAlert(EmailDetails emailDetails);
}
