package com.example.flywaydemo;

import lombok.extern.slf4j.Slf4j;
import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.MigrationInfo;
import org.flywaydb.core.api.output.BaselineResult;
import org.flywaydb.core.api.output.MigrateOutput;
import org.flywaydb.core.api.output.MigrateResult;
import org.flywaydb.core.api.output.OperationResultBase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.sql.DataSource;
import java.util.Objects;

@SpringBootApplication
@Slf4j
public class FlywayApplication implements ApplicationRunner {

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
                    MigrateResult migrateResult = flyway.migrate();
                    if(migrateResult.migrations.size() == 0 ) {
                        log.info("Result: No Migrations");
                    }

                    for (MigrateOutput mr : migrateResult.migrations) {
                        log.info("Result: " , mr.toString());
                    }

                } else if (command.equalsIgnoreCase("baseline")) {
                    log.info(flyway.baseline().toString());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}