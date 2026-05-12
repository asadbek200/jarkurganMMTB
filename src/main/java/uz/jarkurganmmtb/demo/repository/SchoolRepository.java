package uz.jarkurganmmtb.demo.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import uz.jarkurganmmtb.demo.entitiy.School;

import java.util.List;
public interface SchoolRepository extends JpaRepository<School, Long> {
    List<School> findByActiveTrueOrderByNumberAsc();
    List<School> findByTypeAndActiveTrueOrderByNumberAsc(School.SchoolType type);
}
