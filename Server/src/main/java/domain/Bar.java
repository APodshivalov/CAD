package domain;

import model.Project;
import model.User;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by podsh on 07.05.2017.
 */
@Entity
public class Bar implements Serializable {
    @Id
    private String id;
    @ManyToOne(cascade = {CascadeType.ALL},fetch = FetchType.EAGER)
    private Point firstPoint;
    @ManyToOne(cascade = {CascadeType.ALL},fetch = FetchType.EAGER)
    private Point secondPoint;
    @Column(name="cut_id")
    private String cutId;
    @Column(name="material_id")
    private String materialId;
    @ManyToOne
    private ProjectInfo projectInfo;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Point getFirstPoint() {
        return firstPoint;
    }

    public void setFirstPoint(Point firstPoint) {
        this.firstPoint = firstPoint;
    }

    public Point getSecondPoint() {
        return secondPoint;
    }

    public void setSecondPoint(Point secondPoint) {
        this.secondPoint = secondPoint;
    }

    public ProjectInfo getProjectInfo() {
        return projectInfo;
    }

    public void setProjectInfo(ProjectInfo projectInfo) {
        this.projectInfo = projectInfo;
    }

    public String getCutId() {
        return cutId;
    }

    public void setCutId(String cutId) {
        this.cutId = cutId;
    }

    public String getMaterialId() {
        return materialId;
    }

    public void setMaterialId(String materialId) {
        this.materialId = materialId;
    }
}
