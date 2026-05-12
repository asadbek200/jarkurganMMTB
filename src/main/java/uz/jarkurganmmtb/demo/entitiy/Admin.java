package uz.jarkurganmmtb.demo.entitiy;

import jakarta.persistence.*;
import lombok.*;

@Entity @Table(name="admins")
@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class Admin {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @Column(unique=true, nullable=false)
    private String username;

    @Column(nullable=false)
    private String password;

    private String fullName;
    private String email;
    private Boolean active = true;
}
