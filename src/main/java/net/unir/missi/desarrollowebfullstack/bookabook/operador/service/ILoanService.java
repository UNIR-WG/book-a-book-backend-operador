package net.unir.missi.desarrollowebfullstack.bookabook.operador.service;

import net.unir.missi.desarrollowebfullstack.bookabook.operador.model.api.LoanRequest;
import net.unir.missi.desarrollowebfullstack.bookabook.operador.model.api.LoanResponse;

import java.util.Date;
import java.util.List;

public interface ILoanService {
    List<LoanResponse> getAllLoans(Long bookId, Long clientId, Date loanDate, Date returnDate, Date dueDate, Boolean isReturned, Integer renewalCount) throws RuntimeException;
    LoanResponse createLoan(LoanRequest request) throws RuntimeException;
    LoanResponse getLoanById(Long id) throws RuntimeException;
    List<LoanResponse> getLoansByClientId(Long clientId) throws RuntimeException;
    LoanResponse modifyAllLoanData(LoanRequest preData, LoanRequest loanData) throws RuntimeException;
    LoanResponse modifyLoan(LoanRequest preData, LoanRequest loanData) throws RuntimeException;
    LoanResponse deleteLoan(LoanRequest preData, LoanRequest loanData) throws RuntimeException;
}
