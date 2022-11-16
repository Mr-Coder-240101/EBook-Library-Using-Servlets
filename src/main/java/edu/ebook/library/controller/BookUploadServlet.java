package edu.ebook.library.controller;

import edu.ebook.library.model.dao.BooksData;
import edu.ebook.library.model.dao.BooksDetails;
import edu.ebook.library.service.BookManagingService;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

@WebServlet("/book/upload")
@MultipartConfig
public class BookUploadServlet extends HttpServlet {
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        var id = UUID.randomUUID().toString();
        var bookInputStream = request.getPart("book").getInputStream();
        var bookTitle = request.getParameter("title");
        var bookAuthor = request.getParameter("author");
        var bookPages = Integer.parseInt(request.getParameter("pages"));
        var bookManagingService = new BookManagingService();

        BooksData booksData = new BooksData();
        booksData.setId(id);
        booksData.setData(bookInputStream.readAllBytes());
        bookManagingService.saveBooksData(booksData);

        BooksDetails booksDetails = new BooksDetails();
        booksDetails.setId(id);
        booksDetails.setTitle(bookTitle);
        booksDetails.setAuthor(bookAuthor);
        booksDetails.setPages(bookPages);
        bookManagingService.saveBooksDetails(booksDetails);
    }
}
