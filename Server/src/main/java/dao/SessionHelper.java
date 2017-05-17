package dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/**
 * Created by podsh on 06.05.2017.
 */
public class SessionHelper {
    private static Session session;

    public static Session startTransaction() {
        SessionFactory sessionFactory = new Configuration().configure()
                .buildSessionFactory();
        session = sessionFactory.openSession();
        session.beginTransaction();
        return session;
    }

    public static void endTransaction() {
        session.getTransaction().commit();
        session.close();
    }
}
