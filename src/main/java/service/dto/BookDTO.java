package service.dto;

import data.model.Book;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;
import java.util.Date;

public class BookDTO implements Serializable {

    private static final long serialVersionUID = 3186984330429175736L;

    private Long id;

    private String title;

    private String author;

    private Double price;

    private String summary;

    private Date dateOfPublication;

    public BookDTO() {
    }

    public static BookDTO fromModelToDTO(Book book) {
        if (book != null) {
            BookDTO bookDTO = new BookDTO();
            bookDTO.setTitle(book.getTitle());
            bookDTO.setAuthor(book.getAuthor());
            bookDTO.setId(book.getId());
            bookDTO.setDateOfPublication(book.getDateOfPublication());
            bookDTO.setPrice(book.getPrice());
            bookDTO.setSummary(book.getSummary());
            return bookDTO;
        }
        return null;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public Date getDateOfPublication() {
        return dateOfPublication;
    }

    public void setDateOfPublication(Date dateOfPublication) {
        this.dateOfPublication = dateOfPublication;
    }

    public Book toModel() {
        Book book = new Book();
        book.setTitle(title);
        book.setAuthor(author);
        book.setId(id);
        book.setDateOfPublication(dateOfPublication);
        book.setPrice(price);
        book.setSummary(summary);
        return book;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
