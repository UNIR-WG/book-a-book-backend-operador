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
public class LoanRequest {
    private Long bookId;
    private Long clientId;
    private LocalDate returnDate = null;
    private Boolean isReturned;
    private Integer renewalCount;
}
