package app.model;

import app.utils.Colors;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by podsh on 26.04.2017.
 */
public class ArrayOfMaterial {
    private List<Material> item;

    public ArrayOfMaterial() {
        item = new ArrayList<>();
    }



    public List<Material> getItem() {
        return item;
    }

    public void setItem(List<Material> item) {
        this.item = item;
    }

    public boolean add(Material selectedItem) {
        if (!item.contains(selectedItem)){
            selectedItem.setColor(Colors.getNextColor());
            item.add(selectedItem);
            return true;
        }
        return false;
    }

    public Material getMaterialFromArrayOfMaterials(Material material) {
        if(!add(material)) {
            return item.get(item.indexOf(material));
        } else {
            return material;
        }
    }
}
