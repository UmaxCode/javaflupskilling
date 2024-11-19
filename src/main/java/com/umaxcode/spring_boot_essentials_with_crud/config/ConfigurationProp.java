package com.umaxcode.spring_boot_essentials_with_crud.config;


import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@ConfigurationProperties(prefix = "application")
@Component
public class ConfigurationProp {

    private String greeting;

    private Object rand;

    private Security security = new Security();

    @Setter
    @Getter
    public static class Security {

        private String username;

        private String password;

        private List<String> roles;

        private Map<String, Boolean> permissions = new HashMap<>();
    }
}
