package app.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by podsh on 24.05.2017.
 */
public class ArrayOfResult {
    private List<Result> item;

    public ArrayOfResult(){
        item = new ArrayList<>();
    }

    public List<Result> getItem() {
        return item;
    }

    public void setItem(List<Result> item) {
        this.item = item;
    }

    public Result getResultById(String id) {
        return item.stream().filter(result -> result.getBarId().equals(id)).findFirst().get();
    }
}
