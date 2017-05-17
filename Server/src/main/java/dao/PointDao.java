package dao;

import domain.Point;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;

/**
 * Created by podsh on 08.05.2017.
 */
public class PointDao {

    public void update(Point point) {
        Session session = SessionHelper.startTransaction();
        session.update(point);
        SessionHelper.endTransaction();
    }

    public Point loadByNativeId(String nativeId) {
        Session session = SessionHelper.startTransaction();
        Query<Point> query = session.createQuery("from Point where nativeid = :nativeId", Point.class);
        query.setParameter("nativeId", nativeId);
        List<Point> list = query.list();
        SessionHelper.endTransaction();
        if (list.size() == 1){
            return list.get(0);
        } else {
            return null;
        }
    }

    public void save(Point point) {
        Session session = SessionHelper.startTransaction();
        session.save(point);
        SessionHelper.endTransaction();
    }
}
