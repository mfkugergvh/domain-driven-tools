package io.ddd.sample.aak.applicaiton;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class AutoAccountKeeperServer {

    public static void main(String[] args) {
        new SpringApplicationBuilder(AutoAccountKeeperServer.class).web(true).run(args);
    }


}
