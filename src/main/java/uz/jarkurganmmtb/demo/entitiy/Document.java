package uz.jarkurganmmtb.demo.entitiy;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity @Table(name="documents")
@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class Document {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @Column(nullable=false)
    private String title;
    private String description;
    private String fileName;
    private String filePath;
    private String fileType;
    private Long fileSize;

    @Enumerated(EnumType.STRING)
    private Category category;

    private Boolean active = true;
    private LocalDateTime uploadedAt;

    @PrePersist
    public void prePersist() { uploadedAt = LocalDateTime.now(); }

    public enum Category { ISH_REJASI, HISOBOT, QOIDA, ARIZA_NAMUNA, BOSHQA }
}
