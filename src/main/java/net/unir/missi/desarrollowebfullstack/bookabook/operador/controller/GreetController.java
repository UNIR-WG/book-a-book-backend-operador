package net.unir.missi.desarrollowebfullstack.bookabook.operador.controller;

import net.unir.missi.desarrollowebfullstack.bookabook.operador.config.BuscadorClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GreetController {

    @Autowired
    @Lazy
    private BuscadorClient buscadorClient;

    @Value("${spring.application.name}")
    private String appName;

    @RequestMapping("/greeting")
    public String greeting() {
        return String.format(
                "Operador says hello to buscador, buscador replies back saying '%s'!", buscadorClient.hello());
    }
}