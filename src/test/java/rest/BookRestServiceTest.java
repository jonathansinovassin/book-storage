package rest;

import org.codehaus.jackson.map.ObjectMapper;
import org.jboss.resteasy.core.Dispatcher;
import org.jboss.resteasy.mock.MockDispatcherFactory;
import org.jboss.resteasy.mock.MockHttpRequest;
import org.jboss.resteasy.mock.MockHttpResponse;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import service.BookCriteria;
import service.BookService;
import service.dto.BookDTO;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.MediaType;
import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

public class BookRestServiceTest {

    private ObjectMapper mapper = new ObjectMapper();
    private Dispatcher dispatcher;
    @Mock
    private BookService bookService;

    @Before
    public void setUp() {
        dispatcher = MockDispatcherFactory.createDispatcher();


        MockitoAnnotations.initMocks(this);

    }

    @Test
    public void testGet() throws Exception {

        BookRestService bookRestService = new BookRestService();
        bookRestService.setBookService(bookService);
        when(bookService.get(anyLong())).thenReturn(getMockBookDTO());
        dispatcher.getRegistry().addSingletonResource(bookRestService);

        MockHttpRequest request = MockHttpRequest.get("/book/1");
        MockHttpResponse response = new MockHttpResponse();

        dispatcher.invoke(request, response);

        Assert.assertEquals(HttpServletResponse.SC_OK, response.getStatus());
        Assert.assertTrue("Does not contains the value", response.getContentAsString().contains("The great author"));
    }

    @Test
    public void testSave() throws Exception {

        BookRestService bookRestService = new BookRestService();
        bookRestService.setBookService(bookService);
        when(bookService.save(any(BookDTO.class))).thenReturn(1L);
        dispatcher.getRegistry().addSingletonResource(bookRestService);
        MockHttpRequest request = MockHttpRequest.post("/book");
        request.contentType(MediaType.APPLICATION_JSON);

        String jsonStr = mapper.writeValueAsString(getMockBookDTO());

        request.content(jsonStr.getBytes());

        MockHttpResponse response = new MockHttpResponse();

        dispatcher.invoke(request, response);

        Assert.assertEquals(HttpServletResponse.SC_CREATED, response.getStatus());
        Assert.assertTrue("Does not contains the value", response.getContentAsString().contains("1"));
    }

    @Test
    public void testList() throws Exception {

        BookRestService bookRestService = new BookRestService();
        bookRestService.setBookService(bookService);
        when(bookService.list(any(BookCriteria.class))).thenReturn(Collections.singletonList(getMockBookDTO()));
        dispatcher.getRegistry().addSingletonResource(bookRestService);
        MockHttpRequest request = MockHttpRequest.post("/book/list");
        request.contentType(MediaType.APPLICATION_JSON);

        BookCriteria bookCriteria = new BookCriteria();
        bookCriteria.setTitle("Time");
        String jsonStr = mapper.writeValueAsString(bookCriteria);
        request.content(jsonStr.getBytes());

        MockHttpResponse response = new MockHttpResponse();

        dispatcher.invoke(request, response);

        Assert.assertEquals(HttpServletResponse.SC_OK, response.getStatus());
        Assert.assertTrue("Does not contains the value", response.getContentAsString().contains("The great author"));
    }

    @Test
    public void testUpdate() throws Exception {

        BookRestService bookRestService = new BookRestService();
        bookRestService.setBookService(bookService);
        when(bookService.update(any(BookDTO.class))).thenReturn(getMockBookDTO());
        dispatcher.getRegistry().addSingletonResource(bookRestService);
        MockHttpRequest request = MockHttpRequest.put("/book");
        request.contentType(MediaType.APPLICATION_JSON);

        String jsonStr = mapper.writeValueAsString(getMockBookDTO());

        request.content(jsonStr.getBytes());

        MockHttpResponse response = new MockHttpResponse();

        dispatcher.invoke(request, response);

        Assert.assertEquals(HttpServletResponse.SC_OK, response.getStatus());
        Assert.assertTrue("Does not contains the value", response.getContentAsString().contains("The great author"));
    }

    @Test
    public void testDelete() throws Exception {

        BookRestService bookRestService = new BookRestService();
        bookRestService.setBookService(bookService);
        doNothing().when(bookService).delete(anyLong());

        dispatcher.getRegistry().addSingletonResource(bookRestService);

        MockHttpRequest request = MockHttpRequest.delete("/book/1");
        MockHttpResponse response = new MockHttpResponse();

        dispatcher.invoke(request, response);

        Assert.assertEquals(HttpServletResponse.SC_OK, response.getStatus());
        Assert.assertTrue("Does not contains the value", response.getContentAsString().contains("Book deleted successfully"));
    }


    private BookDTO getMockBookDTO() {
        BookDTO mockBook = new BookDTO();
        mockBook.setId(1L);
        mockBook.setTitle("Time");
        mockBook.setSummary("Book about time");
        mockBook.setAuthor("The great author");
        mockBook.setPrice(12D);
        return mockBook;
    }
}
