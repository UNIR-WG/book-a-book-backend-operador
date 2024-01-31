package net.unir.missi.desarrollowebfullstack.bookabook.operador.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.unir.missi.desarrollowebfullstack.bookabook.operador.exceptions.BadParametersException;
import net.unir.missi.desarrollowebfullstack.bookabook.operador.exceptions.EntityInvalidOperationException;
import net.unir.missi.desarrollowebfullstack.bookabook.operador.exceptions.EntityNotFoundException;
import net.unir.missi.desarrollowebfullstack.bookabook.operador.model.api.LoanRequest;
import net.unir.missi.desarrollowebfullstack.bookabook.operador.model.api.LoanResponse;
import net.unir.missi.desarrollowebfullstack.bookabook.operador.model.sql.Loan;
import net.unir.missi.desarrollowebfullstack.bookabook.operador.service.LoanService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.*;
import java.util.logging.Logger;

@RestController
@RequiredArgsConstructor
@Slf4j
public class LoansController {
    @Autowired
    private final LoanService service;

    @GetMapping("/loans")
    @Operation(
            operationId = "Obtener préstamos",
            description = "Operacion de lectura",
            summary = "Se devuelve una lista de todos los préstamos almacenados en la base de datos.")
    @ApiResponse(
            responseCode = "200",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Loan.class)))
    public ResponseEntity<List<LoanResponse>> getLoans(
            @Parameter(name="bookId", description = "Id del Libro préstado")
            @RequestParam(required = false) Long bookId,
            @Parameter(name="clientId", description = "Id del Cliente al que se le realizó el préstamo")
            @RequestParam(required = false) Long clientId,
            @Parameter(name="loanDate", description = "Fecha de registro del préstamo")
            @RequestParam(required = false) LocalDate loanDate,
            @Parameter(name="returnDate", description = "Fecha en que el libro fue devuelto")
            @RequestParam(required = false)LocalDate returnDate,
            @Parameter(name="dueDate", description = "Fecha de vencimiento del préstamo")
            @RequestParam(required = false)LocalDate dueDate,
            @Parameter(name="isReturned", description = "Indica si el libro ha sido devuelto")
            @RequestParam(required = false)Boolean isReturned,
            @Parameter(name="renewalCount", description = "Cantidad de renovaciones del préstamo")
            @RequestParam(required = false)Integer renewalCount
            ) {

        try
        {
            List<LoanResponse> response = this.service.getAllLoans(bookId, clientId, loanDate, returnDate, dueDate, isReturned, renewalCount);
            return ResponseEntity.ok(Objects.requireNonNullElse(response, Collections.emptyList()));
        }
        catch (EntityNotFoundException e)
        {
            return ResponseEntity.notFound().build();
        }
        catch (BadParametersException e)
        {
            return ResponseEntity.badRequest().build();
        }
        catch (Exception e)
        {
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/loans/{id}")
    @Operation(
            operationId = "Obtener un préstamo",
            description = "Operacion de lectura",
            summary = "Se devuelve un préstamo a partir de su identificador.")
    @ApiResponse(
            responseCode = "200",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Loan.class)))
    @ApiResponse(
            responseCode = "404",
            content = @Content(mediaType = "application/json", schema = @Schema()),
            description = "No se ha encontrado el préstamo con el identificador indicado.")
    public ResponseEntity<LoanResponse> getLoan(@PathVariable String id) {
        try
        {
            LoanResponse response = this.service.getLoanById(Long.valueOf(id));
            return ResponseEntity.ok(response);
        }
        catch (EntityNotFoundException e)
        {
            return ResponseEntity.notFound().build();
        }
        catch (BadParametersException e)
        {
            return ResponseEntity.badRequest().build();
        }
        catch (Exception e)
        {
            return ResponseEntity.internalServerError().build();
        }
    }

    @PostMapping("/loans")
    @Operation(
            operationId = "Crear un préstamo",
            description = "Operacion de escritura",
            summary = "Se crea un préstamo a partir de sus datos.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Datos del préstamo a crear.",
                    required = true,
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = LoanRequest.class))))
    @ApiResponse(
            responseCode = "201",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = LoanResponse.class)))
    @ApiResponse(
            responseCode = "400",
            content = @Content(mediaType = "application/json", schema = @Schema()),
            description = "Datos incorrectos introducidos.")
    @ApiResponse(
            responseCode = "404",
            content = @Content(mediaType = "application/json", schema = @Schema()),
            description = "Id del Libro O Id del Cliente no existen.")
    public ResponseEntity<LoanResponse> addLoan(@RequestBody LoanRequest loanRequest) {
        try
        {
            if (loanRequest == null)
            {
                return ResponseEntity.badRequest().build();
            }
            LoanResponse newLoan = this.service.createLoan(loanRequest);
            return ResponseEntity.status(HttpStatus.CREATED).body(newLoan);
        }
        catch (EntityNotFoundException e)
        {
            return ResponseEntity.notFound().build();
        }
        catch (BadParametersException e)
        {
            return ResponseEntity.badRequest().build();
        }
        catch(EntityInvalidOperationException e) {
            return ResponseEntity.badRequest().build();
        }
        catch (Exception e)
        {
            Logger.getGlobal().warning("Error: " +  e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }

    @DeleteMapping("/loans/{id}")
    @Operation(
            operationId = "Eliminar un préstamo",
            description = "Operacion de escritura",
            summary = "Se elimina un préstamo a partir de su identificador.")
    @ApiResponse(
            responseCode = "200",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = LoanResponse.class)))
    @ApiResponse(
            responseCode = "404",
            content = @Content(mediaType = "application/json", schema = @Schema()),
            description = "No se ha encontrado el préstamo con el identificador indicado.")
    public ResponseEntity<LoanResponse> deleteLoan(@PathVariable String id) {
        try
        {
            return ResponseEntity.ok(this.service.deleteLoan(id));
        }
        catch (EntityNotFoundException e)
        {
            return ResponseEntity.notFound().build();
        }
        catch (Exception e)
        {
            return ResponseEntity.internalServerError().build();
        }
    }

    @PatchMapping("/loans/{id}")
    @Operation(
            operationId = "Modificar parcialmente un préstamo",
            description = "RFC 7386. Operacion de escritura",
            summary = "RFC 7386. Se modifica parcialmente un préstamo.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Datos del préstamo a modificar.",
                    required = true,
                    content = @Content(mediaType = "application/merge-patch+json", schema = @Schema(implementation = LoanRequest.class))))
    @ApiResponse(
            responseCode = "200",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = LoanResponse.class)))
    @ApiResponse(
            responseCode = "400",
            content = @Content(mediaType = "application/json", schema = @Schema()),
            description = "Datos incorrectos introducidos.")
    public ResponseEntity<LoanResponse> patchLoan(@RequestBody LoanRequest loanRequest, @PathVariable String id) {
        try
        {
            Logger.getGlobal().warning("Entra al controller" + loanRequest);
            if (loanRequest == null)
            {
                return ResponseEntity.badRequest().build();
            }
            LoanResponse newLoan = this.service.modifyLoan(loanRequest.getIsReturned(), Long.valueOf(id));
            return ResponseEntity.status(HttpStatus.CREATED).body(newLoan);
        }
        catch (EntityNotFoundException e)
        {
            return ResponseEntity.notFound().build();
        }
        catch (BadParametersException e)
        {
            return ResponseEntity.badRequest().build();
        }
        catch (Exception e)
        {
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/clients/{clientId}/loans")
    @Operation(
            operationId = "Obtener los préstamos de un cliente.",
            description = "Operacion de lectura",
            summary = "Se devuelve una lista de préstamos relacionados a un cliente.")
    @ApiResponse(
            responseCode = "200",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = LoanResponse.class)))
    @ApiResponse(
            responseCode = "404",
            content = @Content(mediaType = "application/json", schema = @Schema()),
            description = "No se han encontrado préstamos con el identificador de clienteindicado.")
    public ResponseEntity<List<LoanResponse>> getLoanByClientId(@PathVariable String clientId) {
        try
        {
            List<LoanResponse> response = service.getLoansByClientId(Long.valueOf(clientId));
            return ResponseEntity.ok(Objects.requireNonNullElse(response, Collections.emptyList()));
        }
        catch (EntityNotFoundException e)
        {
            return ResponseEntity.notFound().build();
        }
        catch (BadParametersException e)
        {
            return ResponseEntity.badRequest().build();
        }
        catch(Exception e)
        {
            return ResponseEntity.internalServerError().build();
        }
    }
}
