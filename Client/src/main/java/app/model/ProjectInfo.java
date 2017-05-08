package app.model;

/**
 * Created by podsh on 08.05.2017.
 */
public class ProjectInfo {
    private String id;
    private String name;

    public ProjectInfo(){

    }

    public ProjectInfo(String text) {
        name = text;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
