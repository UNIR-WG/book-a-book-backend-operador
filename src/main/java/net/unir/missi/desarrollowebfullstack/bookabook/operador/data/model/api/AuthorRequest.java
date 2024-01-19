package net.unir.missi.desarrollowebfullstack.bookabook.operador.data.model.api;

import java.util.Date;
import java.util.List;

public class AuthorRequest {
    private Long id;
    private String firstName;
    private String lastName;
    private Date birthDate;
    private String nationality;
    private String email;
    private String webSite;
    private String biography;
    private List<Long> booksWrittenId;



    public AuthorRequest() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getWebSite() {
        return webSite;
    }

    public void setWebSite(String webSite) {
        this.webSite = webSite;
    }

    public String getBiography() {
        return biography;
    }

    public void setBiography(String biography) {
        this.biography = biography;
    }

    public List<Long> getBooksWrittenId() {
        return booksWrittenId;
    }

    public void setBooksWrittenId(List<Long> booksWrittenId) {
        this.booksWrittenId = booksWrittenId;
    }
}