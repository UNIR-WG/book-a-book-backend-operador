package net.unir.missi.desarrollowebfullstack.bookabook.operador.service;

import lombok.extern.slf4j.Slf4j;
import net.unir.missi.desarrollowebfullstack.bookabook.operador.model.api.LoanRequest;
import net.unir.missi.desarrollowebfullstack.bookabook.operador.model.api.LoanResponse;
import net.unir.missi.desarrollowebfullstack.bookabook.operador.model.sql.Loan;
import net.unir.missi.desarrollowebfullstack.bookabook.operador.repository.LoanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class LoanService implements ILoanService {
    //TODO: Listado de préstamos: getAllLoans
    //TODO: Listado de préstamos por persona: getLoansByClientId
    //TODO: Detalle de préstamo: getLoanById
    //TODO: Creación de préstamo: createLoan
    //TODO: Edición de préstamo: modifyAllLoanData
    //TODO: Extensión de préstamo: modifyLoan
    //TODO: Devoluciones: modifyLoan
    //TODO: Eliminación: deleteLoan

    @Autowired
    private LoanRepository loanRepository;
    @Override
    public List<LoanResponse> getAllLoans(Long bookId, Long clientId, Date loanDate, Date returnDate, Date dueDate, Boolean isReturned, Integer renewalCount) throws RuntimeException {

        List<Loan> loanList = new ArrayList<>();
        try {
            if(bookId != null
            || clientId != null
            || loanDate != null
            || returnDate != null
            || dueDate != null
            || isReturned != null
            || renewalCount != null)
                loanList = loanRepository.search(bookId, clientId, loanDate, returnDate, dueDate, isReturned, renewalCount);
            else
                loanList = loanRepository.findAll();

            return getLoanResponses(loanList);
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
                    && request.getRenewalCount() != null

            ) {
                Loan newLoan = Loan.builder()
                        .bookId(request.getBookId())
                        .clientId(request.getClientId())
                        .loanDate(request.getLoanDate())
                        .dueDate(request.getDueDate())
                        .returnDate(request.getReturnDate())
                        .isReturned(request.getIsReturned())
                        .renewalCount(request.getRenewalCount())
                        .build();

                Loan createdLoan = loanRepository.save(newLoan);

                return LoanResponse.builder()
                        .id(createdLoan.getId())
                        .bookId(createdLoan.getBookId())
                        .clientId(createdLoan.getClientId())
                        .loanDate(createdLoan.getLoanDate())
                        .returnDate(createdLoan.getReturnDate())
                        .dueDate(createdLoan.getDueDate())
                        .isReturned(createdLoan.getIsReturned())
                        .renewalCount(createdLoan.getRenewalCount()).build();

            }

        return null;
    }

    @Override
    public LoanResponse getLoanById(Long id) throws RuntimeException {
        Loan loan = loanRepository.findById(id);
        if(loan != null) {
            return LoanResponse.builder()
                    .id(loan.getId())
                    .bookId(loan.getBookId())
                    .clientId(loan.getClientId())
                    .loanDate(loan.getLoanDate())
                    .dueDate(loan.getDueDate())
                    .returnDate(loan.getReturnDate())
                    .isReturned(loan.getIsReturned())
                    .renewalCount(loan.getRenewalCount())
                    .build();
        }
        return null;
    }

    @Override
    public List<LoanResponse> getLoansByClientId(Long clientId) throws RuntimeException {
        List<Loan> loansList = loanRepository.findByClientId(clientId);
        return getLoanResponses(loansList);
    }

    private List<LoanResponse> getLoanResponses(List<Loan> loansList) {
        List<LoanResponse> loansResponse = new ArrayList<>();

        if(loansList != null) {
            for(Loan loan : loansList) {
                LoanResponse loanResponse = LoanResponse.builder()
                        .id(loan.getId())
                        .bookId(loan.getBookId())
                        .clientId(loan.getClientId())
                        .loanDate(loan.getLoanDate())
                        .dueDate(loan.getDueDate())
                        .returnDate(loan.getReturnDate())
                        .isReturned(loan.getIsReturned())
                        .renewalCount(loan.getRenewalCount())
                        .build();

                loansResponse.add(loanResponse);
            }
        }

        return loansResponse.isEmpty() ? null : loansResponse;
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
}
