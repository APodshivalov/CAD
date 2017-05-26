package model;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by podsh on 24.05.2017.
 */
@XmlRootElement(name = "arrayOfResult")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = {
        "item"
})
public class ArrayOfResult {
    @XmlElement
    private List<Result> item;

    public ArrayOfResult() {
        item = new ArrayList<>();
    }

    public List<Result> getItem() {
        return item;
    }

    public void setItem(List<Result> item) {
        this.item = item;
    }

    public void add(Result result) {
        item.add(result);
    }
}
