package app;

import app.controllers.DrawController;
import app.model.Material;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
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
        addEventFilter(MouseEvent.ANY, (e) -> this.requestFocus());
        setOnKeyPressed(this::keyPressed);
        gc = this.getGraphicsContext2D();
        setOnMouseMoved(controller::onMouseMoved);
        setOnMouseDragged(controller::onMouseDrag);
        setOnMouseReleased(controller::onMouseReleased);
        setOnScroll(controller::onScroll);
        setOnMouseExited(event -> redrawExit());
        setOnMouseClicked(controller::onMouseClickedOverCanvas);

    }

    private void keyPressed(KeyEvent event) {
        System.out.println(event.getCode());
        if (event.getCode() == KeyCode.DELETE) {
            controller.getModel().deleteSelected();
            redraw();
        }
        if (event.getCode() == KeyCode.ESCAPE) {
            controller.getModel().unselect();
            redraw();
        }
    }

    /**
     * Перерисовка канваса
     */
    public void redraw() {
        draw();
        controller.getModel().draw();
    }

    private void draw(){
        this.setLayoutX(xLayout);
        this.setWidth(controller.getCanvasPane().getWidth() - xLayout);
        this.setHeight(controller.getCanvasPane().getHeight());
        gc.clearRect(0, 0, this.getWidth(), this.getHeight());
        drawCoordinates();
        controller.getCurrentEventListener().redraw();
        if (controller.getMaterialView().isSelected()){
            drawPalette();
        }
    }

    public void redrawExit() {
        gc.clearRect(0, 0, this.getWidth(), this.getHeight());
        drawCoordinates();
        if (controller.getMaterialView().isSelected()){
            drawPalette();
        }
        controller.getModel().draw();
    }

    private void drawPalette() {
        double x = controller.getCanvas().getWidth() - 40;
        double y = 20;
        for (Material m : controller.getModel().getArrayOfMaterial().getItem()){
            gc.setFill(m.getColor());
            gc.fillRect(x, y, 20, 20);
            gc.setTextAlign(TextAlignment.RIGHT);
            gc.strokeText(m.getName(), x - 5, y + 14);
            gc.setTextAlign(TextAlignment.LEFT);
            y += 25;
        }
    }

    private void drawCoordinates() {
        double x0 = controller.getCoordinateUtils().fromRealX(0);
        double y0 = controller.getCoordinateUtils().fromRealY(0);
        gc.setStroke(Color.BLACK);
        gc.strokeText("x", x0 + 72, y0 + 15);
        gc.strokeText("y", x0 + 8, y0 - 73);
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
        draw();
        controller.getModel().draw(mouseEvent);
    }
}
