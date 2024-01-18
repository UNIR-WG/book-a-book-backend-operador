package net.unir.missi.desarrollowebfullstack.bookabook.operador.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.logging.Logger;

@RestController
public class HelloWorldController {

    @GetMapping("/hello")
    public ResponseEntity<String> helloWorld()
    {
        try {
            return ResponseEntity.ok("Hello World from operador!");
        } catch (Exception e) {
            Logger.getGlobal().warning("Error saying hello from operador" + e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }
}
