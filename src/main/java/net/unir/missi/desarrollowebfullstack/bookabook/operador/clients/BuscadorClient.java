package net.unir.missi.desarrollowebfullstack.bookabook.operador.clients;

import net.unir.missi.desarrollowebfullstack.bookabook.operador.model.api.*;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@FeignClient(name = "buscador")
public interface BuscadorClient {

    // Authors

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

    // Books

    @GetMapping("/books")
    ResponseEntity<List<BookResponse>> getBooks(
            @RequestHeader Map<String, String> headers,
            @RequestParam(required = false) String isbn,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String language,
            @RequestParam(required = false) String description,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) Long authorId);

    @GetMapping("/books/{bookId}")
    ResponseEntity<BookResponse> getBook(@PathVariable String bookId);

    @DeleteMapping("/books/{bookId}")
    ResponseEntity<DeleteResponse> deleteBook(@PathVariable String bookId);

    @PostMapping("/books")
    ResponseEntity<BookResponse> addBook(@RequestBody BookRequest request);

    @PatchMapping("/books/{bookId}")
    ResponseEntity<BookResponse> patchBook(@PathVariable String bookId, @RequestBody String patchBody);

    @PutMapping("/books/{bookId}")
    ResponseEntity<BookResponse> updateBook(@PathVariable String bookId, @RequestBody BookRequest body);

    // Clients

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

    // Hello

    @GetMapping(value = "/hello")
    String hello();
}
