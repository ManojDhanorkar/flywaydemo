package com.example.flywaydemo;

import lombok.extern.slf4j.Slf4j;
import org.flywaydb.core.api.callback.Callback;
import org.flywaydb.core.api.callback.Context;
import org.flywaydb.core.api.callback.Event;

@Slf4j
public class ExampleFlywayCallback implements Callback {

    @Override
    public boolean supports(Event event, Context context) {
        return event == Event.AFTER_EACH_MIGRATE || event == Event.AFTER_MIGRATE || event == Event.BEFORE_EACH_MIGRATE || event == Event.BEFORE_MIGRATE;
    }

    @Override
    public boolean canHandleInTransaction(Event event, Context context) {
        return true;
    }

    @Override
    public void handle(Event event, Context context) {
        if (event == Event.AFTER_EACH_MIGRATE) {
            log.info("> afterEachMigrate");
        } else if (event == Event.AFTER_MIGRATE) {
            log.info("> afterMigrate");
        } else if (event == Event.BEFORE_EACH_MIGRATE) {
            log.info("> beforeEachMigrate");
        } else if (event == Event.BEFORE_MIGRATE) {
            log.info("> beforeMigrate");
        }
    }

    @Override
    public String getCallbackName() {
        return ExampleFlywayCallback.class.getSimpleName();
    }
}
