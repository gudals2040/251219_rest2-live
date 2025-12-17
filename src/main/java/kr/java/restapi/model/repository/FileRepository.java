package kr.java.restapi.model.repository;

import kr.java.restapi.model.entity.FileEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

// #(2)-2
public interface FileRepository extends JpaRepository<FileEntity, Long> {
    Optional<FileEntity> findByStoredName(String storedName);
}