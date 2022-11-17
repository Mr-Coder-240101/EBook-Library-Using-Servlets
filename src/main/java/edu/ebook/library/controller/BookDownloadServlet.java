package edu.ebook.library.controller;

import edu.ebook.library.service.BookManagingService;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/book/download")
public class BookDownloadServlet extends HttpServlet {
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        var id = request.getParameter("id");
        var bookManagingService = new BookManagingService();

        var bookData = bookManagingService.getBooksData(id);
        var bookDetails = bookManagingService.getBooksDetails(id);

        response.setContentType("application/pdf");
        response.addHeader("Content-Disposition", "attachment; filename=" + bookDetails.getTitle() + ".pdf");
        response.setContentLength(bookData.getData().length);

        var servletOutputStream = response.getOutputStream();
        servletOutputStream.write(bookData.getData());

        servletOutputStream.close();
    }
}
