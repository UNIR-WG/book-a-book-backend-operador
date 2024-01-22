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
    public ResponseEntity<List<AuthorRequest>> getAuthors(String firstName, String lastName, LocalDate birthDate, String nationality, String email, String webSite, String biography, Long bookId) {
        return buscadorClient.getAuthors(firstName, lastName, birthDate, nationality, email, webSite, biography, bookId);
    }

    @GetMapping("/authors/{idAuthor}")
    public ResponseEntity<AuthorRequest> getAuthorById(String idAuthor) {
        return buscadorClient.getAuthorById(idAuthor);
    }

    @PostMapping("/authors")
    public ResponseEntity<AuthorRequest> addAuthor(AuthorRequest authorRequested) {
        return buscadorClient.addAuthor(authorRequested);
    }

    @PutMapping("/authors/{idAuthor}")
    public ResponseEntity<AuthorRequest> modifyAllAuthorData(String idAuthor, AuthorRequest authorData) {
        return buscadorClient.modifyAllAuthorData(idAuthor, authorData);
    }

    @PatchMapping("/authors/{idAuthor}")
    public ResponseEntity<AuthorRequest> modifyAuthorData(String idAuthor, String authorData) {
        return buscadorClient.modifyAuthorData(idAuthor, authorData);
    }

    @DeleteMapping("/authors/{idAuthor}")
    public ResponseEntity<AuthorRequest> deleteAuthor(String idAuthor) {
        return buscadorClient.deleteAuthor(idAuthor);
    }

    @GetMapping("/books")
    public ResponseEntity<List<BookResponse>> getBooks(Map<String, String> headers, String isbn, String name, String language, String description, String category, Long authorId) {
        return buscadorClient.getBooks(headers, isbn, name, language, description, category, authorId);
    }

    @GetMapping("/books/{bookId}")
    public ResponseEntity<BookResponse> getBook(String bookId) {
        return buscadorClient.getBook(bookId);
    }

    @DeleteMapping("/books/{bookId}")
    public ResponseEntity<DeleteResponse> deleteBook(String bookId) {
        return buscadorClient.deleteBook(bookId);
    }

    @PostMapping("/books")
    public ResponseEntity<BookResponse> addBook(BookRequest request) {
        return buscadorClient.addBook(request);
    }

    @PatchMapping("/books/{bookId}")
    public ResponseEntity<BookResponse> patchBook(String bookId, String patchBody) {
        return buscadorClient.patchBook(bookId, patchBody);
    }

    @PutMapping("/books/{bookId}")
    public ResponseEntity<BookResponse> updateBook(String bookId, BookRequest body) {
        return buscadorClient.updateBook(bookId, body);
    }

    @GetMapping("/clients")
    public ResponseEntity<List<ClientDto>> getFilterClients(String firstName, String lastName, String address, String phoneNumber, String email) {
        return buscadorClient.getFilterClients(firstName, lastName, address, phoneNumber, email);
    }

    @GetMapping("/clients/{clientId}")
    public ResponseEntity<ClientDto> getClient(String clientId) {
        return buscadorClient.getClient(clientId);
    }

    @PostMapping("/clients")
    public ResponseEntity<ClientDto> addBook(ClientDto requestClient) {
        return buscadorClient.addBook(requestClient);
    }

    @DeleteMapping("/clients/{clientId}")
    public ResponseEntity<DeleteResponse> deleteClient(String clientId) {
        return buscadorClient.deleteClient(clientId);
    }

    @PutMapping("/clients/{clientId}")
    public ResponseEntity<ClientDto> updateClient(String clientId, ClientDto client) {
        return buscadorClient.updateClient(clientId, client);
    }

    @PatchMapping("/clients/{clientId}")
    public ResponseEntity<ClientDto> updateClientAttribute(String clientId, String requestClientAttribute) {
        return buscadorClient.updateClientAttribute(clientId, requestClientAttribute);
    }

    @GetMapping("/greet")
    public String hello() {
        return String.format(
                "Operador says hello to buscador, buscador replies back saying '%s'!", buscadorClient.hello());
    }
}
