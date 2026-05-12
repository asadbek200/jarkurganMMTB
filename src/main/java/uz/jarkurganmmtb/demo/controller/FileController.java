package uz.jarkurganmmtb.demo.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import uz.jarkurganmmtb.demo.service.FileStorageService;


import java.io.IOException;
import java.nio.file.Path;

@RestController
@RequiredArgsConstructor
public class FileController {

    private final FileStorageService fileService;

    @GetMapping("/uploads/{fileName}")
    public ResponseEntity<Resource> download(@PathVariable String fileName) throws IOException {
        Path p = fileService.getPath(fileName);
        Resource res = new UrlResource(p.toUri());
        if (!res.exists()) return ResponseEntity.notFound().build();
        return ResponseEntity.ok()
            .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + fileName + "\"")
            .contentType(MediaType.APPLICATION_OCTET_STREAM)
            .body(res);
    }
}
