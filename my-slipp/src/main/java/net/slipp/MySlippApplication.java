package net.slipp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing	//data의 변동 사항을 추적
public class MySlippApplication {

	public static void main(String[] args) {
		SpringApplication.run(MySlippApplication.class, args);
	}

}
