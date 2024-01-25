package net.unir.missi.desarrollowebfullstack.bookabook.operador.service;

import lombok.extern.slf4j.Slf4j;
import net.unir.missi.desarrollowebfullstack.bookabook.operador.clients.BuscadorClient;
import net.unir.missi.desarrollowebfullstack.bookabook.operador.config.LoanRequestToLoanConverter;
import net.unir.missi.desarrollowebfullstack.bookabook.operador.config.LoanToLoanResponseConverter;
import net.unir.missi.desarrollowebfullstack.bookabook.operador.exceptions.BadParametersException;
import net.unir.missi.desarrollowebfullstack.bookabook.operador.exceptions.EntityNotFoundException;
import net.unir.missi.desarrollowebfullstack.bookabook.operador.model.api.BookResponse;
import net.unir.missi.desarrollowebfullstack.bookabook.operador.model.api.ClientResponse;
import net.unir.missi.desarrollowebfullstack.bookabook.operador.model.api.LoanRequest;
import net.unir.missi.desarrollowebfullstack.bookabook.operador.model.api.LoanResponse;
import net.unir.missi.desarrollowebfullstack.bookabook.operador.model.sql.Loan;
import net.unir.missi.desarrollowebfullstack.bookabook.operador.repository.LoanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 *     //TODO: Listado de préstamos: getAllLoans
 *     //TODO: Listado de préstamos por persona: getLoansByClientId
 *     //TODO: Detalle de préstamo: getLoanById
 *     //TODO: Creación de préstamo: createLoan
 *     //TODO: Edición de préstamo: modifyAllLoanData
 *     //TODO: Extensión de préstamo: modifyLoan
 *     //TODO: Devoluciones: modifyLoan
 *     //TODO: Eliminación: deleteLoan
 */
@Service
@Slf4j
public class LoanServiceImpl implements LoanService {

    @Autowired
    private LoanRepository loanRepository;

    @Autowired
    private BuscadorClient buscadorClient;

    @Autowired
    private LoanRequestToLoanConverter loanRequestToLoanConverter;

    @Autowired
    private LoanToLoanResponseConverter loanToLoanResponseConverter;

    // CRUD

    @Override
    public List<LoanResponse> getAllLoans(Long bookId, Long clientId, Date loanDate, Date returnDate, Date dueDate, Boolean isReturned, Integer renewalCount) {
        Loan loan = new Loan(null, bookId, clientId, loanDate, returnDate, dueDate, isReturned, renewalCount);
        List<Loan> loanList;
        try {
            if (this.isValidSyntaxLoanForNulls(loan)) {
                loanList = loanRepository.search(bookId, clientId, loanDate, returnDate, dueDate, isReturned, renewalCount);
            }
            else {
                loanList = loanRepository.findAll();
            }
            return loanList.stream().map((Loan l) -> this.loanToLoanResponseConverter.convert(l)).collect(Collectors.toList());
        }
        catch(Exception e) {
            throw new RuntimeException("Database failed: " + e.getMessage());
        }
    }

    @Override
    public LoanResponse createLoan(LoanRequest request) {
        Loan loan = this.loanRequestToLoanConverter.convert(request);
        if (
                ! this.isValidSyntaxLoanForNulls(Objects.requireNonNull(loan))
                || ! this.isValidSyntaxLoanForZeroes(loan))
        {
            throw new BadParametersException("One or more parameters of the request are wrong", null);
        }

        if (! isExistingBook(loan.getBookId().toString()))
        {
            throw new EntityNotFoundException("Book id " + loan.getBookId() + " does not exist.", null);
        }

        if(! isExistingClient(loan.getClientId().toString()))
        {
            throw new EntityNotFoundException("Client id " + loan.getClientId() + " does not exist.", null);
        }
        Loan createdLoan = loanRepository.save(loan);
        return this.loanToLoanResponseConverter.convert(createdLoan);
    }

    @Override
    public LoanResponse getLoanById(Long id) {
        Loan loan = loanRepository.findById(id);
        if (loan == null)
        {
            throw new EntityNotFoundException("Loan with id " + id.toString() + " does not exist", null);
        }

        return this.loanToLoanResponseConverter.convert(loan);
    }

    @Override
    public LoanResponse modifyAllLoanData(LoanRequest preData, LoanRequest loanData) {
        return null;
    }

    @Override
    public LoanResponse modifyLoan(LoanRequest preData, LoanRequest loanData) {
        return null;
    }

    @Override
    public LoanResponse deleteLoan(LoanRequest preData, LoanRequest loanData) {
        return null;
    }

    // SPECIALIZATION

    @Override
    public List<LoanResponse> getLoansByClientId(Long clientId) {
        List<Loan> loansList = loanRepository.findByClientId(clientId);
        return loansList.stream().map((Loan l) -> this.loanToLoanResponseConverter.convert(l)).collect(Collectors.toList());
    }

    // PRIVATE METHODS

    private boolean isExistingBook(String id) {
        try {
            ResponseEntity<BookResponse> book = buscadorClient.getBook(id);
            return book != null;
        }
        catch(Exception e) {
            return false;
        }
    }

    private boolean isExistingClient(String id) {
        try {
            ResponseEntity<ClientResponse> client = buscadorClient.getClient(id);
            return client != null;
        }
        catch(Exception e) {
            return false;
        }
    }

    private boolean isValidSyntaxLoanForNulls(Loan loan)
    {
        return loan.getBookId() != null
                && loan.getClientId() != null
                && loan.getLoanDate() != null
                && loan.getReturnDate() != null
                && loan.getDueDate() != null
                && loan.getIsReturned() != null
                && loan.getRenewalCount() != null;
    }

    private boolean isValidSyntaxLoanForZeroes(Loan loan)
    {
        return loan.getBookId() != 0
                && loan.getClientId() != 0;
    }
}
