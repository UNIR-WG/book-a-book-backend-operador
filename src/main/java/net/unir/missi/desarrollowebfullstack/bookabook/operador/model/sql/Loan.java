package net.unir.missi.desarrollowebfullstack.bookabook.operador.model.sql;

import java.util.Date;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@Entity
@Table(name = "loans")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class Loan {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "bookId")
    private Long bookId;
    @Column(name = "clientId")
    private Long clientId;
    @Column(name = "loanDate")
    private Date loanDate;
    @Column(name = "returnDate")
    private Date returnDate;
    @Column(name = "dueDate")
    private Date dueDate;
    @Column(name = "isReturned")
    private Boolean isReturned;
    @Column(name = "renewalCount")
    private Integer renewalCount;
}
