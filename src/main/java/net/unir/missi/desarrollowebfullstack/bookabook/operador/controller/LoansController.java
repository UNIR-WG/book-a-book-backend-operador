package net.unir.missi.desarrollowebfullstack.bookabook.operador.controller;

import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.unir.missi.desarrollowebfullstack.bookabook.operador.model.api.LoanRequest;
import net.unir.missi.desarrollowebfullstack.bookabook.operador.model.api.LoanResponse;
import net.unir.missi.desarrollowebfullstack.bookabook.operador.service.LoanService;

import org.apache.http.protocol.ResponseServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequiredArgsConstructor
@Slf4j
public class LoansController {
    @Autowired
    private final LoanService service;

    @GetMapping("/loans")
    public ResponseEntity<List<LoanResponse>> getAllLoans(
            @Parameter(name="bookId", example = "")
            @RequestParam(required = false) Long bookId,
            @Parameter(name="clientId", example = "")
            @RequestParam(required = false) Long clientId,
            @Parameter(name="loanDate", example = "")
            @RequestParam(required = false)Date loanDate,
            @Parameter(name="returnDate", example = "")
            @RequestParam(required = false)Date returnDate,
            @Parameter(name="dueDate", example = "")
            @RequestParam(required = false)Date dueDate,
            @Parameter(name="isReturned", example = "")
            @RequestParam(required = false)Boolean isReturned,
            @Parameter(name="renewalCount", example = "")
            @RequestParam(required = false)Integer renewalCount
            ) {

            try {
                List<LoanResponse> response = service.getAllLoans(bookId, clientId, loanDate, returnDate, dueDate, isReturned, renewalCount);
                return ResponseEntity.ok(Objects.requireNonNullElse(response, Collections.emptyList()));
            }
            catch(Exception e) {
                return ResponseEntity.internalServerError().build();
            }
    }

    @PostMapping("/loans")
    public ResponseEntity<LoanResponse> addLoan(@RequestBody LoanRequest loanRequest) {
        try {
            if(loanRequest != null) {
                LoanResponse newLoan = service.createLoan(loanRequest);
                return ResponseEntity.status(HttpStatus.CREATED).body(newLoan);
            }
            else
            {
                return ResponseEntity.badRequest().build();
            }
        } catch(Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}
