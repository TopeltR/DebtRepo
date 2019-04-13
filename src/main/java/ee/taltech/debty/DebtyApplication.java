package ee.taltech.debty;


import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories
@SpringBootApplication
@RequiredArgsConstructor
public class DebtyApplication {

    public static void main(String[] args) {
        SpringApplication.run(DebtyApplication.class, args);
    }

}
