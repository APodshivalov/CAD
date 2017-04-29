package app;

import app.controllers.DrawController;
import app.model.Material;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;

/**
 * Наследник обычного канваса с обработкой изменения экрана и его перерисовкой
 * Created by podsh on 08.04.2017.
 */
public class ResizableCanvas extends Canvas {
    private Controller controller;
    private GraphicsContext gc;
    private double xLayout;

    public ResizableCanvas(Controller controller) {
        this.controller = controller;
        gc = this.getGraphicsContext2D();
        setOnMouseMoved(controller::onMouseMoved);
        setOnMouseDragged(controller::onMouseDrag);
        setOnMouseReleased(controller::onMouseReleased);
        setOnScroll(controller::onScroll);
        setOnMouseExited(controller::onMouseExited);
        setOnMouseClicked(controller::onMouseClickedOverCanvas);
    }

    /**
     * Перерисовка канваса
     */
    public void redraw() {
        this.setLayoutX(xLayout);
        this.setWidth(controller.getCanvasPane().getWidth() - xLayout);
        this.setHeight(controller.getCanvasPane().getHeight());
        gc.clearRect(0, 0, this.getWidth(), this.getHeight());
        controller.getModel().draw();
        drawCoordinates();
        if (controller.getCurrentEventListener() instanceof DrawController) {
            DrawController drawController = (DrawController) controller.getCurrentEventListener();
            if (controller.getNet().isSelected()) {
                drawController.drawDots();
            }
            drawController.drawCursor();
        }
        if (controller.getMaterialView().isSelected()){
            drawPalette();
        }
    }

    private void drawPalette() {
        double x = controller.getCanvas().getWidth() - 40;
        double y = 20;
        for (Material m : controller.getModel().getArrayOfMaterial().getItem()){
            gc.setFill(m.getColor());
            gc.fillRect(x, y, 20, 20);
            gc.setTextAlign(TextAlignment.RIGHT);
            gc.strokeText(m.getName(), x - 5, y + 14);
            y += 25;
        }
    }

    private void drawCoordinates() {
        double x0 = controller.getCoordinateUtils().fromRealX(0);
        double y0 = controller.getCoordinateUtils().fromRealY(0);
        gc.setStroke(Color.BLACK);
        gc.strokeLine(x0, y0, x0, y0 - 80);
        gc.strokeLine(x0, y0, x0 + 80, y0);
        gc.strokeLine(x0 + 80, y0, x0 + 70, y0 - 5);
        gc.strokeLine(x0 + 80, y0, x0 + 70, y0 + 5);
        gc.strokeLine(x0, y0 - 80, x0 + 5, y0 - 70);
        gc.strokeLine(x0, y0 - 80, x0 - 5, y0 - 70);
    }

    public double getXLayout() {
        return xLayout;
    }

    public void setXLayout(double xLayout) {
        this.xLayout = xLayout;
    }

    public void redraw(MouseEvent mouseEvent) {
        this.setLayoutX(xLayout);
        this.setWidth(controller.getCanvasPane().getWidth() - xLayout);
        this.setHeight(controller.getCanvasPane().getHeight());
        gc.clearRect(0, 0, this.getWidth(), this.getHeight());
        controller.getModel().draw(mouseEvent);
        drawCoordinates();
        if (controller.getMaterialView().isSelected()){
            drawPalette();
        }
    }
}
