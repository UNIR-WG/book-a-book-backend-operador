package net.unir.missi.desarrollowebfullstack.bookabook.operador.config;

import net.unir.missi.desarrollowebfullstack.bookabook.operador.model.sql.Loan;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LoanMergerNonEmpty {
    public Loan merge(Loan destiny, Loan source)
    {
        if (source.getBookId() != null && source.getBookId() != 0)
        {
            destiny.setBookId(source.getBookId());
        }

        if (source.getClientId() != null && source.getClientId() != 0)
        {
            destiny.setClientId(source.getClientId());
        }

        if (source.getDueDate() != null)
        {
            destiny.setDueDate(source.getDueDate());
        }

        if (source.getLoanDate() != null)
        {
            destiny.setLoanDate(source.getLoanDate());
        }

        if (source.getIsReturned() != null)
        {
            destiny.setIsReturned(source.getIsReturned());
        }

        if (source.getRenewalCount() != null)
        {
            destiny.setRenewalCount(source.getRenewalCount());
        }

        if (source.getReturnDate() != null)
        {
            destiny.setReturnDate(source.getReturnDate());
        }
        return destiny;
    }
}