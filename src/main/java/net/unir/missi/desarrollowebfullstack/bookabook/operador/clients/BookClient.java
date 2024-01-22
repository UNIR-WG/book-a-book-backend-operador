package net.unir.missi.desarrollowebfullstack.bookabook.operador.clients;

import io.swagger.v3.oas.annotations.Parameter;
import net.unir.missi.desarrollowebfullstack.bookabook.operador.model.api.BookRequest;
import net.unir.missi.desarrollowebfullstack.bookabook.operador.model.api.BookResponse;
import net.unir.missi.desarrollowebfullstack.bookabook.operador.model.api.DeleteResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@FeignClient(name = "buscador")
public interface BookClient {

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
}
