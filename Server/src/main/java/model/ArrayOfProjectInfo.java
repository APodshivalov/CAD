package model;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by podsh on 09.05.2017.
 */
@XmlRootElement(name = "arrayOfProjectInfo")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = {
        "item"
})
public class ArrayOfProjectInfo {
    @XmlElement
    private List<ProjectInfo> item;

    public ArrayOfProjectInfo() {
        item = new ArrayList<>();
    }

    public List<ProjectInfo> getItem() {
        return item;
    }

    public void setItem(List<ProjectInfo> item) {
        this.item = item;
    }
}
