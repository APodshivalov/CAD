package app;

import app.utils.CoordinateUtils;
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
        double x0 = controller.getCoordinateUtils().fromRealX(0);
        double y0 = controller.getCoordinateUtils().fromRealY(0);
        gc.strokeLine(x0, y0, x0, y0 - 80);
        gc.strokeLine(x0, y0, x0 + 80, y0);
        gc.strokeLine(x0 + 80, y0, x0 + 70, y0-5);
        gc.strokeLine(x0 + 80, y0, x0 + 70, y0+5);
        gc.strokeLine(x0, y0-80, x0 + 5, y0-70);
        gc.strokeLine(x0, y0-80, x0 - 5, y0-70);
    }

}
