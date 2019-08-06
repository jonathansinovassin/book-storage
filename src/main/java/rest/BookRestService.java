package rest;

import service.BookCriteria;
import service.BookService;
import service.dto.BookDTO;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/book")
public class BookRestService {

    private BookService bookService;

    public BookRestService() {
        bookService = new BookService(true);
    }

    public BookRestService(Boolean initSession) {
        bookService = new BookService(false);
    }

    public void setBookService(BookService bookService) {
        this.bookService = bookService;
    }

    @POST
    @Path("/list")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public List<BookDTO> list(BookCriteria bookCriteria) {
        return bookService.list(bookCriteria);
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public BookDTO get(@PathParam("id") Long id) {
        bookService.get(id);
        return bookService.get(id);
    }

    @POST
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response save(BookDTO bookDTO) {
        return Response.status(201).entity(bookService.save(bookDTO)).build();
    }

    @PUT
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response update(BookDTO bookDTO) {
        return Response.status(200).entity(bookService.update(bookDTO)).build();
    }

    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response delete(@PathParam("id") Long id) {
        bookService.delete(id);
        return Response.ok().entity("Book deleted successfully").build();
    }

}
