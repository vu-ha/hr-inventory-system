package vn.edu.hust.vha.hims;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing 
public class HrInventorySystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(HrInventorySystemApplication.class, args);
	}

}
