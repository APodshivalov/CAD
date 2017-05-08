package app.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by podsh on 09.05.2017.
 */
public class ArrayOfProjectInfo {
    private List<ProjectInfo> list;

    public ArrayOfProjectInfo() {
        list = new ArrayList<>();
    }

    public List<ProjectInfo> getList() {
        return list;
    }

    public void setList(List<ProjectInfo> list) {
        this.list = list;
    }
}
