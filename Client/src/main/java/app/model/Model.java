package app.model;

import app.Controller;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by podsh on 08.04.2017.
 * Стержневая модель. Отвечает за хранение информации о конструкции.
 */
public class Model {
    private List<Bar> bars;
    private Controller controller;

    public Model(Controller controller){
        this.controller = controller;
        bars = new ArrayList<>();
    }

    /**
     * Отрисовка модели
     */
    public void draw(){
        bars.forEach(bar -> bar.draw(controller.getGraphicsContext()));
    }

    /**
     * Добавить стержень
     * @param bar Стержень
     */
    public void addBar(Bar bar) {
        bars.add(bar);
    }
}
