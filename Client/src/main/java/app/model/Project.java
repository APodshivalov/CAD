package app.model;

import app.Controller;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by podsh on 01.05.2017.
 */
public class Project {
    private ProjectInfo projectInfo;
    private List<Bar> bars;

    public Project() {
        bars = new ArrayList<>();
    }

    public void add(Bar bar, Material currentMaterial, Cut currentCut) {
        if (bars.stream().noneMatch(bar::equals)) {
            bars.add(bar);
            bar.setMaterial(currentMaterial);
            bar.setCut(currentCut);
        }
    }

    public void add(Bar bar) {
        if (bars.stream().noneMatch(bar::equals)) {
            bars.add(bar);
        }
    }

    public void setSelect(double x, double y, double x1, double y1) {
        for (Bar bar : bars) {
            bar.setIsSelected(bar.between(x, y, x1, y1));
        }
    }

    public void setCurrentMaterial(Material selectedItem) {
        bars.stream().filter(Bar::isSelected).forEach(bar -> {
            bar.setMaterial(selectedItem);
            bar.setIsSelected(false);
        });
    }

    public void setCurrentCut(Cut selectedItem) {
        bars.stream().filter(Bar::isSelected).forEach(bar -> {
            bar.setCut(selectedItem);
            bar.setIsSelected(false);
        });
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

    public void clear() {
        bars.clear();
        projectInfo = null;
    }

    public boolean contains(Point point) {
        return bars.stream().anyMatch(bar -> bar.getFirstPoint().equals(point) || bar.getSecondPoint().equals(point));
    }
}
