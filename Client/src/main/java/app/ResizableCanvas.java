package app;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;

/**
 * Наследник обычного канваса с обработкой изменения экрана и его перерисовкой
 * Created by podsh on 08.04.2017.
 */
public class ResizableCanvas extends Canvas{
    private Controller controller;
    private Pane pane;

    public ResizableCanvas(Controller controller) {
        this.controller = controller;
        pane = controller.getCanvasPane();
        pane.getChildren().add(this);
        pane.widthProperty().addListener(evt -> this.redraw());
        pane.heightProperty().addListener(evt -> this.redraw());
    }

    /**
     * Перерисовка канваса
     */
    public void redraw() {
        this.setWidth(pane.getWidth());
        this.setHeight(pane.getHeight());
        GraphicsContext gc = this.getGraphicsContext2D();
        gc.clearRect(0, 0, this.getWidth(), this.getHeight());
        controller.getModel().draw();
    }
}
