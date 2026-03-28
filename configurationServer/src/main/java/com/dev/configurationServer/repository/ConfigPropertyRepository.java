package com.dev.configurationServer.repository;

import com.dev.configurationServer.model.ConfigProperty;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ConfigPropertyRepository extends JpaRepository<ConfigProperty, Long> {
    List<ConfigProperty> findByApplicationInAndProfileAndLabel(List<String> appNames, String profile, String label);
}