package com.dev.EcommerceUserService.repository;

import com.dev.EcommerceUserService.model.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface TokenRepository extends JpaRepository<Token, UUID> {
    Optional<Token> findByTokenAndUser_Id(String token, UUID userId);

    @Query(value = CustomQueries.FIND_ACTIVE_SESSION_BY_USER_EMAIL, nativeQuery = true)
    Optional<Token> findActiveTokenByUserEmail(@Param("email") String email);
}