package com.everything.bigapp.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.secretsmanager.SecretsManagerClient;
import software.amazon.awssdk.services.secretsmanager.model.GetSecretValueRequest;
import software.amazon.awssdk.services.secretsmanager.model.GetSecretValueResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Bean;
import javax.sql.DataSource;
import com.zaxxer.hikari.HikariDataSource;
import java.util.Map;

@Configuration
public class AwsSecretsManagerConfig {

    @Value("${aws.secrets.name}")
    private String secretName;

    @Value("${aws.region:us-east-2}")
    private String region;

    @Bean
    public DataSource dataSource() throws Exception {
        SecretsManagerClient client = SecretsManagerClient.builder()
                .region(Region.of(region))
                .build();

        GetSecretValueRequest req = GetSecretValueRequest.builder()
                .secretId(secretName)
                .build();

        GetSecretValueResponse resp = client.getSecretValue(req);
        String secretString = resp.secretString();

        Map<String, String> creds = new ObjectMapper().readValue(secretString, Map.class);
        System.out.println("Kedar: " + creds);
        HikariDataSource ds = new HikariDataSource();
        ds.setJdbcUrl(String.format("jdbc:mysql://%s:%s/%s", creds.get("host"), creds.get("port"), "big-app"));
        ds.setUsername(creds.get("username"));
        ds.setPassword(creds.get("password"));
        ds.setDriverClassName("com.mysql.cj.jdbc.Driver");
        ds.setMaximumPoolSize(10);
        return ds;
    }
}
