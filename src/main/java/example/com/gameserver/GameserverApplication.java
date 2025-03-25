package example.com.gameserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class GameserverApplication {

    public static void main(String[] args) {
        SpringApplication.run(GameserverApplication.class, args);
    }

}
