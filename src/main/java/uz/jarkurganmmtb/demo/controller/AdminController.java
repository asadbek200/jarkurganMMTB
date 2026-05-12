package uz.jarkurganmmtb.demo.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import uz.jarkurganmmtb.demo.entitiy.*;
import uz.jarkurganmmtb.demo.repository.*;
import uz.jarkurganmmtb.demo.service.*;


import java.io.IOException;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final NewsRepository newsRepo;
    private final SchoolRepository schoolRepo;
    private final DocumentRepository docRepo;
    private final ContactRepository contactRepo;
    private final SiteSettingsService settingsService;
    private final FileStorageService fileService;

    // ===== DASHBOARD =====
    @GetMapping({"/", "/dashboard"})
    public String dashboard(Model model) {
        model.addAttribute("settings", settingsService.get());
        model.addAttribute("newsCount", newsRepo.count());
        model.addAttribute("schoolCount", schoolRepo.findByActiveTrueOrderByNumberAsc().size());
        model.addAttribute("contactCount", contactRepo.count());
        model.addAttribute("newContacts", contactRepo.countByStatus(Contact.Status.YANGI));
        model.addAttribute("latestContacts", contactRepo.findAllByOrderByCreatedAtDesc()
                .stream().limit(5).toList());
        model.addAttribute("latestNews", newsRepo.findAllByOrderByCreatedAtDesc(PageRequest.of(0,5)).getContent());
        return "admin/dashboard";
    }

    // ===== YANGILIKLAR =====
    @GetMapping("/news")
    public String newsList(@RequestParam(defaultValue="0") int page, Model model) {
        model.addAttribute("settings", settingsService.get());
        model.addAttribute("newsPage", newsRepo.findAllByOrderByCreatedAtDesc(PageRequest.of(page, 10)));
        model.addAttribute("currentPage", page);
        return "admin/news-list";
    }

    @GetMapping("/news/new")
    public String newsForm(Model model) {
        model.addAttribute("settings", settingsService.get());
        model.addAttribute("news", new News());
        model.addAttribute("categories", News.Category.values());
        return "admin/news-form";
    }

    @GetMapping("/news/edit/{id}")
    public String newsEdit(@PathVariable Long id, Model model) {
        return newsRepo.findById(id).map(n -> {
            model.addAttribute("settings", settingsService.get());
            model.addAttribute("news", n);
            model.addAttribute("categories", News.Category.values());
            return "admin/news-form";
        }).orElse("redirect:/admin/news");
    }

    @PostMapping("/news/save")
    public String newsSave(@ModelAttribute News news,
                           @RequestParam(required=false) MultipartFile imageFile,
                           RedirectAttributes ra) throws IOException {
        if (imageFile != null && !imageFile.isEmpty()) {
            String name = fileService.store(imageFile);
            news.setImageUrl("/uploads/" + name);
        }
        newsRepo.save(news);
        ra.addFlashAttribute("success", "Yangilik saqlandi!");
        return "redirect:/admin/news";
    }

    @PostMapping("/news/delete/{id}")
    public String newsDelete(@PathVariable Long id, RedirectAttributes ra) {
        newsRepo.deleteById(id);
        ra.addFlashAttribute("success", "Yangilik o'chirildi");
        return "redirect:/admin/news";
    }

    @PostMapping("/news/publish/{id}")
    public String newsPublish(@PathVariable Long id) {
        newsRepo.findById(id).ifPresent(n -> {
            n.setPublished(!n.getPublished());
            newsRepo.save(n);
        });
        return "redirect:/admin/news";
    }

    // ===== MAKTABLAR =====
    @GetMapping("/schools")
    public String schoolList(Model model) {
        model.addAttribute("settings", settingsService.get());
        model.addAttribute("schools", schoolRepo.findByActiveTrueOrderByNumberAsc());
        model.addAttribute("types", School.SchoolType.values());
        return "admin/schools";
    }

    @PostMapping("/schools/save")
    public String schoolSave(@ModelAttribute School school, RedirectAttributes ra) {
        if (school.getId() != null) {
            // Mavjud maktabni yangilash
            schoolRepo.findById(school.getId()).ifPresent(existing -> {
                existing.setNumber(school.getNumber());
                existing.setName(school.getName());
                existing.setAddress(school.getAddress());
                existing.setPhone(school.getPhone());
                existing.setDirectorName(school.getDirectorName());
                existing.setStudentCount(school.getStudentCount());
                existing.setTeacherCount(school.getTeacherCount());
                existing.setType(school.getType());
                existing.setLatitude(school.getLatitude());
                existing.setLongitude(school.getLongitude());
                schoolRepo.save(existing);
            });
            ra.addFlashAttribute("success", "Maktab yangilandi!");
        } else {
            // Yangi maktab qo'shish
            school.setActive(true);
            schoolRepo.save(school);
            ra.addFlashAttribute("success", "Maktab saqlandi!");
        }
        return "redirect:/admin/schools";
    }

    @PostMapping("/schools/delete/{id}")
    public String schoolDelete(@PathVariable Long id, RedirectAttributes ra) {
        schoolRepo.findById(id).ifPresent(s -> { s.setActive(false); schoolRepo.save(s); });
        ra.addFlashAttribute("success", "Maktab o'chirildi");
        return "redirect:/admin/schools";
    }

    // ===== HUJJATLAR =====
    @GetMapping("/documents")
    public String documentList(Model model) {
        model.addAttribute("settings", settingsService.get());
        model.addAttribute("docs", docRepo.findByActiveTrueOrderByUploadedAtDesc());
        model.addAttribute("categories", Document.Category.values());
        return "admin/documents";
    }

    @PostMapping("/documents/upload")
    public String documentUpload(@RequestParam String title,
                                 @RequestParam(required=false) String description,
                                 @RequestParam Document.Category category,
                                 @RequestParam MultipartFile file,
                                 RedirectAttributes ra) throws IOException {
        String name = fileService.store(file);
        Document doc = Document.builder()
                .title(title).description(description).category(category)
                .fileName(file.getOriginalFilename()).filePath(name)
                .fileType(file.getContentType()).fileSize(file.getSize())
                .active(true).build();
        docRepo.save(doc);
        ra.addFlashAttribute("success", "Hujjat yuklandi!");
        return "redirect:/admin/documents";
    }

    @PostMapping("/documents/delete/{id}")
    public String documentDelete(@PathVariable Long id, RedirectAttributes ra) {
        docRepo.findById(id).ifPresent(d -> {
            fileService.delete(d.getFilePath());
            d.setActive(false);
            docRepo.save(d);
        });
        ra.addFlashAttribute("success", "Hujjat o'chirildi");
        return "redirect:/admin/documents";
    }

    // ===== MUROJAATLAR =====
    @GetMapping("/contacts")
    public String contactList(Model model) {
        model.addAttribute("settings", settingsService.get());
        model.addAttribute("contacts", contactRepo.findAllByOrderByCreatedAtDesc());
        model.addAttribute("newCount", contactRepo.countByStatus(Contact.Status.YANGI));
        return "admin/contacts";
    }

    @PostMapping("/contacts/status/{id}")
    public String contactStatus(@PathVariable Long id, @RequestParam Contact.Status status) {
        contactRepo.findById(id).ifPresent(c -> { c.setStatus(status); contactRepo.save(c); });
        return "redirect:/admin/contacts";
    }

    @PostMapping("/contacts/delete/{id}")
    public String contactDelete(@PathVariable Long id) {
        contactRepo.deleteById(id);
        return "redirect:/admin/contacts";
    }

    // ===== SOZLAMALAR =====
    @GetMapping("/settings")
    public String settings(Model model) {
        model.addAttribute("settings", settingsService.get());
        return "admin/settings";
    }

    @PostMapping("/settings/save")
    public String settingsSave(@ModelAttribute SiteSettings settings, RedirectAttributes ra) {
        settingsService.save(settings);
        ra.addFlashAttribute("success", "Sozlamalar saqlandi!");
        return "redirect:/admin/settings";
    }
}