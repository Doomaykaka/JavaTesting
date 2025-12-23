package taco.cloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication()
public class App {
    public static synchronized void main(String[] args) {
        //For correct startup in Eclipse use setting Window-Preferencese-Java-Compiler-Store information about method parameters
        //Mongo in user home folder .embedmongo
        SpringApplication.run(App.class, args);
    }
}
