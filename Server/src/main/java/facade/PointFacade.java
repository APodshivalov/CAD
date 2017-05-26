package facade;

import dao.PointDao;
import domain.Point;

/**
 * Created by podsh on 09.05.2017.
 */
public class PointFacade {
    private PointDao pointDao;

    public PointFacade() {
        pointDao = new PointDao();
    }

    public void save(Point point) {
        pointDao.startSession();
        pointDao.saveOrUpdate(point);
        pointDao.stopSession();
    }
}
