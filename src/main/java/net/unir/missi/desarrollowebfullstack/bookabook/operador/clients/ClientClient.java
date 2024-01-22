package net.unir.missi.desarrollowebfullstack.bookabook.operador.clients;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import net.unir.missi.desarrollowebfullstack.bookabook.operador.model.api.ClientDto;
import net.unir.missi.desarrollowebfullstack.bookabook.operador.model.api.DeleteResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "buscador")
public interface ClientClient {

        @GetMapping(value = "/clients")
        ResponseEntity<List<ClientDto>> getFilterClients(
                @RequestParam(required = false) String firstName,
                @RequestParam(required = false) String lastName,
                @RequestParam(required = false) String address,
                @RequestParam(required = false) String phoneNumber,
                @RequestParam(required = false) String email);

        @GetMapping("/clients/{clientId}")
        ResponseEntity<ClientDto> getClient(@PathVariable String clientId);

        @PostMapping("/clients")
        ResponseEntity<ClientDto> addBook(@RequestBody ClientDto requestClient);

        @DeleteMapping("/clients/{clientId}")
        ResponseEntity<DeleteResponse> deleteClient(@PathVariable String clientId);

        @PutMapping("/clients/{clientId}")
        ResponseEntity<ClientDto> updateClient(@PathVariable String clientId, @RequestBody ClientDto client);

        @PatchMapping("/clients/{clientId}")
        ResponseEntity<ClientDto> updateClientAttribute(@PathVariable String clientId, @RequestBody String requestClientAttribute);
}