package com.dev.EcommerceUserService.repository;

import com.dev.EcommerceUserService.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface RolesRepository extends JpaRepository<Role, UUID> {
    List<Role> findAllById_In(List<UUID> roleIds);

    Optional<Role> findByRoleName(String roleName);
}