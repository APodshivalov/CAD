package model;

import javax.xml.bind.annotation.*;

/**
 * Created by podsh on 01.05.2017.
 */
@XmlRootElement(name = "point")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = {
        "id",
        "nativeId",
        "x",
        "y",
        "force",
        "reaction"
})
public class Point {
    @XmlElement
    private String id;
    @XmlElement
    private String nativeId;
    @XmlElement
    private double x;
    @XmlElement
    private double y;
    @XmlElement
    private Force force;
    @XmlElement
    private Reaction reaction;

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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Force getForce() {
        return force;
    }

    public void setForce(Force force) {
        this.force = force;
    }

    public Reaction getReaction() {
        return reaction;
    }

    public void setReaction(Reaction reaction) {
        this.reaction = reaction;
    }

    public String getNativeId() {
        return nativeId;
    }

    public void setNativeId(String nativeId) {
        this.nativeId = nativeId;
    }

    @Override
    public String toString() {
        return "(X: " + x + ", Y:" + y + " Reaction: " + reaction + " Force:" + force + ")" ;
    }
}
