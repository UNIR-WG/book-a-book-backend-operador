package net.unir.missi.desarrollowebfullstack.bookabook.operador.config;

import net.unir.missi.desarrollowebfullstack.bookabook.operador.model.api.LoanRequest;
import net.unir.missi.desarrollowebfullstack.bookabook.operador.model.sql.Loan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;

@Configuration
public class LoanRequestToLoanConverter implements Converter<LoanRequest, Loan> {
    @Override
    public Loan convert(LoanRequest source) {
        return Loan.builder()
                .bookId(source.getBookId())
                .clientId(source.getClientId())
                .returnDate(source.getReturnDate())
                .isReturned(source.getIsReturned())
                .renewalCount(source.getRenewalCount())
                .build();
    }
}