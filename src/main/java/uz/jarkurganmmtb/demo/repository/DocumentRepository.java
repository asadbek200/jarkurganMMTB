package uz.jarkurganmmtb.demo.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import uz.jarkurganmmtb.demo.entitiy.Document;

import java.util.List;
public interface DocumentRepository extends JpaRepository<Document, Long> {
    List<Document> findByActiveTrueOrderByUploadedAtDesc();
}
