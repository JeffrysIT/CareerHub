import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "careerhub")
public class CareerHubApplication {
    public static void main(String[] args) {
        SpringApplication.run(CareerHubApplication.class, args);
    }
}
