package taco.cloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import taco.cloud.support.DataConfig;

@SpringBootApplication()
public class App {
    public static synchronized void main(String[] args) {
        DataConfig.startCassandraThread();
        DataConfig.printCassandraInfo();
        System.out.println(DataConfig.cassandraIsStarted());
        SpringApplication.run(App.class, args);
    }
}
