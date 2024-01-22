package net.unir.missi.desarrollowebfullstack.bookabook.operador;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class OperadorApplication {

	public static void main(String[] args) {
		SpringApplication.run(OperadorApplication.class, args);
	}

}
