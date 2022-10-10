package my.app.bookmyvenue;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
// import org.springframework.boot.autoconfigure.domain.EntityScan;
// import org.springframework.context.annotation.ComponentScan;
// import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


// @EntityScan("my.app.bookmyvenue.Model")
// @EnableJpaRepositories("my.app.bookmyvenue.Repository")
// @ComponentScan("my.app.bookmyvenue")
@SpringBootApplication
public class StudentServiceApplication{

	public static void main(String[] args) {
		SpringApplication.run(StudentServiceApplication.class, args);
	}

}
