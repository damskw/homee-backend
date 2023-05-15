package com.codecool.homee_backend.repository;

import com.codecool.homee_backend.entity.Document;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface DocumentRepository extends JpaRepository<Document, UUID> {
    List<Document> findAllByDeviceId(UUID id);
    @Query("SELECT COUNT (d) FROM HomeeUser u JOIN u.spaces s JOIN s.devices de JOIN de.documents d WHERE u.id = :id")
    Integer countUserDocuments(UUID id);
}
