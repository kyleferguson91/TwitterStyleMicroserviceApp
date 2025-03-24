package org.ac.cst8277.ferguson.kyle.usermanagementservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan("org.ac.cst8277.ferguson.kyle.usermanagementservice.model")
public class UsermanagementserviceApplication {
    public static void main(String[] args) {
        SpringApplication.run(UsermanagementserviceApplication.class, args);
    }
}
