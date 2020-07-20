package by.ngrudnitsky;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@SpringBootApplication
public class SpringForumProjectApplication {
    public static void main(String[] args) {
        SpringApplication.run(SpringForumProjectApplication.class, args);
    }
}
