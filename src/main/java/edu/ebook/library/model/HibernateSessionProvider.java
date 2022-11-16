package edu.ebook.library.model;

import edu.ebook.library.model.dao.BooksData;
import edu.ebook.library.model.dao.BooksDetails;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.Properties;

public class HibernateSessionProvider {
    private static SessionFactory sessionFactory;

    private static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            synchronized (HibernateSessionProvider.class) {
                if (sessionFactory == null) {
                    Properties properties = new Properties();
                    properties.setProperty("hibernate.connection.driver_class", "com.mysql.jdbc.Driver");
                    properties.setProperty("hibernate.connection.url", "jdbc:mysql://localhost:3306/ebook-library?useSSL=false&allowPublicKeyRetrieval=true&sessionVariables=sql_mode='NO_ENGINE_SUBSTITUTION'&jdbcCompliantTruncation=false");
                    properties.setProperty("hibernate.connection.username", "db_user");
                    properties.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQL55Dialect");
                    properties.setProperty("hibernate.hbm2ddl.auto", "update");
                    properties.setProperty("hibernate.show_query", "true");
                    properties.setProperty("hibernate.cache.use_second_level_cache", "true");
                    properties.setProperty("hibernate.cache.region.factory_class", "org.hibernate.cache.ehcache.EhCacheRegionFactory");
                    properties.setProperty("hibernate.use_query_cache", "true");
                    sessionFactory = new Configuration()
                            .addAnnotatedClass(BooksData.class)
                            .addAnnotatedClass(BooksDetails.class)
                            .addProperties(properties)
                            .buildSessionFactory();
                }
            }
        }

        return sessionFactory;
    }

    public static Session getSession() {
        return getSessionFactory().openSession();
    }

    public static void closeSession(Session session) {
        session.close();
    }
}
