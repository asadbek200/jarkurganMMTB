package uz.jarkurganmmtb.demo.entitiy;

import jakarta.persistence.*;
import lombok.*;
@Entity @Table(name="schools")
@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class School {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    private Integer number;

    @Column(nullable=false)
    private String name;

    private String address;
    private String phone;
    private String directorName;
    private Integer studentCount;
    private Integer teacherCount;

    @Enumerated(EnumType.STRING)
    private SchoolType type;

    // Joylashuv koordinatalari
    private Double latitude;
    private Double longitude;

    private Boolean active = true;

    public enum SchoolType { MAKTAB, MTM, IXTISOSLASHGAN }
}