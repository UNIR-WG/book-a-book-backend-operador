package net.unir.missi.desarrollowebfullstack.bookabook.operador.service;

import lombok.extern.slf4j.Slf4j;
import net.unir.missi.desarrollowebfullstack.bookabook.operador.clients.BuscadorClient;
import net.unir.missi.desarrollowebfullstack.bookabook.operador.config.LoanRequestToLoanConverter;
import net.unir.missi.desarrollowebfullstack.bookabook.operador.config.LoanToLoanResponseConverter;
import net.unir.missi.desarrollowebfullstack.bookabook.operador.model.api.BookResponse;
import net.unir.missi.desarrollowebfullstack.bookabook.operador.model.api.ClientResponse;
import net.unir.missi.desarrollowebfullstack.bookabook.operador.model.api.LoanRequest;
import net.unir.missi.desarrollowebfullstack.bookabook.operador.model.api.LoanResponse;
import net.unir.missi.desarrollowebfullstack.bookabook.operador.model.sql.Loan;
import net.unir.missi.desarrollowebfullstack.bookabook.operador.repository.LoanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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
    public List<LoanResponse> getAllLoans(Long bookId, Long clientId, Date loanDate, Date returnDate, Date dueDate, Boolean isReturned, Integer renewalCount) throws RuntimeException {

        List<Loan> loanList;
        try {
            if (bookId != null
            || clientId != null
            || loanDate != null
            || returnDate != null
            || dueDate != null
            || isReturned != null
            || renewalCount != null)
                loanList = loanRepository.search(bookId, clientId, loanDate, returnDate, dueDate, isReturned, renewalCount);
            else
                loanList = loanRepository.findAll();

            return loanList.stream().map((Loan l) -> this.loanToLoanResponseConverter.convert(l)).collect(Collectors.toList());
        }
        catch(Exception e) {
            throw new RuntimeException("Database failed: " + e.getMessage());
        }
    }

    @Override
    public LoanResponse createLoan(LoanRequest request) throws RuntimeException {
            if(request != null
                    && request.getBookId() != 0
                    && request.getClientId() != 0
                    && request.getLoanDate() != null
                    && request.getDueDate() != null
                    && request.getReturnDate() != null
                    && request.getIsReturned() != null
                    && request.getRenewalCount() != null) {

                    boolean bookExists = isBookValid(request.getBookId().toString());

                    if(!bookExists) {
                        return null;
                    }
                    Loan newLoan = this.loanRequestToLoanConverter.convert(request);
                    Loan createdLoan = loanRepository.save(newLoan);
                    return this.loanToLoanResponseConverter.convert(createdLoan);
                }
                return null;
    }

    @Override
    public LoanResponse getLoanById(Long id) throws RuntimeException {
        Loan loan = loanRepository.findById(id);
        if(loan != null) {
            return this.loanToLoanResponseConverter.convert(loan);
        }
        return null;
    }

    @Override
    public List<LoanResponse> getLoansByClientId(Long clientId) throws RuntimeException {
        List<Loan> loansList = loanRepository.findByClientId(clientId);
        return loansList.stream().map((Loan l) -> this.loanToLoanResponseConverter.convert(l)).collect(Collectors.toList());
    }

    @Override
    public LoanResponse modifyAllLoanData(LoanRequest preData, LoanRequest loanData) throws RuntimeException {
        return null;
    }

    @Override
    public LoanResponse modifyLoan(LoanRequest preData, LoanRequest loanData) throws RuntimeException {
        return null;
    }

    @Override
    public LoanResponse deleteLoan(LoanRequest preData, LoanRequest loanData) throws RuntimeException {
        return null;
    }

    // SPECIALIZATION

    private boolean isBookValid(String id) {
        try {
            ResponseEntity<BookResponse> book = buscadorClient.getBook(id);
            return true;
        }
        catch(Exception e) {
            Logger.getGlobal().warning(e.getStackTrace().toString());
            return false;
        }
    }

    private boolean isClientValid(String id) {
        try {
            ResponseEntity<ClientResponse> client = buscadorClient.getClient(id);
            return true;
        }
        catch(Exception e) {
            Logger.getGlobal().warning(e.getStackTrace().toString());
            return false;
        }
    }
}
