package org.tyj;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class PressInstrumentApplication {
    private static ConfigurableApplicationContext context;

    public static void main(String[] args) {
        context = SpringApplication.run(PressInstrumentApplication.class, args);
    }

    public static ConfigurableApplicationContext getContext() {
        return context;
    }
}
