package uz.jarkurganmmtb.demo.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.*;
import java.util.UUID;

@Service
public class FileStorageService {

    @Value("${app.upload.dir}")
    private String uploadDir;

    public String store(MultipartFile file) throws IOException {
        Path dir = Paths.get(uploadDir);
        Files.createDirectories(dir);
        String uniqueName = UUID.randomUUID() + "_" + file.getOriginalFilename();
        Path dest = dir.resolve(uniqueName);
        Files.copy(file.getInputStream(), dest, StandardCopyOption.REPLACE_EXISTING);
        return uniqueName;
    }

    public void delete(String fileName) {
        try {
            Path p = Paths.get(uploadDir).resolve(fileName);
            Files.deleteIfExists(p);
        } catch (IOException ignored) {}
    }

    public Path getPath(String fileName) {
        return Paths.get(uploadDir).resolve(fileName);
    }
}
