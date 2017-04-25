package app.model;

import java.util.List;

/**
 * Created by podsh on 26.04.2017.
 */
public class ArrayOfMaterial {
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
