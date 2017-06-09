package dao;

import domain.Cut;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;

/**
 * Created by podsh on 08.05.2017.
 */
public class CutDao {
    public List<Cut> loadCutsByType(String type) {
        Session session = SessionHelper.startTransaction();
        Query<Cut> query = session.createQuery("from Cut where type = :type order by shortname", Cut.class);
        query.setParameter("type", type);
        List<Cut> list = query.list();
        SessionHelper.endTransaction();

        return list;
    }

    public Cut loadCutById(String cutId) {
        Session session = SessionHelper.startTransaction();
        Cut cut = session.get(Cut.class, cutId);
        SessionHelper.endTransaction();
        return cut;
    }

    public void save(Cut cut) {
        Session session = SessionHelper.startTransaction();
        session.save(cut);
        SessionHelper.endTransaction();
    }
}
