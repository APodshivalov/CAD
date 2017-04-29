package model;

import javax.xml.bind.annotation.*;
import java.util.List;

/**
 * Created by podsh on 29.04.2017.
 */
@XmlRootElement(name = "arrayOfCut")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = {
        "item"
})
public class ArrayOfCut {
    @XmlElement
    private List<Cut> item;

    public ArrayOfCut() {
    }

    public List<Cut> getItem() {
        return item;
    }

    public void setItem(List<Cut> item) {
        this.item = item;
    }
}