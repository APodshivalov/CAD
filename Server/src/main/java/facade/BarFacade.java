package facade;

import dao.SessionHelper;
import domain.Bar;
import org.hibernate.Session;

/**
 * Created by podsh on 09.05.2017.
 */
public class BarFacade {

    public void save(Bar bar) {
        Session session = SessionHelper.startTransaction();
        if (bar.getId() == null) {
            session.save(bar);
        } else {
            session.update(bar);
        }
        SessionHelper.endTransaction();
    }
}
