package data.repository.impl;

import data.criteriabuilder.BookCriteriaBuilder;
import data.model.Book;
import data.repository.BookRepository;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import service.BookCriteria;

import java.util.List;

public class BookRepositoryImpl implements BookRepository {

    private Session session;

    public BookRepositoryImpl(Session session) {
        this.session = session;
    }

    public List<Book> list(BookCriteria bookCriteria) {

        Query<Book> query = session.createQuery(BookCriteriaBuilder.build(session)
                .addTitleCriteria(bookCriteria.getTitle())
                .addAuthorCriteria(bookCriteria.getAuthor())
                .addDatePublicationCriteria(bookCriteria.getStartDatePublication(),bookCriteria.getEndDatePublication())
                .addMaximumPriceCriteria(bookCriteria.getPrice())
                .addSummaryCriteria(bookCriteria.getSummary())
                .sortBy(bookCriteria.isSortByAsc(),bookCriteria.getSortBy())
                .getQuery());

        return query.getResultList();
    }

    public List<Book> list() {
        Query<Book> query = session.createQuery(BookCriteriaBuilder
                .build(session)
                .getQuery());
        return query.getResultList();
    }

    public Book get(Long id) {
        return session.find(Book.class, id);
    }

    public Long save(Book book) {
        return (Long) session.save(book);
    }

    public Book update(Book book) {
        return (Book) session.merge(book);
    }

    public void delete(Long id) {
        Transaction transaction = session.beginTransaction();
        session.delete(get(id));
        transaction.commit();
    }
}

