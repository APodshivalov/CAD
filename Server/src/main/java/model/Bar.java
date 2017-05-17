package model;

import javax.xml.bind.annotation.*;

/**
 * Created by podsh on 01.05.2017.
 */
@XmlRootElement(name = "bar")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = {
        "id",
        "firstPoint",
        "secondPoint",
        "cut",
        "material"
})
public class Bar {
    @XmlElement
    private String id;
    @XmlElement
    private Point firstPoint;
    @XmlElement
    private Point secondPoint;
    @XmlElement
    private Cut cut;
    @XmlElement
    private Material material;

    public Point getFirstPoint() {
        return firstPoint;
    }

    public void setFirstPoint(Point firstPoint) {
        this.firstPoint = firstPoint;
    }

    public Point getSecondPoint() {
        return secondPoint;
    }

    public void setSecondPoint(Point secondPoint) {
        this.secondPoint = secondPoint;
    }

    public Cut getCut() {
        return cut;
    }

    public void setCut(Cut cut) {
        this.cut = cut;
    }

    public Material getMaterial() {
        return material;
    }

    public void setMaterial(Material material) {
        this.material = material;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Point 1: " + firstPoint + " Point 2: " + secondPoint + ". Material: " + material + ", Cut: " + cut;
    }
}
