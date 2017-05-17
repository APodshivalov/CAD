package model;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by podsh on 01.05.2017.
 */
@XmlRootElement(name = "project")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = {
        "projectInfo",
        "bars"
})
public class Project {
    @XmlElement
    private ProjectInfo projectInfo;
    @XmlElement
    private List<Bar> bars;

    public Project() {
        bars = new ArrayList<>();
    }

    public List<Bar> getBars() {
        return bars;
    }

    public void setBars(List<Bar> bars) {
        this.bars = bars;
    }

    public ProjectInfo getProjectInfo() {
        return projectInfo;
    }

    public void setProjectInfo(ProjectInfo projectInfo) {
        this.projectInfo = projectInfo;
    }
}
