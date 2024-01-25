package net.unir.missi.desarrollowebfullstack.bookabook.operador.service;

import net.unir.missi.desarrollowebfullstack.bookabook.operador.model.api.LoanRequest;
import net.unir.missi.desarrollowebfullstack.bookabook.operador.model.api.LoanResponse;

import java.util.Date;
import java.util.List;

public interface LoanService {
    // CRUD
    List<LoanResponse> getAllLoans(Long bookId, Long clientId, Date loanDate, Date returnDate, Date dueDate, Boolean isReturned, Integer renewalCount);
    LoanResponse createLoan(LoanRequest request);
    LoanResponse getLoanById(Long id);
    LoanResponse modifyAllLoanData(LoanRequest preData, LoanRequest loanData);
    LoanResponse modifyLoan(LoanRequest preData, LoanRequest loanData);
    LoanResponse deleteLoan(LoanRequest preData, LoanRequest loanData);

    // SPECIALIZATIONS
    List<LoanResponse> getLoansByClientId(Long clientId);
}
