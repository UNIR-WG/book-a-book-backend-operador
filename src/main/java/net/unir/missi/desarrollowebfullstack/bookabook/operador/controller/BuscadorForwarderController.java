package net.unir.missi.desarrollowebfullstack.bookabook.operador.controller;

import net.unir.missi.desarrollowebfullstack.bookabook.operador.clients.BuscadorClient;
import net.unir.missi.desarrollowebfullstack.bookabook.operador.model.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

/**
 * Delega todas las llamadas del buscador hacia el buscador. Se usa solo en el modo de desarrollador
 * todo añadir loans
 */
@RestController
@Profile("dev")
public class BuscadorForwarderController {

    private final BuscadorClient buscadorClient;

    public BuscadorForwarderController(BuscadorClient buscadorClient) {
        this.buscadorClient = buscadorClient;
    }

    // DELEGATES

    @GetMapping("/authors")
    public ResponseEntity<List<AuthorRequest>> getAuthors(
            @RequestParam(required = false) String firstName,
            @RequestParam(required = false) String lastName,
            @RequestParam(required = false) LocalDate birthDate,
            @RequestParam(required = false) String nationality,
            @RequestParam(required = false) String email,
            @RequestParam(required = false) String webSite,
            @RequestParam(required = false) String biography,
            @RequestParam(required = false) Long bookId) {
        return buscadorClient.getAuthors(firstName, lastName, birthDate, nationality, email, webSite, biography, bookId);
    }

    @GetMapping("/authors/{idAuthor}")
    public ResponseEntity<AuthorRequest> getAuthorById(@PathVariable String idAuthor) {
        return buscadorClient.getAuthorById(idAuthor);
    }

    @PostMapping("/authors")
    public ResponseEntity<AuthorRequest> addAuthor(@RequestBody AuthorRequest authorRequested) {
        return buscadorClient.addAuthor(authorRequested);
    }

    @PutMapping("/authors/{idAuthor}")
    public ResponseEntity<AuthorRequest> modifyAllAuthorData(@PathVariable String idAuthor, @RequestBody AuthorRequest authorData) {
        return buscadorClient.modifyAllAuthorData(idAuthor, authorData);
    }

    @PatchMapping("/authors/{idAuthor}")
    public ResponseEntity<AuthorRequest> modifyAuthorData(@PathVariable String idAuthor, @RequestBody String authorData) {
        return buscadorClient.modifyAuthorData(idAuthor, authorData);
    }

    @DeleteMapping("/authors/{idAuthor}")
    public ResponseEntity<AuthorRequest> deleteAuthor(@PathVariable String idAuthor) {
        return buscadorClient.deleteAuthor(idAuthor);
    }

    @GetMapping("/books")
    public ResponseEntity<List<BookResponse>> getBooks(
            @RequestHeader Map<String, String> headers,
            @RequestParam(required = false) String isbn,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String language,
            @RequestParam(required = false) String description,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) Long authorId) {
        return buscadorClient.getBooks(headers, isbn, name, language, description, category, authorId);
    }

    @GetMapping("/books/{bookId}")
    public ResponseEntity<BookResponse> getBook(@PathVariable String bookId) {
        return buscadorClient.getBook(bookId);
    }

    @DeleteMapping("/books/{bookId}")
    public ResponseEntity<DeleteResponse> deleteBook(@PathVariable String bookId) {
        return buscadorClient.deleteBook(bookId);
    }

    @PostMapping("/books")
    public ResponseEntity<BookResponse> addBook(@RequestBody BookRequest request) {
        return buscadorClient.addBook(request);
    }

    @PatchMapping("/books/{bookId}")
    public ResponseEntity<BookResponse> patchBook(@PathVariable String bookId, @RequestBody String patchBody) {
        return buscadorClient.patchBook(bookId, patchBody);
    }

    @PutMapping("/books/{bookId}")
    public ResponseEntity<BookResponse> updateBook(@PathVariable String bookId, @RequestBody BookRequest body) {
        return buscadorClient.updateBook(bookId, body);
    }

    @GetMapping("/clients")
    public ResponseEntity<List<ClientDto>> getFilterClients(
            @RequestParam(required = false) String firstName,
            @RequestParam(required = false) String lastName,
            @RequestParam(required = false) String address,
            @RequestParam(required = false) String phoneNumber,
            @RequestParam(required = false) String email) {
        return buscadorClient.getFilterClients(firstName, lastName, address, phoneNumber, email);
    }

    @GetMapping("/clients/{clientId}")
    public ResponseEntity<ClientDto> getClient(@PathVariable String clientId) {
        return buscadorClient.getClient(clientId);
    }

    @PostMapping("/clients")
    public ResponseEntity<ClientDto> addBook(@RequestBody ClientDto requestClient) {
        return buscadorClient.addBook(requestClient);
    }

    @DeleteMapping("/clients/{clientId}")
    public ResponseEntity<DeleteResponse> deleteClient(@PathVariable String clientId) {
        return buscadorClient.deleteClient(clientId);
    }

    @PutMapping("/clients/{clientId}")
    public ResponseEntity<ClientDto> updateClient(@PathVariable String clientId, @RequestBody ClientDto client) {
        return buscadorClient.updateClient(clientId, client);
    }

    @PatchMapping("/clients/{clientId}")
    public ResponseEntity<ClientDto> updateClientAttribute(@PathVariable String clientId, @RequestBody String requestClientAttribute) {
        return buscadorClient.updateClientAttribute(clientId, requestClientAttribute);
    }

    @GetMapping("/greet")
    public String hello() {
        return String.format(
                "Operador says hello to buscador, buscador replies back saying '%s'!", buscadorClient.hello());
    }
}
