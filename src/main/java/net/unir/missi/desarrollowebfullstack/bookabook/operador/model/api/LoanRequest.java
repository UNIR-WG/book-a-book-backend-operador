package net.unir.missi.desarrollowebfullstack.bookabook.operador.model.api;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class LoanRequest {
    private Long bookId;
    private Long clientId;
    private Date loanDate;
    private Date returnDate;
    private Date dueDate;
    private Boolean isReturned;
    private Integer renewalCount;
}
