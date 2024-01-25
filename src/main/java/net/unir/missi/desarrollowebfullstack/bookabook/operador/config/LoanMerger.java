package net.unir.missi.desarrollowebfullstack.bookabook.operador.config;

import net.unir.missi.desarrollowebfullstack.bookabook.operador.model.sql.Loan;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Configuration
public class LoanMerger {
    public Loan merge(Loan destiny, Loan source)
    {
        source.setBookId(destiny.getBookId());
        source.setClientId(destiny.getClientId());
        source.setDueDate(destiny.getDueDate());
        source.setLoanDate(destiny.getLoanDate());
        source.setIsReturned(destiny.getIsReturned());
        source.setRenewalCount(destiny.getRenewalCount());
        source.setReturnDate(destiny.getReturnDate());
        return source;
    }
}
