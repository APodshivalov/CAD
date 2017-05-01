package app.model;

import app.Controller;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by podsh on 01.05.2017.
 */
public class ArrayOfBars {
    private List<Bar> bars;

    public ArrayOfBars() {
        bars = new ArrayList<>();
    }

    public List<Bar> getBars() {
        return bars;
    }

    public void setBars(List<Bar> bars) {
        this.bars = bars;
    }

    public void add(Bar bar, Material currentMaterial, Cut currentCut) {
        if (bars.stream().noneMatch(bar::equals)) {
            bars.add(bar);
            bar.setMaterial(currentMaterial);
            bar.setCut(currentCut);
        }
    }

    public void setSelect(double x, double y, double x1, double y1) {
        for (Bar bar : bars) {
            bar.setIsSelected(bar.between(x, y, x1, y1));
        }
    }

    public void draw(Controller controller) {
        bars.forEach(bar -> bar.draw(controller));
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
}
