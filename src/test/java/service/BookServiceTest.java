package service;

import data.model.Book;
import data.repository.BookRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import service.dto.BookDTO;
import service.exception.BookException;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class BookServiceTest {

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookService bookService = new BookService(false);

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGet() {
        Book book = new Book();
        book.setId(1L);
        book.setAuthor("The author");
        when(bookRepository.get(1L)).thenReturn(book);

        BookDTO bookFromDatabase = bookService.get(1L);

        assertEquals(Long.valueOf(1), bookFromDatabase.getId());
        assertEquals("The author", bookFromDatabase.getAuthor());
    }

    @Test(expected = NullPointerException.class)
    public void testGetNullId() {
        Book book = new Book();
        book.setId(1L);
        book.setAuthor("The author");
        when(bookRepository.get(1L)).thenReturn(book);

        BookDTO bookFromDatabase = bookService.get(null);
    }


    @Test
    public void testListWithoutCriteria() {
        Book book1 = new Book();
        book1.setId(1L);
        book1.setAuthor("The author");

        Book book2 = new Book();
        book2.setId(2L);
        book2.setAuthor("The other author");

        Book book3 = new Book();
        book3.setId(3L);
        book3.setAuthor("The third author");

        when(bookRepository.list()).thenReturn(Arrays.asList(book1, book2, book3));

        List<BookDTO> books = bookService.list(null);

        assertEquals(3, books.size());
    }

    @Test
    public void testListWithCriteria() {


        Book book1 = new Book();
        book1.setId(1L);
        book1.setAuthor("The author");

        Book book2 = new Book();
        book2.setId(2L);
        book2.setAuthor("The other author");

        when(bookRepository.list(any(BookCriteria.class))).thenReturn(Arrays.asList(book1, book2));

        BookCriteria criteria = new BookCriteria();
        criteria.setTitle("A book");
        criteria.setSortBy("title");
        List<BookDTO> books = bookService.list(criteria);

        assertEquals(2, books.size());
    }

    @Test
    public void testSave() {

        BookDTO book = new BookDTO();
        book.setAuthor("The author");

        when(bookRepository.save(any(Book.class))).thenReturn(1L);

        Long idBooksSaved = bookService.save(book);

        assertEquals(Long.valueOf(1), idBooksSaved);
    }

    @Test
    public void testUpdate() {

        BookDTO book = new BookDTO();
        book.setId(1L);
        book.setAuthor("The author");
        book.setId(1L);
        book.setTitle("The title");
        book.setAuthor("The author");
        book.setSummary("The summary");
        book.setPrice(12D);
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, 2018);
        calendar.set(Calendar.MONTH, 1);
        calendar.set(Calendar.DAY_OF_MONTH, 20);
        book.setDateOfPublication(calendar.getTime());

        Book bookSaved = new Book();
        bookSaved.setId(1L);
        bookSaved.setTitle("The title");
        bookSaved.setAuthor("The author");
        bookSaved.setSummary("The summary");
        bookSaved.setPrice(12D);
        Calendar calendarSaved = Calendar.getInstance();
        calendarSaved.set(Calendar.YEAR, 2018);
        calendarSaved.set(Calendar.MONTH, 1);
        calendarSaved.set(Calendar.DAY_OF_MONTH, 20);
        bookSaved.setDateOfPublication(calendarSaved.getTime());

        when(bookRepository.update(any(Book.class))).thenReturn(bookSaved);

        Long idBooksSaved = bookService.save(book);

        assertEquals(Long.valueOf(1), bookSaved.getId());
        assertEquals("The title", bookSaved.getTitle());
        assertEquals("The author", bookSaved.getAuthor());
        assertEquals("The summary", book.getSummary());
        assertEquals(Double.valueOf(12), book.getPrice());
        assertEquals(calendarSaved.getTime(), bookSaved.getDateOfPublication());
    }
}
