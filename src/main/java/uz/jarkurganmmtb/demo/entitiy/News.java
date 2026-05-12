package uz.jarkurganmmtb.demo.entitiy;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;


@Entity @Table(name="news")
@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class News {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @Column(nullable=false)
    private String title;

    @Column(columnDefinition="LONGTEXT", nullable=false)
    private String content;

    private String shortDesc;
    private String imageUrl;

    @Enumerated(EnumType.STRING)
    private Category category;

    private Boolean published = false;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @PrePersist
    public void prePersist() { createdAt = LocalDateTime.now(); updatedAt = LocalDateTime.now(); }
    @PreUpdate
    public void preUpdate() { updatedAt = LocalDateTime.now(); }

    public enum Category { YANGILIK, ELON, OLIMPIADA, TADBIR }
}
