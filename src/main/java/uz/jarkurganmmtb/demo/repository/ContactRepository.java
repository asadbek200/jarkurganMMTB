package uz.jarkurganmmtb.demo.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import uz.jarkurganmmtb.demo.entitiy.Contact;

import java.util.List;
public interface ContactRepository extends JpaRepository<Contact, Long> {
    List<Contact> findAllByOrderByCreatedAtDesc();
    long countByStatus(Contact.Status status);
}
