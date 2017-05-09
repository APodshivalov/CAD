package app.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by podsh on 09.05.2017.
 */
public class ArrayOfProjectInfo {
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
