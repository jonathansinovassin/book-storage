package service;

import com.google.common.base.Preconditions;
import data.repository.BookRepository;
import data.repository.Util.HibernateUtil;
import data.repository.impl.BookRepositoryImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import service.dto.BookDTO;
import service.exception.BookException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class BookService {

    private BookRepository bookRepository;

    private Logger LOGGER = LoggerFactory.getLogger(BookService.class);

    public BookService() {
        bookRepository = new BookRepositoryImpl(HibernateUtil.getSessionFactory().openSession());
    }

    public List<BookDTO> list(BookCriteria bookCriteria) {
        if (bookCriteria == null) {
            LOGGER.info("Get all books");
            return bookRepository
                    .list()
                    .stream()
                    .map(BookDTO::fromModelToDTO)
                    .collect(Collectors.toList());
        } else {
            LOGGER.info("Get books with the following criteria : {}", bookCriteria.toString());
            return bookRepository
                    .list(bookCriteria)
                    .stream()
                    .map(BookDTO::fromModelToDTO)
                    .collect(Collectors.toList());
        }
    }

    public BookDTO get(Long id) {
        Preconditions.checkNotNull(id, "Id must not be null");
        LOGGER.info("Search book with the id {}", id);
        return Optional.ofNullable(bookRepository.get(id))
                .map(BookDTO::fromModelToDTO)
                .orElse(null);
    }

    public Long save(BookDTO book) {
        Preconditions.checkNotNull(book, "Book must not be null");
        LOGGER.info("Save a new book : {}", book.toString());
        return bookRepository.save(book.toModel());
    }

    public BookDTO update(BookDTO book) {
        Preconditions.checkNotNull(book, "Book must not be null");
        Preconditions.checkNotNull(book.getId(), "Book id must not be null");
        LOGGER.info("Update the book with the id {} with the data : {}", book.getId(), book.toString());
        return Optional.ofNullable(bookRepository.update(book.toModel()))
                .map(BookDTO::fromModelToDTO)
                .orElseThrow(() -> new BookException("Failed to update the book"));
    }

    public void delete(Long id) {
        Preconditions.checkNotNull(id);
        bookRepository.delete(id);
    }
}
