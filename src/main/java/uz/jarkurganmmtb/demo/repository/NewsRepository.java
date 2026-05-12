package uz.jarkurganmmtb.demo.repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import uz.jarkurganmmtb.demo.entitiy.News;

import java.util.List;
public interface NewsRepository extends JpaRepository<News, Long> {
    Page<News> findByPublishedTrueOrderByCreatedAtDesc(Pageable p);
    List<News> findTop5ByPublishedTrueOrderByCreatedAtDesc();
    Page<News> findAllByOrderByCreatedAtDesc(Pageable p);
}
