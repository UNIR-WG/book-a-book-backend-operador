package net.unir.missi.desarrollowebfullstack.bookabook.operador.controller;

import net.unir.missi.desarrollowebfullstack.bookabook.operador.clients.AuthorClient;
import net.unir.missi.desarrollowebfullstack.bookabook.operador.model.api.AuthorRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;


@RestController
@Profile("dev")
public class AuthorForwarderController {

    @Lazy
    @Autowired
    private AuthorClient authorClient;


    @GetMapping("/authors")
    public ResponseEntity<List<AuthorRequest>> getAuthors(String firstName, String lastName, LocalDate birthDate, String nationality, String email, String webSite, String biography, Long bookId) {
        return authorClient.getAuthors(firstName, lastName, birthDate, nationality, email, webSite, biography, bookId);
    }

    @GetMapping("/authors/{idAuthor}")
    public ResponseEntity<AuthorRequest> getAuthorById(String idAuthor) {
        return authorClient.getAuthorById(idAuthor);
    }

    @PostMapping("/authors")
    public ResponseEntity<AuthorRequest> addAuthor(AuthorRequest authorRequested) {
        return authorClient.addAuthor(authorRequested);
    }

    @PutMapping("/authors/{idAuthor}")
    public ResponseEntity<AuthorRequest> modifyAllAuthorData(String idAuthor, AuthorRequest authorData) {
        return authorClient.modifyAllAuthorData(idAuthor, authorData);
    }

    @PatchMapping("/authors/{idAuthor}")
    public ResponseEntity<AuthorRequest> modifyAuthorData(String idAuthor, String authorData) {
        return authorClient.modifyAuthorData(idAuthor, authorData);
    }

    @DeleteMapping("/authors/{idAuthor}")
    public ResponseEntity<AuthorRequest> deleteAuthor(String idAuthor) {
        return authorClient.deleteAuthor(idAuthor);
    }
}
