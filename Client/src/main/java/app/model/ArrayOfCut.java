package app.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by podsh on 29.04.2017.
 */
public class ArrayOfCut {
    private List<Cut> item;

    public ArrayOfCut() {
        item = new ArrayList<>();
    }



    public List<Cut> getItem() {
        return item;
    }

    public void setItem(List<Cut> item) {
        this.item = item;
    }

    public void add(Cut selectedItem) {
        if (!item.contains(selectedItem)){
            item.add(selectedItem);
        }
    }
}
