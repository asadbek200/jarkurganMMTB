package uz.jarkurganmmtb.demo.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import uz.jarkurganmmtb.demo.entitiy.Contact;


@Service
@RequiredArgsConstructor
@Slf4j
public class EmailService {

    private final JavaMailSender mailSender;

    @Value("${app.admin.email}")
    private String adminEmail;

    @Async
    public void sendContactNotification(Contact contact) {
        try {
            SimpleMailMessage msg = new SimpleMailMessage();
            msg.setTo(adminEmail);
            msg.setSubject("jarkurganMMTB.uz — Yangi murojaat: " + contact.getFullName());
            msg.setText(
                "Yangi murojaat keldi!\n\n" +
                "Ismi: " + contact.getFullName() + "\n" +
                "Telefon: " + (contact.getPhone() != null ? contact.getPhone() : "ko'rsatilmagan") + "\n" +
                "Email: " + (contact.getEmail() != null ? contact.getEmail() : "ko'rsatilmagan") + "\n\n" +
                "Xabar:\n" + contact.getMessage() + "\n\n" +
                "---\n" +
                "Vaqt: " + contact.getCreatedAt() + "\n" +
                "Admin panelda ko'rish: http://localhost:8080/admin/contacts"
            );
            mailSender.send(msg);
            log.info("Email yuborildi: {}", adminEmail);
        } catch (Exception e) {
            log.error("Email yuborishda xatolik: {}", e.getMessage());
        }
    }
}
