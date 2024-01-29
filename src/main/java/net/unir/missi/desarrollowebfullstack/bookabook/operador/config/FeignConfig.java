package net.unir.missi.desarrollowebfullstack.bookabook.operador.config;

import feign.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignConfig {

    @Bean
    Logger.Level feingLoggerLevel(){ return Logger.Level.FULL; }
}
