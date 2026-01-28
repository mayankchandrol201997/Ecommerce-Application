package com.dev.EcommerceUserService.repository;

import com.dev.EcommerceUserService.model.Session;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface SessionRepository extends JpaRepository<Session, UUID> {
    Optional<Session> findByTokenAndUser_Id(String token, UUID userId);

    @Query(value = CustomQueries.FIND_ACTIVE_SESSION_BY_USER_EMAIL, nativeQuery = true)
    Optional<Session> findActiveSessionByUserEmail(@Param("email") String email);
}