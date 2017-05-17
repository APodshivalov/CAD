package app.model;

import javafx.scene.paint.Color;

/**
 * Created by podsh on 25.04.2017.
 */
public class Material {
    private String name;
    private String id;
    private Color color;

    public Material(){}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (! (obj instanceof Material)) {
            return false;
        }
        Material other = (Material) obj;
        return this.getId().equals(other.getId());
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }
}
