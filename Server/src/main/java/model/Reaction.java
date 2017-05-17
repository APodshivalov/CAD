package model;

import javax.xml.bind.annotation.*;

/**
 * Created by podsh on 08.05.2017.
 */
@XmlRootElement(name = "reaction")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = {
        "id",
        "name",
        "angle"
})
public class Reaction {
    @XmlElement
    private String id;
    @XmlElement
    private String name;
    @XmlElement
    private int angle;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAngle() {
        return angle;
    }

    public void setAngle(int angle) {
        this.angle = angle;
    }

    @Override
    public String toString() {
        return name + angle;
    }
}
