package mappers;

import domain.DomainForce;
import model.Force;

/**
 * Created by podsh on 08.05.2017.
 */
public class ForceMapper {

    public static DomainForce map(Force force) {
        DomainForce serverForce = new DomainForce();
        serverForce.setId(force.getId());
        serverForce.setM(force.getM());
        serverForce.setX(force.getX());
        serverForce.setY(force.getY());
        return serverForce;
    }

    public static Force map(DomainForce serverForce) {
        Force clientForce = new Force();
        clientForce.setId(serverForce.getId());
        clientForce.setM((int)serverForce.getM());
        clientForce.setX((int)serverForce.getX());
        clientForce.setY((int)serverForce.getY());
        return clientForce;
    }
}
