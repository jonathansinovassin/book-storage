package data.repository;

import data.model.Book;
import service.BookCriteria;

import java.util.List;

public interface BookRepository {

    List<Book> list(BookCriteria bookCriteria);

    List<Book> list();

    Book get(Long id);

    Long save(Book book);

    Book update(Book book);

    void delete(Long id);
}