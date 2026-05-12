package uz.jarkurganmmtb.demo.entitiy;

import jakarta.persistence.*;
import lombok.*;
@Entity @Table(name="site_settings")
@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class SiteSettings {
    @Id
    private Long id = 1L;

    private String siteName;
    private String siteTitle;
    private String siteDescription;

    // Bo'lim haqida
    @Column(columnDefinition="LONGTEXT")
    private String aboutText;
    private String address;
    private String phone;
    private String email;
    private String workHours;

    // Rahbariyat
    private String leaderName;
    private String leaderPosition;
    private String leaderPhone;

    private String deputyName;
    private String deputyPosition;
    private String deputyPhone;

    // Statistika (admin kiritadi)
    private Integer schoolCount;
    private Integer mtmCount;
    private Integer studentCount;
    private Integer teacherCount;

    // Ijtimoiy tarmoqlar
    private String telegram;
    private String facebook;
    private String instagram;
}
