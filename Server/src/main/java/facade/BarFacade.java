package facade;

import dao.BarDao;
import dao.SessionHelper;
import domain.Bar;
import org.hibernate.Session;

/**
 * Created by podsh on 09.05.2017.
 */
public class BarFacade {
    private BarDao dao;

    public BarFacade() {
        dao = new BarDao();
    }

    public void save(Bar bar) {
        dao.saveOrDelete(bar);
    }
}
