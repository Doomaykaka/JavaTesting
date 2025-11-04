package taco.cloud.support;

import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppCommandLineAndApplicationRunner {
    @Bean
    public CommandLineRunner commandLineRunnerTest() {
        return args -> {
            System.out.println("Command line runner works!");
        };
    }

    @Bean
    public ApplicationRunner applicationRunnerTest() {
        return args -> {
            System.out.println("Application runner works!");
        };
    }
}
