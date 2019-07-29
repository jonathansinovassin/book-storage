package data;

import data.model.Book;
import data.repository.BookRepository;
import data.repository.impl.BookRepositoryImpl;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.junit.After;
import org.junit.Test;
import service.BookCriteria;

import java.io.FileInputStream;
import java.util.Calendar;
import java.util.List;


public class BookRepositoryTest extends HibernateDbUnitTestCase {

    protected IDataSet getDataSet() throws Exception {
        return new FlatXmlDataSetBuilder().build(new FileInputStream("src/test/resources/dataset/book.xml"));
    }

    @Test
    public void testList() {
        BookRepository bookRepository = new BookRepositoryImpl(session);
        List<Book> books = bookRepository.list();
        assertNotNull(books);
        assertEquals(3, books.size());
    }

    @Test
    public void testListWithTitleCriteria() {
        BookRepository bookRepository = new BookRepositoryImpl(session);

        BookCriteria bookCriteria = new BookCriteria();
        bookCriteria.setTitle("Premier livre");

        List<Book> books = bookRepository.list(bookCriteria);

        assertNotNull(books);
        assertEquals(1, books.size());
    }

    @Test
    public void testListWithNotExistingTitleCriteria() {
        BookRepository bookRepository = new BookRepositoryImpl(session);

        BookCriteria bookCriteria = new BookCriteria();
        bookCriteria.setTitle("Not existing");

        List<Book> books = bookRepository.list(bookCriteria);

        assertEquals(0, books.size());
    }

    @Test
    public void testListWithDateCriteria() {
        BookRepository bookRepository = new BookRepositoryImpl(session);

        BookCriteria bookCriteria = new BookCriteria();

        Calendar startCalendar = Calendar.getInstance();
        startCalendar.set(Calendar.YEAR, 2015);
        startCalendar.set(Calendar.MONTH, 11);
        startCalendar.set(Calendar.DAY_OF_MONTH, 1);

        Calendar endCalendar = Calendar.getInstance();
        endCalendar.set(Calendar.YEAR, 2017);
        endCalendar.set(Calendar.MONTH, 11);
        endCalendar.set(Calendar.DAY_OF_MONTH, 1);

        bookCriteria.setStartDatePublication(startCalendar.getTime());
        bookCriteria.setEndDatePublication(endCalendar.getTime());

        List<Book> books = bookRepository.list(bookCriteria);

        assertEquals(2, books.size());
    }

    @Test
    public void testListWithEndDateCriteria() {
        BookRepository bookRepository = new BookRepositoryImpl(session);

        BookCriteria bookCriteria = new BookCriteria();

        Calendar endCalendar = Calendar.getInstance();
        endCalendar.set(Calendar.YEAR, 2015);
        endCalendar.set(Calendar.MONTH, 11);
        endCalendar.set(Calendar.DAY_OF_MONTH, 12);

        bookCriteria.setEndDatePublication(endCalendar.getTime());

        List<Book> books = bookRepository.list(bookCriteria);

        assertEquals(1, books.size());
    }

    @Test
    public void testSave() {
        BookRepository bookRepository = new BookRepositoryImpl(session);
        Book book = new Book();
        book.setAuthor("Un auteur");
        book.setPrice(10.2);
        book.setTitle("La suite");
        book.setSummary("Un nouveau livre");
        Long newId = bookRepository.save(book);
        Book savedBook = bookRepository.get(newId);
        assertEquals("La suite", savedBook.getTitle());
    }

    @Test
    public void testUpdate() {
        BookRepository bookRepository = new BookRepositoryImpl(session);
        Book book = bookRepository.get(1L);
        book.setPrice(15D);
        Book updatedBook = bookRepository.update(book);
        assertEquals(Double.valueOf(15), updatedBook.getPrice());
    }

    @Test
    public void testDelete() {
        BookRepository bookRepository = new BookRepositoryImpl(session);
        bookRepository.delete(1L);

        List<Book> books = bookRepository.list();
        assertEquals(2, books.size());
    }

    @After
    public void after() {
        session.close();
    }
}