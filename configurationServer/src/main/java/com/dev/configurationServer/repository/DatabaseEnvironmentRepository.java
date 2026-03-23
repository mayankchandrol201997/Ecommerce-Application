package com.dev.configurationServer.repository;

import com.dev.configurationServer.model.ConfigProperty;
import org.springframework.cloud.config.environment.Environment;
import org.springframework.cloud.config.environment.PropertySource;
import org.springframework.cloud.config.server.environment.EnvironmentRepository;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class DatabaseEnvironmentRepository implements EnvironmentRepository {

    private final ConfigPropertyRepository repository;

    public DatabaseEnvironmentRepository(ConfigPropertyRepository repository) {
        this.repository = repository;
    }

    @Override
    public Environment findOne(String application, String profile, String label) {

        List<ConfigProperty> props =
                repository.findByApplicationAndProfileAndLabel(application, profile, label);

        Map<String, Object> map = new HashMap<>();

        for (ConfigProperty prop : props) {
            map.put(prop.getPropKey(), prop.getPropValue());
        }

        PropertySource propertySource = new PropertySource("mysql", map);

        Environment environment = new Environment(application, profile);
        environment.add(propertySource);

        return environment;
    }
}