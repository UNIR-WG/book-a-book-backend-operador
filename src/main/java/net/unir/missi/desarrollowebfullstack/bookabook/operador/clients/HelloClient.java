package net.unir.missi.desarrollowebfullstack.bookabook.operador.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "buscador")
public interface HelloClient {

    @GetMapping(value = "/hello")
    String hello();

}
