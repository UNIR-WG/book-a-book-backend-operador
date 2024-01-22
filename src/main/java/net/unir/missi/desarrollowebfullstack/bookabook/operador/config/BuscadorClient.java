package net.unir.missi.desarrollowebfullstack.bookabook.operador.config;

import net.unir.missi.desarrollowebfullstack.bookabook.operador.data.model.api.AuthorRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(name = "buscador")
public interface BuscadorClient {
    @GetMapping(value = "/hello")
    String hello();

    @GetMapping("/authors")
    ResponseEntity<List<AuthorRequest>> getAuthors();
}
