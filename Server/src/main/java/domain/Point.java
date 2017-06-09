package domain;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by podsh on 07.05.2017.
 */
@Entity
public class Point implements Serializable {
    @Id
    private String id;
    private double x;
    private double y;
    @OneToOne(cascade = {CascadeType.ALL})
    private DomainForce force;
    @OneToOne(cascade = {CascadeType.ALL})
    private Reaction reaction;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public DomainForce getForce() {
        return force;
    }

    public void setForce(DomainForce force) {
        this.force = force;
    }

    public Reaction getReaction() {
        return reaction;
    }

    public void setReaction(Reaction reaction) {
        this.reaction = reaction;
    }

    @Override
    public String toString() {
        return id;
    }
}
