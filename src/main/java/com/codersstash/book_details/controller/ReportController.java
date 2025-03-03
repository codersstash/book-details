package com.codersstash.book_details.controller;

import com.codersstash.book_details.repository.BookRepository;
import com.codersstash.book_details.service.BookService;
import com.codersstash.book_details.service.ReportService;
import jakarta.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Controller
public class ReportController {
    @Autowired
    private BookService bookService;
    @Autowired
    private ReportService reportService;

    @GetMapping("/printAllBookDetails")
    @CrossOrigin(value = "*")
    public void printAllBookDetails(HttpServletResponse response) {
        InputStream inputStream = getClass().getResourceAsStream("/reports/bookdetails.jasper");
        Map param = new HashMap<>();
        param.put("title", "All Book Details");
        final JRBeanCollectionDataSource beanCollectionDataSource = new JRBeanCollectionDataSource(bookService.getAllBooks());
        JasperPrint jasperPrint =reportService.generateReport(inputStream, param, beanCollectionDataSource);
        response.setContentType("application/x-pdf");
        response.setHeader("Content-Disposition", "attachment; filename=bookdetails.pdf");
        try {
            JasperExportManager.exportReportToPdfStream(jasperPrint,response.getOutputStream());
            response.getOutputStream().flush();
            response.getOutputStream().close();
        } catch (JRException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/printBookDetails/{id}")
    @CrossOrigin(value = "*")
    public void printBookDetails(@PathVariable int id ,HttpServletResponse response) {
        InputStream inputStream = getClass().getResourceAsStream("/reports/bookdetails.jasper");
        Map param = new HashMap<>();
        param.put("title", "All Book Details");
        final JRBeanCollectionDataSource beanCollectionDataSource = new JRBeanCollectionDataSource(Collections.singleton(bookService.getBookById(id)));
        JasperPrint jasperPrint =reportService.generateReport(inputStream, param, beanCollectionDataSource);
        response.setContentType("application/x-pdf");
        response.setHeader("Content-Disposition", "attachment; filename=bookdetails.pdf");
        try {
            JasperExportManager.exportReportToPdfStream(jasperPrint,response.getOutputStream());
            response.getOutputStream().flush();
            response.getOutputStream().close();
        } catch (JRException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
