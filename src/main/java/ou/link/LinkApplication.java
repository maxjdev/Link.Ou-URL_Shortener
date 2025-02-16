package ou.link;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication
public class LinkApplication {
	public static void main(String[] args) {
		SpringApplication.run(LinkApplication.class, args);
	}
}
