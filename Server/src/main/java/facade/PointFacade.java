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
    public Point save(Point point) {
        if (point.getId() == null) { //Не синхронизироано с сервером
            Point serverPoint = pointDao.loadByNativeId(point.getNativeId());
            if (serverPoint == null){
                pointDao.save(point);
            } else {
                return serverPoint;
            }
        } else {
            pointDao.update(point);
        }
        return point;
    }
}
