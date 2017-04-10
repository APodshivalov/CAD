package app;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

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
    }
}
