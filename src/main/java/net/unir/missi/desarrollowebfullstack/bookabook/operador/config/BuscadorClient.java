package net.unir.missi.desarrollowebfullstack.bookabook.operador.config;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "buscador")
public interface BuscadorClient {
    @GetMapping(value = "/hello")
    String hello();
}
