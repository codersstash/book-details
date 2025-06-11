package com.codersstash.book_details.controller;


import com.codersstash.book_details.entity.Book;
import com.codersstash.book_details.service.BookService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(BookController.class)
public class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private BookService bookService;

    private Book book;
    @BeforeEach
    void setUp(){
        book = new Book();
        book.setBook_id(1);
        book.setBook_name("Test Book");
        book.setBook_author("Test Author");
        book.setBook_publisher("Test Publisher");
        book.setBook_description("Test Description");
        book.setBook_price(28.59);
    }

    @Test
    void showAllBooks_shouldReturnIndexPage() throws Exception {
        List<Book> books= Arrays.asList(book);
        when(bookService.getAllBooks()).thenReturn(books);

        mockMvc.perform(MockMvcRequestBuilders.get("/index"))
                .andExpect(status().isOk())
                .andExpect(view().name("index"))
                .andExpect(model().attributeExists("books"));

        verify(bookService,times(1)).getAllBooks();
    }

    @Test
    void getNewBookForm_ShouldReturnAddNewBookFormPage() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/addnewbook"))
                .andExpect(status().isOk())
                .andExpect(view().name("addNewBookForm"))
                .andExpect(model().attributeExists("book"));
    }

    @Test
    void addNewBook_ShouldRedirectToIndex()throws Exception{
        when(bookService.saveBook(any(Book.class))).thenReturn(book);
        mockMvc.perform(MockMvcRequestBuilders.post("/saveBook")
                .flashAttr("book",book))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/index"));

        verify(bookService,times(1)).saveBook(any(Book.class));
    }

    @Test
    void editBook_shouldReturnUpdateForm()throws Exception{
        when(bookService.getBookById(1)).thenReturn(book);

        mockMvc.perform(MockMvcRequestBuilders.get("/editbook/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("updateBookForm"))
                .andExpect(model().attributeExists("book"));

        verify(bookService,times(1)).getBookById(1);
    }

    @Test
    void deleteBook_ShouldRedirectToIndex()throws Exception{
        doNothing().when(bookService).deleteBook(1);
        mockMvc.perform(MockMvcRequestBuilders.get("/deletebook/1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/index"));

        verify(bookService,times(1)).deleteBook(1);
    }
}
