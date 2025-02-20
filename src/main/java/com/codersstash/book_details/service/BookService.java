package com.codersstash.book_details.service;

import com.codersstash.book_details.entity.Book;
import com.codersstash.book_details.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {
    @Autowired
    private BookRepository bookRepository;
    public Book saveBook(Book book){
        return bookRepository.save(book);
    }
    public Book getBookById(int book_id){
        return bookRepository.findById(book_id);
    }
    public List<Book> getAllBooks(){
        return bookRepository.findAll();
    }
    public void deleteBook(int id){
        bookRepository.deleteById(id);
    }
}
