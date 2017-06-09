package dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/**
 * Created by podsh on 06.05.2017.
 */
public class SessionHelper {
    private static Session session;
    private static SessionFactory  sessionFactory = new Configuration().configure()
            .buildSessionFactory();

    public static Session startTransaction() {
        session = sessionFactory.openSession();
        session.beginTransaction();
        return session;
    }

    public static void endTransaction() {
        session.getTransaction().commit();
        session.close();
    }
}
