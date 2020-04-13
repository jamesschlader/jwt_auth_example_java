package com.schlader.james.jwtauthexample.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Data
@Component
@ConfigurationProperties(prefix = "com.schlader.james")
public class ApplicationProperties {
    private String corsOrigin;
    private List<String> usersList;
    private Map<String, String> userPasswords;
    private List<String> rolesList;
    private List<String> privilegesList;
    private Map<String, List> userRoles;
    private Map<String, List> rolePrivileges;
}
