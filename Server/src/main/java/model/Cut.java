package model;

import javax.xml.bind.annotation.*;

/**
 * Created by podsh on 29.04.2017.
 */
@XmlRootElement(name = "cut")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = {
        "id",
        "type",
        "fullName",
        "shortName",
        "h",
        "b",
        "s",
        "t",
        "r"
})
public class Cut {

    @XmlElement
    private String id;
    @XmlElement
    private String type;
    @XmlElement
    private String fullName;
    @XmlElement
    private String shortName;
    @XmlElement
    private double h;
    @XmlElement
    private double b;
    @XmlElement
    private double s;
    @XmlElement
    private double t;
    @XmlElement
    private double r;

    public Cut() {}

    public Cut(String id, String type, String fullName, String shortName, double h, double b, double s, double t, double r) {
        this.id = id;
        this.type = type;
        this.fullName = fullName;
        this.shortName = shortName;
        this.h = h;
        this.b = b;
        this.s = s;
        this.t = t;
        this.r = r;
    }

    public Cut(String[] items) {
        id = items[0];
        type = items[1];
        fullName = items[2];
        shortName = items[3];
        h = Double.parseDouble(items[4]);
        b = Double.parseDouble(items[5]);
        s = Double.parseDouble(items[6]);
        t = Double.parseDouble(items[7]);
        r = Double.parseDouble(items[8]);
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public double getH() {
        return h;
    }

    public void setH(double h) {
        this.h = h;
    }

    public double getB() {
        return b;
    }

    public void setB(double b) {
        this.b = b;
    }

    public double getS() {
        return s;
    }

    public void setS(double s) {
        this.s = s;
    }

    public double getT() {
        return t;
    }

    public void setT(double t) {
        this.t = t;
    }

    public double getR() {
        return r;
    }

    public void setR(double r) {
        this.r = r;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }
}
