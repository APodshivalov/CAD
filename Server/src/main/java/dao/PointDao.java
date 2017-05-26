package dao;

import domain.Point;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;

/**
 * Created by podsh on 08.05.2017.
 */
public class PointDao {
    private Session session;

    public void startSession() {
        session = SessionHelper.startTransaction();
    }

    public void stopSession() {
        SessionHelper.endTransaction();
    }

    public void saveOrUpdate(Point point) {
        session.saveOrUpdate(point);
    }
}
