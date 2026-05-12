package uz.jarkurganmmtb.demo.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.jarkurganmmtb.demo.entitiy.SiteSettings;
import uz.jarkurganmmtb.demo.repository.SiteSettingsRepository;


@Service
@RequiredArgsConstructor
public class SiteSettingsService {

    private final SiteSettingsRepository repo;

    public SiteSettings get() {
        return repo.findById(1L).orElseGet(() -> {
            SiteSettings s = SiteSettings.builder()
                .id(1L)
                .siteName("jarkurganMMTB.uz")
                .siteTitle("Jarqo'rg'on tumani maktabgacha va maktab ta'limi bo'limi")
                .siteDescription("Surxondaryo viloyati, Jarqo'rg'on tumani ta'lim bo'limi rasmiy veb-sayti")
                .aboutText("Jarqo'rg'on tumani maktabgacha va maktab ta'limi bo'limi 2000-yildan faoliyat yuritib kelmoqda. Bo'lim tuman miqyosidagi barcha ta'lim muassasalarini boshqaradi va nazorat qiladi.")
                .address("Surxondaryo viloyati, Jarqo'rg'on tumani, Mustaqillik ko'chasi, 1")
                .phone("+998 77 123-45-67")
                .email("jarqorgon.mmtb@edu.uz")
                .workHours("Dushanba–Juma: 9:00–18:00")
                .leaderName("F.I.O")
                .leaderPosition("Bo'lim boshlig'i")
                .leaderPhone("+998 77 000-00-00")
                .deputyName("F.I.O")
                .deputyPosition("Bo'lim boshlig'i o'rinbosari")
                .deputyPhone("+998 77 000-00-00")
                .schoolCount(42)
                .mtmCount(18)
                .studentCount(14300)
                .teacherCount(1200)
                .build();
            return repo.save(s);
        });
    }

    public SiteSettings save(SiteSettings settings) {
        settings.setId(1L);
        return repo.save(settings);
    }
}
