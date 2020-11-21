/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.CS250;

import java.util.Stack;
import javafx.event.EventHandler;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;

/**
 *
 * @author Blazi
 */
public class myCanvas extends LayoutController{

    /**
     *
     */
    public final Stack<Double[]> dimensionStack;

    /**
     *
     */
    public final Stack<Double[]> redoDimensionStack;

    /**
     *
     */
    public final Stack<Image> imageStack;

    /**
     *
     */
    public final Stack<Image> redoImageStack;

    /**
     *
     */
    public final Canvas canvas;

    /**
     *
     */
    public final Canvas tempCanvas;
    private final LayoutController controller;
    
    /**
     * @param LC************************************************************************/
    public myCanvas(LayoutController LC){
        canvas = new Canvas(1048, 664);
        tempCanvas = new Canvas(1048, 664);
        dimensionStack = new Stack<>();
        redoDimensionStack = new Stack<>();
        imageStack = new Stack<>();
        redoImageStack = new Stack<>();
        controller = LC;
        
        dimensionStack.push(new Double[]{1048.0,662.0});
        imageStack.push(canvas.snapshot(null, null));
        
        
        setOnMousePressed();
        setOnMouseDragged();
        setOnMouseReleased();
        setOnMouseHover();
    }

    /**
     *
     * @param can
     * @param temp
     * @param dimen
     * @param redoDimen
     * @param image
     * @param redoImage
     * @param lc
     */
    public myCanvas(Canvas can, Canvas temp, Stack<Double[]> dimen, Stack<Double[]> redoDimen, Stack<Image> image, Stack<Image> redoImage, LayoutController lc){
        canvas = can;
        tempCanvas = temp;
        dimensionStack = dimen;
        redoDimensionStack = redoDimen;
        imageStack = image;
        redoImageStack = redoImage;
        controller = lc;
    }
    
    /**
     *
     * @return
     */
    public Stack<Double[]> getDimensionStack() {
        return dimensionStack;
    }

    /**
     *
     * @return
     */
    public Stack<Double[]> getRedoDimensionStack() {
        return redoDimensionStack;
    }

    /**
     *
     * @return
     */
    public Stack<Image> getImageStack() {
        return imageStack;
    }

    /**
     *
     * @return
     */
    public Stack<Image> getRedoImageStack() {
        return redoImageStack;
    }
    
    /**
     *
     * @return
     */
    @Override
    public Canvas getCanvas() {
        return canvas;
    }
    
    /**
     *
     * @return
     */
    @Override
    public Canvas getTempCanvas() {
        return tempCanvas;
    }
    /**************************************************************************/
    
    
    private void setOnMousePressed(){
        canvas.setOnMousePressed(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent mouse) {
                controller.drawInit(mouse);
            }
        });
        tempCanvas.setOnMousePressed(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent mouse) {
                controller.drawInit(mouse);
            }
        });
    }
    
    private void setOnMouseDragged(){
        canvas.setOnMouseDragged(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent mouse) {
                controller.drawDrag(mouse);
            }
        });
        tempCanvas.setOnMouseDragged(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent mouse) {
                controller.drawDrag(mouse);
            }
        });
    }
    
    private void setOnMouseReleased(){
        canvas.setOnMouseReleased(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent mouse) {
                controller.drawReleased(mouse);
                
            }
        });
        tempCanvas.setOnMouseReleased(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent mouse) {
                controller.drawReleased(mouse);
                
            }
        });
    }
    
    private void setOnMouseHover(){
        tempCanvas.setOnMouseMoved(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent mouse) {
                controller.drawHover(mouse);
            }
        });
    }
    
    /**************************************************************************/
    
    
}
