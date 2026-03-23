package com.dev.configurationServer.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "configuration_properties")
@Getter
@Setter
public class ConfigProperty {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String application;
    private String profile;
    private String label;
    private String propKey;
    private String propValue;
}