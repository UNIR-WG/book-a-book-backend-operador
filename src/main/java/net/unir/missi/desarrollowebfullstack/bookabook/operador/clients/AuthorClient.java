package net.unir.missi.desarrollowebfullstack.bookabook.operador.clients;

import io.swagger.v3.oas.annotations.Parameter;
import net.unir.missi.desarrollowebfullstack.bookabook.operador.model.api.AuthorRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@FeignClient(name = "buscador")
public interface AuthorClient {

    @GetMapping("/authors")
    ResponseEntity<List<AuthorRequest>> getAuthors(
            @RequestParam(required = false) String firstName,
            @RequestParam(required = false) String lastName,
            @RequestParam(required = false) LocalDate birthDate,
            @RequestParam(required = false) String nationality,
            @RequestParam(required = false) String email,
            @RequestParam(required = false) String webSite,
            @RequestParam(required = false) String biography,
            @RequestParam(required = false) Long bookId);

    @GetMapping("/authors/{idAuthor}")
    ResponseEntity<AuthorRequest> getAuthorById(@PathVariable String idAuthor);

    @PostMapping("/authors")
    ResponseEntity<AuthorRequest> addAuthor(@RequestBody AuthorRequest authorRequested);

    @PutMapping("/authors/{idAuthor}")
    ResponseEntity<AuthorRequest> modifyAllAuthorData(@PathVariable String idAuthor, @RequestBody AuthorRequest authorData);

    @PatchMapping("/authors/{idAuthor}")
    ResponseEntity<AuthorRequest> modifyAuthorData(@PathVariable String idAuthor, @RequestBody String authorData);

    @DeleteMapping("/authors/{idAuthor}")
    ResponseEntity<AuthorRequest> deleteAuthor(@PathVariable String idAuthor);
}
