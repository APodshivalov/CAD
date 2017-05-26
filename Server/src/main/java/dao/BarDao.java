package dao;

import domain.Bar;
import org.hibernate.Session;

/**
 * Created by podsh on 07.05.2017.
 */
public class BarDao {
    public void saveOrDelete(Bar bar) {
        Session session = SessionHelper.startTransaction();
        session.saveOrUpdate(bar);
        SessionHelper.endTransaction();
    }
}
