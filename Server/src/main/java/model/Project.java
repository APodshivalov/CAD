package model;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by podsh on 01.05.2017.
 */
@XmlRootElement(name = "arrayOfCut")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = {
        "bars"
})
public class ArrayOfBars {
    @XmlElement
    private List<Bar> bars;

    public ArrayOfBars() {
        bars = new ArrayList<>();
    }

    public List<Bar> getBars() {
        return bars;
    }

    public void setBars(List<Bar> bars) {
        this.bars = bars;
    }
}
