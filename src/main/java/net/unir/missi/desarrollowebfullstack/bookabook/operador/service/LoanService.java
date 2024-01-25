package net.unir.missi.desarrollowebfullstack.bookabook.operador.service;

import net.unir.missi.desarrollowebfullstack.bookabook.operador.model.api.LoanRequest;
import net.unir.missi.desarrollowebfullstack.bookabook.operador.model.api.LoanResponse;
import net.unir.missi.desarrollowebfullstack.bookabook.operador.model.sql.Loan;

import java.util.Date;
import java.util.List;

public interface LoanService {
    // CRUD
    List<LoanResponse> getAllLoans(Long bookId, Long clientId, Date loanDate, Date returnDate, Date dueDate, Boolean isReturned, Integer renewalCount);
    LoanResponse createLoan(LoanRequest request);
    LoanResponse getLoanById(Long id);
    LoanResponse modifyAllLoanData(LoanRequest loan, Long id);
    LoanResponse modifyLoan(LoanRequest loan, Long id);
    LoanResponse deleteLoan(String id);

    // SPECIALIZATIONS
    List<LoanResponse> getLoansByClientId(Long clientId);
}
