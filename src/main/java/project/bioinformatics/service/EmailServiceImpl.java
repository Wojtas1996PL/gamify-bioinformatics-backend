package project.bioinformatics.service;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import project.bioinformatics.dto.BioUserRegisterRequestDto;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {
    private final JavaMailSender mailSender;

    @Override
    public void sendWelcomeEmail(BioUserRegisterRequestDto user) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(user.getEmail());
        message.setSubject("Welcome to Bioinformatics");
        message.setText("Hello, " + user.getUsername() + "! Welcome to our website.");
        message.setFrom("bioinftesting@outlook.com");
        mailSender.send(message);
    }

    @Override
    public void sendResetPasswordLink(String email, String link) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Bioinformatics Password Reset");
        message.setText("Hello, Please follow this link to reset "
                + "your password: " + link);
        message.setFrom("bioinftesting@outlook.com");
        mailSender.send(message);
    }

    @Override
    public void sendChangePasswordEmails(String username, String oldEmail, String newEmail) {
        SimpleMailMessage oldEmailMessage = new SimpleMailMessage();
        oldEmailMessage.setTo(oldEmail);
        oldEmailMessage.setSubject("Bioinformatics email change");
        oldEmailMessage.setText("Hello " + username + "! This email "
                + "is to confirm successful change of email "
                + "address to your new email: " + newEmail);
        oldEmailMessage.setFrom("bioinftesting@outlook.com");

        SimpleMailMessage newEmailMessage = new SimpleMailMessage();
        newEmailMessage.setTo(newEmail);
        newEmailMessage.setSubject("Bioinformatics email change");
        newEmailMessage.setText("Hello " + username + "! This email "
                + "is to confirm successful change of the email "
                + "address for your Bioinformatics account");
        newEmailMessage.setFrom("bioinftesting@outlook.com");

        mailSender.send(oldEmailMessage);
        mailSender.send(newEmailMessage);
    }
}
