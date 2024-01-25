package net.unir.missi.desarrollowebfullstack.bookabook.operador.config;

import net.unir.missi.desarrollowebfullstack.bookabook.operador.model.sql.Loan;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Configuration
public class LoanMerger {
    public Loan merge(Loan destiny, Loan source)
    {
        destiny.setBookId(source.getBookId());
        destiny.setClientId(source.getClientId());
        destiny.setDueDate(source.getDueDate());
        destiny.setLoanDate(source.getLoanDate());
        destiny.setIsReturned(source.getIsReturned());
        destiny.setRenewalCount(source.getRenewalCount());
        destiny.setReturnDate(source.getReturnDate());
        return destiny;
    }
}
