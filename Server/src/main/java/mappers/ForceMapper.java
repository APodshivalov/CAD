package mappers;

import model.Force;

/**
 * Created by podsh on 08.05.2017.
 */
public class ForceMapper {

    public static domain.Force map(Force force) {
        domain.Force serverForce = new domain.Force();
        serverForce.setId(force.getId());
        serverForce.setM(force.getM());
        serverForce.setX(force.getX());
        serverForce.setY(force.getY());
        return serverForce;
    }

    public static Force map(domain.Force serverForce) {
        Force clientForce = new Force();
        clientForce.setId(serverForce.getId());
        clientForce.setM(serverForce.getM());
        clientForce.setX(serverForce.getX());
        clientForce.setY(serverForce.getY());
        return clientForce;
    }
}
