package com.example.flywaydemo;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.MigrationInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.sql.DataSource;
import java.util.Objects;

@SpringBootApplication
public class FlywayApplication implements ApplicationRunner {

    private final Log log = LogFactory.getLog(getClass());

    @Value("${flyway.command}")
    private String command;

    @Autowired
    private DataSource dataSource;

    public static void main(String[] args) {
        SpringApplication.run(FlywayApplication.class, args);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        log.info("command: " + command);
        Flyway flyway = Flyway.configure().dataSource(dataSource).load();

        try {
            if (Objects.nonNull(command)) {
                if (command.equalsIgnoreCase("migrate")) {
                    flyway.migrate();
                } else if (command.equalsIgnoreCase("baseline")) {
                    flyway.baseline();
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        for (MigrationInfo info : flyway.info().all()) {
            log.info(info.getState());
        }
    }
}
