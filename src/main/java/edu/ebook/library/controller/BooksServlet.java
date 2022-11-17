package edu.ebook.library.controller;

import com.google.gson.Gson;
import edu.ebook.library.model.dao.BooksDetails;
import edu.ebook.library.service.BookManagingService;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/books")
public class BooksServlet extends HttpServlet {
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        var option = request.getParameter("option");
        var keyword = request.getParameter("keyword");
        var bookManagingService = new BookManagingService();

        var bookDetailList = (List<BooksDetails>) new ArrayList<BooksDetails>();
        if ("title".equals(option)) {
            bookDetailList = bookManagingService.getAllBookDetailsByTitle(keyword);
        } else if ("author".equals(option)){
            bookDetailList = bookManagingService.getAllBookDetailsByAuthor(keyword);
        } else {
            bookDetailList = bookManagingService.getAllBookDetails();
        }

        var bookDetailsListJson = new Gson().toJson(bookDetailList);

        response.setContentType("application/json");
        PrintWriter printWriter = response.getWriter();
        printWriter.write(bookDetailsListJson);
    }
}
