package model;

import javax.xml.bind.annotation.*;
import java.util.List;

/**
 * Created by podsh on 25.04.2017.
 */
@XmlRootElement(name = "arrayOfMaterial")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = {
        "item"
})
public class ArrayOfMaterial {
    @XmlElement
    private List<Material> item;

    public ArrayOfMaterial() {
    }

    public List<Material> getItem() {
        return item;
    }

    public void setItem(List<Material> item) {
        this.item = item;
    }
}
