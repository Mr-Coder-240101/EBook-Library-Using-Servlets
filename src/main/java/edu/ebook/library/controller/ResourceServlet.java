package edu.ebook.library.controller;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Map;

@WebServlet("/")
public class ResourceServlet extends HttpServlet {
    private static final String RESOURCE_PATH = "C:\\Users\\Jay Rathod\\Documents\\MyDocuments\\Java\\EBook Library Using Servlets\\src\\main\\resources\\";

    private static Map<String, String> urlToFileMapper = Map.of("/", "index.html",
                                                                "/index", "index.html",
                                                                "/upload", "upload.html",
                                                                "/error", "error.html",
                                                                "/fevicon", "fevicon.jpg",
                                                                "/script", "app.js",
                                                                "/image-left", "image-left.png",
                                                                "/right-shape", "right-shape.png");

    private static Map<String, String> urlToContentTypeMapper = Map.of("/", "text/html",
                                                                "/index", "text/html",
                                                                "/upload", "text/html",
                                                                "/error", "text/html",
                                                                "/fevicon", "image/jpeg",
                                                                "/script", "text/javascript",
                                                                "/image-left", "image/png",
                                                                "/right-shape", "image/png");

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        if (urlToFileMapper.keySet().contains(request.getServletPath())) {
            var filePath = RESOURCE_PATH + urlToFileMapper.get(request.getServletPath());
            var fileInputStream = new FileInputStream(new File(filePath));
            var servletOutputStream = response.getOutputStream();

            var buffer = new byte[1024];
            var length = fileInputStream.read(buffer);
            while (length > 0) {
                servletOutputStream.write(buffer, 0, length);
                length = fileInputStream.read(buffer);
            }

            response.setContentType(urlToContentTypeMapper.get(request.getServletPath()));

            fileInputStream.close();
            servletOutputStream.close();
        } else {
            response.sendRedirect("error");
        }
    }
}
