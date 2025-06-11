package com.codersstash.book_details.Service;

import com.codersstash.book_details.entity.Book;
import com.codersstash.book_details.repository.BookRepository;
import com.codersstash.book_details.service.BookService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Testcontainers
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class BookServiceTest {
    @Container
    static MySQLContainer<?> mysql=new MySQLContainer<>("mysql:8.0.41")
            .withDatabaseName("test_db")
            .withUsername("test")
            .withPassword("test");

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry){
        registry.add("spring.datasource.url",mysql::getJdbcUrl);
        registry.add("spring.datasource.username",mysql::getUsername);
        registry.add("spring.datasource.password",mysql::getPassword);
    }

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private BookService bookService;

    private Book book;

    @BeforeEach
    void setup(){
        book=new Book();
        book.setBook_name("Test Book");
        book.setBook_author("Test Author");
        book.setBook_publisher("Test Publisher");
        book.setBook_description("Test Description");
        book.setBook_price(49.99);
    }

    @Test
    @Order(1)
    void saveBook_ShouldPersistBook(){
        Book savedBook=bookService.saveBook(book);

        assertNotNull(savedBook);
        assertEquals("Test Book",savedBook.getBook_name());
    }

    @Test
    @Order(2)
    void getBookById_ShouldReturnBook(){
        Book savedBook=bookService.saveBook(book);
        Book foundBook=bookService.getBookById(savedBook.getBook_id());

        assertNotNull(foundBook);
        assertEquals(savedBook.getBook_id(),foundBook.getBook_id());
    }

    @Test
    @Order(3)
    void getAllBooks_shouldReturnBooksList(){
        bookService.saveBook(book);
        List<Book> books=bookService.getAllBooks();

        assertFalse(books.isEmpty());
        assertTrue(books.size()>0);
    }

    @Test
    @Order(4)
    void deleteBook_ShouldRemoveBook(){
        Book savedBook=bookService.saveBook(book);
        bookService.deleteBook(savedBook.getBook_id());

        assertNull(bookService.getBookById(savedBook.getBook_id()));
    }
}
