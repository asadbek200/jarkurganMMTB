package uz.jarkurganmmtb.demo.config;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import uz.jarkurganmmtb.demo.entitiy.Admin;
import uz.jarkurganmmtb.demo.repository.AdminRepository;
import uz.jarkurganmmtb.demo.service.SiteSettingsService;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final AdminRepository adminRepository;
    private final PasswordEncoder passwordEncoder;
    private final SiteSettingsService siteSettingsService;

    @Override
    public void run(String... args) {
        // Default admin
        if (!adminRepository.existsByUsername("admin")) {
            Admin admin = Admin.builder()
                .username("admin")
                .password(passwordEncoder.encode("Admin@2025"))
                .fullName("Administrator")
                .email("admin@jarkurganmmtb.uz")
                .active(true)
                .build();
            adminRepository.save(admin);
            System.out.println("========================================");
            System.out.println("  Admin yaratildi:");
            System.out.println("  Login:  admin");
            System.out.println("  Parol:  Admin@2025");
            System.out.println("  ⚠  Parolni o'zgartiring!");
            System.out.println("========================================");
        }
        // Default site settings
        siteSettingsService.get();
    }
}
