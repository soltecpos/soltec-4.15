package com.soltec.web.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;
import org.springframework.core.env.PropertySource;

import java.io.File;
import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class SoltecPropertiesEnvironmentPostProcessor implements EnvironmentPostProcessor {

    private static final String DEFAULT_FILE = "soltec.properties";
    
    @Override
    public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {
        File userHome = new File(System.getProperty("user.home"));
        File configFile = new File(userHome, DEFAULT_FILE);
        
        // Also check if they are running it directly overriding the dir
        String dirname = System.getProperty("dirname.path");
        if (dirname != null) {
            File alt = new File(new File(dirname), DEFAULT_FILE);
            if (alt.exists()) {
                configFile = alt;
            }
        }

        if (configFile.exists() && configFile.isFile()) {
            try (FileInputStream in = new FileInputStream(configFile)) {
                Properties props = new Properties();
                props.load(in);

                Map<String, Object> springProps = new HashMap<>();

                // Translate Soltec properties to Spring Boot Data Source properties
                String url = props.getProperty("db.URL");
                if (url != null && !url.isEmpty()) {
                    if(!url.contains("allowPublicKeyRetrieval=true")) {
                         if (url.contains("?")) url += "&allowPublicKeyRetrieval=true&serverTimezone=UTC";
                         else url += "?allowPublicKeyRetrieval=true&serverTimezone=UTC";
                    }
                    springProps.put("spring.datasource.url", url);
                }

                String user = props.getProperty("db.user");
                if (user != null) {
                    springProps.put("spring.datasource.username", user);
                }

                String pwd = props.getProperty("db.password");
                if (pwd != null) {
                    if (pwd.startsWith("crypt:")) {
                        pwd = decrypt(pwd.substring(6));
                    }
                    springProps.put("spring.datasource.password", pwd);
                }

                PropertySource<?> propertySource = new MapPropertySource("soltecProperties", springProps);
                environment.getPropertySources().addFirst(propertySource);

            } catch (Exception e) {
                System.err.println("Error reading soltec.properties: " + e.getMessage());
            }
        } else {
             System.out.println("No soltec.properties found in " + configFile.getAbsolutePath() + ". Using application.properties defaults.");
        }
    }
    
    private String decrypt(String input) {
        if(input == null || input.isEmpty()) return input;
        try {
           com.openbravo.pos.util.AltEncrypter enc = new com.openbravo.pos.util.AltEncrypter("cypherkey");
           return enc.decrypt(input);
        } catch(Exception e) {
            return input;
        }
    }
}
