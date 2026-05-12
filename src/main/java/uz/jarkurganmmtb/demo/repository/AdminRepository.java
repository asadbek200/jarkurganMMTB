package uz.jarkurganmmtb.demo.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import uz.jarkurganmmtb.demo.entitiy.Admin;
import java.util.Optional;
public interface AdminRepository extends JpaRepository<Admin, Long> {
    Optional<Admin> findByUsername(String username);
    boolean existsByUsername(String username);
}
