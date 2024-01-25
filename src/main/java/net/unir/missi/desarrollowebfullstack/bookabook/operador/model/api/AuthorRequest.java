package net.unir.missi.desarrollowebfullstack.bookabook.operador.model.api;

import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class AuthorRequest {
    private String firstName;
    private String lastName;
    private LocalDate birthDate;
    private String nationality;
    private String email;
    private String webSite;
    private String biography;
    private List<Long> booksWrittenId;
}