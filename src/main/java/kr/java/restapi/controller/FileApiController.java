package kr.java.restapi.controller;

import kr.java.restapi.model.dto.FileResponse;
import kr.java.restapi.model.entity.FileEntity;
import kr.java.restapi.service.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

// #(2)-5
/**
 * 파일 REST API
 * - POST /api/files              : 업로드
 * - GET  /api/files              : 목록
 * - GET  /api/files/{id}/download : 다운로드
 */
@RestController
@RequestMapping("/api/files")
@RequiredArgsConstructor
public class FileApiController {

    private final FileService fileService;

    // UPLOAD: POST /api/files → 201 Created
    @PostMapping
    public ResponseEntity<FileResponse> upload(
            @RequestParam("file") MultipartFile file) {

        FileResponse response = fileService.upload(file);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // LIST: GET /api/files → 200 OK
    @GetMapping
    public ResponseEntity<List<FileResponse>> findAll() {
        return ResponseEntity.ok(fileService.findAll());
    }

    // DOWNLOAD: GET /api/files/{id}/download
    @GetMapping("/{id}/download")
    public ResponseEntity<Resource> download(@PathVariable Long id) {
        FileEntity fileEntity = fileService.findById(id);
        Resource resource = fileService.loadAsResource(id);

        // 한글 파일명 인코딩
        String encodedFilename = URLEncoder.encode(
                        fileEntity.getOriginalName(), StandardCharsets.UTF_8)
                .replace("+", "%20");

        return ResponseEntity.ok()
                // import org.springframework.http.MediaType;
                .contentType(MediaType.parseMediaType(fileEntity.getContentType()))
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename*=UTF-8''" + encodedFilename)
                .body(resource);
    }
}