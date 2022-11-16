package edu.ebook.library.service;

import edu.ebook.library.model.HibernateSessionProvider;
import edu.ebook.library.model.dao.BooksData;
import edu.ebook.library.model.dao.BooksDetails;

import java.util.List;

public class BookManagingService {
    public void saveBooksData(BooksData booksData) {
        var session = HibernateSessionProvider.getSession();
        var transaction = session.beginTransaction();
        session.save(booksData);
        transaction.commit();
        session.close();
    }

    public void saveBooksDetails(BooksDetails booksDetails) {
        var session = HibernateSessionProvider.getSession();
        var transaction = session.beginTransaction();
        session.save(booksDetails);
        transaction.commit();
        session.close();
    }

    public List<BooksDetails> getAllBookDetails() {
        var session = HibernateSessionProvider.getSession();
        var transaction = session.beginTransaction();
        var query = session.createQuery("from BooksDetails");
        var bookDetailsList = query.list();
        transaction.commit();
        HibernateSessionProvider.closeSession(session);
        return bookDetailsList;
    }

    public List<BooksDetails> getAllBookDetailsByTitle(String titleName) {
        var session = HibernateSessionProvider.getSession();
        var transaction = session.beginTransaction();
        var query = session.createQuery("from BooksDetails where title like :title order by title");
        query.setParameter("title", "%" + titleName + "%");
        query.setCacheable(true);
        var bookDetailsList = query.list();
        transaction.commit();
        HibernateSessionProvider.closeSession(session);
        return bookDetailsList;
    }

    public List<BooksDetails> getAllBookDetailsByAuthor(String authorName) {
        var session = HibernateSessionProvider.getSession();
        var transaction = session.beginTransaction();
        var query = session.createQuery("from BooksDetails where author like :author order by title");
        query.setParameter("author", "%" + authorName + "%");
        query.setCacheable(true);
        var bookDetailsList = query.list();
        transaction.commit();
        HibernateSessionProvider.closeSession(session);
        return bookDetailsList;
    }
}
