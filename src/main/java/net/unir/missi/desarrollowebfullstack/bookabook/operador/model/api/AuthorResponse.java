package net.unir.missi.desarrollowebfullstack.bookabook.operador.model.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class AuthorResponse {
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;
    private String firstName;
    private String lastName;
    private LocalDate birthDate;
    private String nationality;
    private String email;
    private String webSite;
    private String biography;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private List<Long> booksWrittedId;

}