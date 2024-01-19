package net.unir.missi.desarrollowebfullstack.bookabook.operador.data.model.api;

public class BookRequest {

    private String isbn;
    private String name;
    private String language;
    private String description;
    private String category;
    private Long authorId;

    public BookRequest() {
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Long getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Long authorId) {
        this.authorId = authorId;
    }

    @Override
    public String toString() {
        return "BookRequest{" +
                "isbn='" + isbn + '\'' +
                ", name='" + name + '\'' +
                ", language='" + language + '\'' +
                ", description='" + description + '\'' +
                ", category='" + category + '\'' +
                ", authorId=" + authorId +
                '}';
    }
}
