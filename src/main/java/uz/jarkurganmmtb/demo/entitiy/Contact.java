package uz.jarkurganmmtb.demo.entitiy;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity @Table(name="contacts")
@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class Contact {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @Column(nullable=false)
    private String fullName;
    private String phone;
    private String email;

    @Column(columnDefinition="TEXT", nullable=false)
    private String message;

    @Enumerated(EnumType.STRING)
    private Status status;

    private LocalDateTime createdAt;

    @PrePersist
    public void prePersist() { createdAt = LocalDateTime.now(); status = Status.YANGI; }

    public enum Status { YANGI, KORIB_CHIQILDI, YOPILDI }
}
