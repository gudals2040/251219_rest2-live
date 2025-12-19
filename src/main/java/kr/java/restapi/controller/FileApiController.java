package kr.java.restapi.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.tags.Tag;
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
// #(3)-7
@RestController
@RequestMapping("/api/files")
@RequiredArgsConstructor
@Tag(name = "File", description = "파일 업로드/다운로드 API")
public class FileApiController {

    private final FileService fileService;

    // UPLOAD: POST /api/files → 201 Created
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE) // 필수적으로!
    @Operation(summary = "파일 업로드", description = "파일을 서버에 업로드합니다.")
    public ResponseEntity<FileResponse> upload(
            @Parameter(
                    description = "업로드할 파일",
//                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE)
                    content = @Content(mediaType = MediaType.MULTIPART_FORM_DATA_VALUE)
            )
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