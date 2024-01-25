package net.unir.missi.desarrollowebfullstack.bookabook.operador.config;

import net.unir.missi.desarrollowebfullstack.bookabook.operador.model.api.LoanResponse;
import net.unir.missi.desarrollowebfullstack.bookabook.operador.model.sql.Loan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;

@Configuration
public class LoanToLoanResponseConverter implements Converter<Loan, LoanResponse> {
    @Override
    public LoanResponse convert(Loan source) {
        return LoanResponse.builder()
                .id(source.getId())
                .bookId(source.getBookId())
                .clientId(source.getClientId())
                .loanDate(source.getLoanDate())
                .dueDate(source.getDueDate())
                .returnDate(source.getReturnDate())
                .isReturned(source.getIsReturned())
                .renewalCount(source.getRenewalCount())
                .build();
    }
}
