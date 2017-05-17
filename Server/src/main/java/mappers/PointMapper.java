package mappers;

import model.Point;

/**
 * Created by podsh on 08.05.2017.
 */
public class PointMapper {
    public static domain.Point map(Point clientPoint) {
        domain.Point serverPoint = new domain.Point();
        serverPoint.setId(clientPoint.getId());
        serverPoint.setNativeId(clientPoint.getNativeId());
        serverPoint.setX(clientPoint.getX());
        serverPoint.setY(clientPoint.getY());
        serverPoint.setForce(ForceMapper.map(clientPoint.getForce()));
        serverPoint.setReaction(ReactionMapper.map(clientPoint.getReaction()));
        return serverPoint;
    }

    public static Point map(domain.Point serverPoint) {
        Point clientPoint = new Point();
        clientPoint.setId(serverPoint.getId());
        clientPoint.setNativeId(serverPoint.getNativeId());
        clientPoint.setX(serverPoint.getX());
        clientPoint.setY(serverPoint.getY());
        clientPoint.setForce(ForceMapper.map(serverPoint.getForce()));
        clientPoint.setReaction(ReactionMapper.map(serverPoint.getReaction()));
        return clientPoint;
    }
}
