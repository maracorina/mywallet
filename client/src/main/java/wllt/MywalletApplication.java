package wllt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class MywalletApplication {

    public static void main(String[] args) {
        SpringApplication.run(MywalletApplication.class, args);
    }

}
