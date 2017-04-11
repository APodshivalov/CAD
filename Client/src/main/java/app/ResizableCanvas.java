package app;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

/**
 * Наследник обычного канваса с обработкой изменения экрана и его перерисовкой
 * Created by podsh on 08.04.2017.
 */
public class ResizableCanvas extends Canvas{
    private Controller controller;
    private GraphicsContext gc;

    public ResizableCanvas(Controller controller) {
        this.controller = controller;
        gc = this.getGraphicsContext2D();
    }

    /**
     * Перерисовка канваса
     */
    public void redraw() {
        this.setWidth(controller.getCanvasPane().getWidth());
        this.setHeight(controller.getCanvasPane().getHeight());
        gc.clearRect(0, 0, this.getWidth(), this.getHeight());
        controller.getModel().draw();
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

}
