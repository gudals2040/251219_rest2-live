package kr.java.restapi.model.dto;

import java.time.Instant;

// #(3)-1
/**
 * 에러 응답 DTO (모든 API 에러 공통)
 */
public record ErrorResponse(
        int status,
        String message,
        String path,
        Instant timestamp
) {
    public static ErrorResponse of(int status, String message, String path) {
        return new ErrorResponse(status, message, path, Instant.now());
    }
}
