package org.crealytics;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * Bootstrap is a Main class to start this application.
 */
@SpringBootApplication
@ComponentScan("org.crealytics")
public class BootStrap {
    public static void main(String[] args) throws Exception {
        SpringApplication.run(BootStrap.class, args);
    }
}
