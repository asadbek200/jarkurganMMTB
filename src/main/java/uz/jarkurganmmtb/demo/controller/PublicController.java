package uz.jarkurganmmtb.demo.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import uz.jarkurganmmtb.demo.entitiy.Contact;
import uz.jarkurganmmtb.demo.entitiy.School;
import uz.jarkurganmmtb.demo.repository.*;
import uz.jarkurganmmtb.demo.service.EmailService;
import uz.jarkurganmmtb.demo.service.SiteSettingsService;
@Controller
@RequiredArgsConstructor
public class PublicController {

    private final NewsRepository newsRepo;
    private final SchoolRepository schoolRepo;
    private final DocumentRepository docRepo;
    private final ContactRepository contactRepo;
    private final SiteSettingsService settingsService;
    private final EmailService emailService;

    // Bosh sahifa
    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("settings", settingsService.get());
        model.addAttribute("latestNews", newsRepo.findTop5ByPublishedTrueOrderByCreatedAtDesc());
        model.addAttribute("maktablar", schoolRepo.findByTypeAndActiveTrueOrderByNumberAsc(School.SchoolType.MAKTAB));
        model.addAttribute("ixtisoslashganlar", schoolRepo.findByTypeAndActiveTrueOrderByNumberAsc(School.SchoolType.IXTISOSLASHGAN));
        model.addAttribute("mtmlar", schoolRepo.findByTypeAndActiveTrueOrderByNumberAsc(School.SchoolType.MTM));
        model.addAttribute("docs", docRepo.findByActiveTrueOrderByUploadedAtDesc());
        return "index";
    }

    // Yangiliklar sahifasi
    @GetMapping("/news")
    public String news(@RequestParam(defaultValue="0") int page, Model model) {
        model.addAttribute("settings", settingsService.get());
        model.addAttribute("newsPage", newsRepo.findByPublishedTrueOrderByCreatedAtDesc(PageRequest.of(page, 9)));
        model.addAttribute("currentPage", page);
        return "news";
    }

    // Bitta yangilik
    @GetMapping("/news/{id}")
    public String newsDetail(@PathVariable Long id, Model model) {
        return newsRepo.findById(id).map(n -> {
            if (!n.getPublished()) return "redirect:/news";
            model.addAttribute("settings", settingsService.get());
            model.addAttribute("news", n);
            model.addAttribute("latestNews", newsRepo.findTop5ByPublishedTrueOrderByCreatedAtDesc());
            return "news-detail";
        }).orElse("redirect:/news");
    }

    // Login sahifasi
    @GetMapping("/login")
    public String login() { return "login"; }

    // Murojaat yuborish (POST)
    @PostMapping("/contact")
    @ResponseBody
    public String contact(@RequestBody Contact contact) {
        contactRepo.save(contact);
        emailService.sendContactNotification(contact);
        return "ok";
    }
}
