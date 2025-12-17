package kr.java.restapi.model.dto;

import kr.java.restapi.model.entity.FileEntity;

import java.time.Instant;

// #(2)-3
public record FileResponse(
        Long id,
        String originalName,
        String contentType,
        Long fileSize,
        Instant createdAt
) {
    public static FileResponse from(FileEntity file) {
        return new FileResponse(
                file.getId(),
                file.getOriginalName(),
                file.getContentType(),
                file.getFileSize(),
                file.getCreatedAt()
        );
    }
}