package com.codecool.homee_backend.repository;

import com.codecool.homee_backend.entity.HomeeUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface HomeeUserRepository extends JpaRepository<HomeeUser, UUID> {
    @Query("SELECT DISTINCT h FROM HomeeUser h LEFT JOIN FETCH h.spaces")
    List<HomeeUser> findAllBy();

    @Query("SELECT h FROM HomeeUser h LEFT JOIN FETCH h.spaces WHERE h.id=:id")
    Optional<HomeeUser> findByUserId(UUID id);

    Optional<HomeeUser> findByEmail(String email);

    Optional<HomeeUser> findByEmailOrUsername(String username, String email);

    @Query("SELECT DISTINCT u FROM HomeeUser u LEFT JOIN FETCH u.spaces s WHERE s.id = :spaceId")
    List<HomeeUser> getUsersForSpace(UUID spaceId);
}
