package kr.java.restapi.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

// #(2)-1
/**
 * 파일 메타데이터 엔티티
 */
@Entity
@Table(name = "files")
@Getter
@NoArgsConstructor
public class FileEntity extends BaseEntity {

    @Column(name = "original_name", nullable = false)
    private String originalName;    // 원본 파일명

    @Column(name = "stored_name", nullable = false, unique = true)
    private String storedName;      // 저장 파일명 (UUID)

    @Column(name = "content_type")
    private String contentType;     // MIME 타입

    @Column(name = "file_size")
    private Long fileSize;

    @Column(name = "file_path", nullable = false)
    private String filePath;

    @Builder
    public FileEntity(String originalName, String storedName,
                      String contentType, Long fileSize, String filePath) {
        this.originalName = originalName;
        this.storedName = storedName;
        this.contentType = contentType;
        this.fileSize = fileSize;
        this.filePath = filePath;
    }
}