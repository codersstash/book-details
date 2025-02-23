package com.codersstash.book_details.controller;

import com.codersstash.book_details.entity.Book;
import com.codersstash.book_details.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class BookController {

    private final BookService bookService;
    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }
    @GetMapping("/index")
    public String showAllBooks(Model model){
        model.addAttribute("books", bookService.getAllBooks());
        return "index";
    }
    @GetMapping("/addnewbook")
    public String getNewBookForm(Model model){
        Book book = new Book();
        model.addAttribute("book", book);
        return "addNewBookForm";
    }
    @PostMapping("/saveBook")
    public String addNewBookForm(@ModelAttribute("book") Book book){
        bookService.saveBook(book);
        return "redirect:/index";
    }
    @GetMapping("/editbook/{book_id}")
    public String updateBook(@PathVariable int book_id, Model model){
        model.addAttribute("book", bookService.getBookById(book_id));
        return "updateBookForm";
    }
}
