package project.bioinformatics.service;

import project.bioinformatics.dto.BioUserRegisterRequestDto;

public interface EmailService {
    void sendWelcomeEmail(BioUserRegisterRequestDto user);

    void sendResetPasswordLink(String email, String link);

    void sendChangePasswordEmails(String username, String oldEmail, String newEmail);

    void sendSuccessfulPasswordChangeEmail(String username, String email);
}
