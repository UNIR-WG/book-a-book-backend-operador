package net.unir.missi.desarrollowebfullstack.bookabook.operador.model.api;

import lombok.*;

import java.time.LocalDate;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class LoanResponse {
    private Long id;
    private Long bookId;
    private Long clientId;
    private LocalDate loanDate;
    private LocalDate returnDate;
    private LocalDate dueDate;
    private Boolean isReturned;
    private Integer renewalCount;
}
