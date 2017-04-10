package app.model;

import app.Controller;
import javafx.scene.canvas.GraphicsContext;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by podsh on 08.04.2017.
 * Стержневая модель. Отвечает за хранение информации о конструкции.
 */
public class Model {
    private List<Bar> bars;
    private Controller controller;
    private GraphicsContext gc;

    public Model(Controller controller){
        this.controller = controller;
        gc = controller.getCanvas().getGraphicsContext2D();
        bars = new ArrayList<>();
    }

    /**
     * Отрисовка модели
     */
    public void draw(){
        bars.forEach(bar -> bar.draw(controller));
        drawCoordinates();
    }

    private void drawCoordinates() {
        double height = controller.getCanvas().getHeight() - 20;
        gc.strokeLine(20, height, 20, height - 80);
        gc.strokeLine(20, height, 100, height);
        gc.strokeLine(100, height, 90, height-5);
        gc.strokeLine(100, height, 90, height+5);
        gc.strokeLine(20, height-80, 25, height-70);
        gc.strokeLine(20, height-80, 15, height-70);
    }

    /**
     * Добавить стержень
     * @param bar Стержень
     */
    public void addBar(Bar bar) {
        bars.add(bar);
    }
}
