/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose myTools | Templates
 * and open the template in the editor.
 */
package com.CS250;

import javafx.event.EventHandler;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;

/**
 *
 * @author Blazi
 */
public class myTools{
   
    /**
     *
     */
    public GraphicsContext gc;

    /**
     *
     */
    public LayoutController lc;
    
    private Line line = new Line();
    private Boolean selectOn = false, filterMade = false, selectionMoved = false;
    private double circleStartX, circleStartY, circleEndX, circleEndY;
    private double squareStartX, squareStartY, squareEndX, squareEndY;
    private double triangleStartX, triangleStartY, triangleEndX, triangleEndY;
    private double[] xCoords, yCoords;
    private double lastEraseX, lastEraseY;
    private double selectStartX, selectStartY, selectEndX, selectEndY, offsetX, offsetY, offsetBX, offsetBY, prevX, prevY;
    private Integer[] selection = new Integer[4]; private Image selectionImage; private Canvas main;
    
    
    //**************************Setter Functions******************************//

    /**
     *
     * @param context context used to draw on
     */
    
    public void setGraphicsContext(GraphicsContext context){
        gc = context;
    }

    /**
     *
     * @param controller layoutcontroller for respective functions
     */
    public void setLayoutController(LayoutController controller){
        lc = controller;
    }
    
    
    //***************************Tool Functions*******************************//

    /**
     *
     * @param mouse gets coordinates
     */
    public void freeDrawStart(MouseEvent mouse){
        gc.beginPath();
        gc.moveTo(mouse.getX(), mouse.getY());
        gc.stroke();
    }

    /**
     *
     * @param mouse gets coordinates
     */
    public void freeDrawDrag(MouseEvent mouse){
        gc.lineTo(mouse.getX(), mouse.getY());
        gc.stroke();
    }
    
    /**
     *
     * @param mouse gets coordinates
     */
    public void freeDrawRelease(MouseEvent mouse){
        gc.closePath();
    }
    
    /**
     *
     * @param mouse gets coordinates
     */
    public void lineStart(MouseEvent mouse){
        line.setStartX(mouse.getX());
        line.setStartY(mouse.getY());
    }
    
    /**
     *
     * @param mouse gets coordinates
     */
    public void lineDrag(MouseEvent mouse){
        if( (line.getEndX() != mouse.getX()) || (line.getEndY() != mouse.getY())){
            lc.canvasReload();
            line.setEndX(mouse.getX());
            line.setEndY(mouse.getY());
            gc.strokeLine(line.getStartX(), line.getStartY(), line.getEndX(), line.getEndY());
        }
    }
    
    /**
     *
     * @param mouse gets coordinates
     */
    public void lineRelease(MouseEvent mouse){
        gc.strokeLine(line.getStartX(), line.getStartY(), line.getEndX(), line.getEndY());
    }
    
    /**
     *
     * @param mouse gets coordinates
     */
    public void ellipseStart(MouseEvent mouse){
        circleStartX = mouse.getX();
        circleStartY = mouse.getY();
        
    }
    
    /**
     *
     * @param mouse gets coordinates
     */
    public void ellipseDrag(MouseEvent mouse){
        double diameterX, diameterY;
        if( (circleEndX != mouse.getX()) || (circleEndY != mouse.getY())){
            lc.canvasReload();
            circleEndX = mouse.getX();
            circleEndY = mouse.getY();

            diameterX = Math.abs( Math.abs(circleEndX) - Math.abs(circleStartX) );
            diameterY = Math.abs( Math.abs(circleEndY) - Math.abs(circleStartY) );

            if(circleEndX < circleStartX && circleEndY < circleStartY){
                gc.fillOval(circleStartX-diameterX, circleStartY-diameterY,
                        diameterX, diameterY);
                gc.strokeOval(circleStartX-diameterX, circleStartY-diameterY,
                        diameterX, diameterY);
            }else if(circleEndY < circleStartY){
                gc.fillOval(circleStartX, circleStartY-diameterY,
                        diameterX, diameterY);
                gc.strokeOval(circleStartX, circleStartY-diameterY,
                        diameterX, diameterY);
            }else if(circleEndX < circleStartX){
                gc.fillOval(circleStartX-diameterX, circleStartY,
                        diameterX, diameterY);
                gc.strokeOval(circleStartX-diameterX, circleStartY,
                        diameterX, diameterY);
            }else{
                gc.fillOval(circleStartX, circleStartY,
                        diameterX, diameterY);
                gc.strokeOval(circleStartX, circleStartY,
                        diameterX, diameterY);
            }
        }
    }
    
    /**
     *
     * @param mouse gets coordinates
     */
    public void ellipseRelease(MouseEvent mouse){
        double diameterX, diameterY;
        diameterX = Math.abs( Math.abs(circleEndX) - Math.abs(circleStartX) );
        diameterY = Math.abs( Math.abs(circleEndY) - Math.abs(circleStartY) );

        if(circleEndX < circleStartX && circleEndY < circleStartY){
            gc.fillOval(circleStartX-diameterX, circleStartY-diameterY,
                    diameterX, diameterY);
            gc.strokeOval(circleStartX-diameterX, circleStartY-diameterY,
                    diameterX, diameterY);
        }else if(circleEndY < circleStartY){
            gc.fillOval(circleStartX, circleStartY-diameterY,
                    diameterX, diameterY);
            gc.strokeOval(circleStartX, circleStartY-diameterY,
                    diameterX, diameterY);
        }else if(circleEndX < circleStartX){
            gc.fillOval(circleStartX-diameterX, circleStartY,
                    diameterX, diameterY);
            gc.strokeOval(circleStartX-diameterX, circleStartY,
                    diameterX, diameterY);
        }else{
            gc.fillOval(circleStartX, circleStartY,
                    diameterX, diameterY);
            gc.strokeOval(circleStartX, circleStartY,
                    diameterX, diameterY);
        }
    }
    
    /**
     *
     * @param mouse gets coordinates
     */
    public void circleDrag(MouseEvent mouse){
        double diameter;
        if( (circleEndX != mouse.getX()) || (circleEndY != mouse.getY())){
            lc.canvasReload();
            circleEndX = mouse.getX();
            circleEndY = mouse.getY();

            diameter = Math.abs( Math.abs(circleEndX) - Math.abs(circleStartX) );

            if(circleEndX < circleStartX && circleEndY < circleStartY){
                gc.fillOval(circleStartX-diameter, circleStartY-diameter,
                        diameter, diameter);
                gc.strokeOval(circleStartX-diameter, circleStartY-diameter,
                        diameter, diameter);
            }else if(circleEndY < circleStartY){
                gc.fillOval(circleStartX, circleStartY-diameter,
                        diameter, diameter);
                gc.strokeOval(circleStartX, circleStartY-diameter,
                        diameter, diameter);
            }else if(circleEndX < circleStartX){
                gc.fillOval(circleStartX-diameter, circleStartY,
                        diameter, diameter);
                gc.strokeOval(circleStartX-diameter, circleStartY,
                        diameter, diameter);
            }else{
                gc.fillOval(circleStartX, circleStartY,
                        diameter, diameter);
                gc.strokeOval(circleStartX, circleStartY,
                        diameter, diameter);
            }

        }
    }
    
    /**
     *
     * @param mouse gets coordinates
     */
    public void circleRelease(MouseEvent mouse){
        double diameter;
        diameter = Math.abs( Math.abs(circleEndX) - Math.abs(circleStartX) );

        if(circleEndX < circleStartX && circleEndY < circleStartY){
            gc.fillOval(circleStartX-diameter, circleStartY-diameter,
                    diameter, diameter);
            gc.strokeOval(circleStartX-diameter, circleStartY-diameter,
                    diameter, diameter);
        }else if(circleEndY < circleStartY){
            gc.fillOval(circleStartX, circleStartY-diameter,
                    diameter, diameter);
            gc.strokeOval(circleStartX, circleStartY-diameter,
                    diameter, diameter);
        }else if(circleEndX < circleStartX){
            gc.fillOval(circleStartX-diameter, circleStartY,
                    diameter, diameter);
            gc.strokeOval(circleStartX-diameter, circleStartY,
                    diameter, diameter);
        }else{
            gc.fillOval(circleStartX, circleStartY,
                    diameter, diameter);
            gc.strokeOval(circleStartX, circleStartY,
                    diameter, diameter);
        }
    }
    
    /**
     *
     * @param mouse gets coordinates
     */
    public void rectangleStart(MouseEvent mouse){
        squareStartX = mouse.getX();
        squareStartY = mouse.getY();
    }
    
    /**
     *
     * @param mouse gets coordinates
     */
    public void rectangleDrag(MouseEvent mouse){
        double widthX, widthY;
        if( (squareEndX != mouse.getX()) || (squareEndY != mouse.getY())){
            lc.canvasReload();
            squareEndX = mouse.getX();
            squareEndY = mouse.getY();

            widthX = Math.abs(squareEndX - squareStartX);
            widthY = Math.abs(squareEndY - squareStartY);

            if(squareEndX < squareStartX && squareEndY < squareStartY){
                gc.fillRect(squareStartX-widthX, squareStartY-widthY,
                        widthX, widthY);
                gc.strokeRect(squareStartX-widthX, squareStartY-widthY,
                        widthX, widthY);
            }else if(squareEndY < squareStartY){
                gc.fillRect(squareStartX, squareStartY-widthY,
                        widthX, widthY);
                gc.strokeRect(squareStartX, squareStartY-widthY,
                        widthX, widthY);
            }else if(squareEndX < squareStartX){
                gc.fillRect(squareStartX-widthX, squareStartY,
                        widthX, widthY);
                gc.strokeRect(squareStartX-widthX, squareStartY,
                        widthX, widthY);
            }else{
                gc.fillRect(squareStartX, squareStartY,
                        widthX, widthY);
                gc.strokeRect(squareStartX, squareStartY,
                        widthX, widthY);
            }
        }
    }
    
    /**
     *
     * @param mouse gets coordinates
     */
    public void rectangleRelease(MouseEvent mouse){
        double widthX, widthY;
        widthX = Math.abs(squareEndX - squareStartX);
        widthY = Math.abs(squareEndY - squareStartY);

        if(squareEndX < squareStartX && squareEndY < squareStartY){
            gc.fillRect(squareStartX-widthX, squareStartY-widthY,
                    widthX, widthY);
            gc.strokeRect(squareStartX-widthX, squareStartY-widthY,
                    widthX, widthY);
        }else if(squareEndY < squareStartY){
            gc.fillRect(squareStartX, squareStartY-widthY,
                    widthX, widthY);
            gc.strokeRect(squareStartX, squareStartY-widthY,
                    widthX, widthY);
        }else if(squareEndX < squareStartX){
            gc.fillRect(squareStartX-widthX, squareStartY,
                    widthX, widthY);
            gc.strokeRect(squareStartX-widthX, squareStartY,
                    widthX, widthY);
        }else{
            gc.fillRect(squareStartX, squareStartY,
                    widthX, widthY);
            gc.strokeRect(squareStartX, squareStartY,
                    widthX, widthY);
        }
    }
    
    /**
     *
     * @param mouse gets coordinates
     */
    public void squareDrag(MouseEvent mouse){
        double width;
        if( (squareEndX != mouse.getX()) || (squareEndY != mouse.getY())){
            lc.canvasReload();
            squareEndX = mouse.getX();
            squareEndY = mouse.getY();

            width = Math.abs(squareEndX - squareStartX);

            if(squareEndX < squareStartX && squareEndY < squareStartY){
                gc.fillRect(squareStartX-width, squareStartY-width,
                        width, width);
                gc.strokeRect(squareStartX-width, squareStartY-width,
                        width, width);
            }else if(squareEndY < squareStartY){
                gc.fillRect(squareStartX, squareStartY-width,
                        width, width);
                gc.strokeRect(squareStartX, squareStartY-width,
                        width, width);
            }else if(squareEndX < squareStartX){
                gc.fillRect(squareStartX-width, squareStartY,
                        width, width);
                gc.strokeRect(squareStartX-width, squareStartY,
                        width, width);
            }else{
                gc.fillRect(squareStartX, squareStartY,
                        width, width);
                gc.strokeRect(squareStartX, squareStartY,
                        width, width);
            }
        }
    }
    
    /**
     *
     * @param mouse gets coordinates
     */
    public void squareRelease(MouseEvent mouse){
        double width;
        width = Math.abs(squareEndX - squareStartX);

        if(squareEndX < squareStartX && squareEndY < squareStartY){
            gc.fillRect(squareStartX-width, squareStartY-width,
                    width, width);
            gc.strokeRect(squareStartX-width, squareStartY-width,
                    width, width);
        }else if(squareEndY < squareStartY){
            gc.fillRect(squareStartX, squareStartY-width,
                    width, width);
            gc.strokeRect(squareStartX, squareStartY-width,
                    width, width);
        }else if(squareEndX < squareStartX){
            gc.fillRect(squareStartX-width, squareStartY,
                    width, width);
            gc.strokeRect(squareStartX-width, squareStartY,
                    width, width);
        }else{
            gc.fillRect(squareStartX, squareStartY,
                    width, width);
            gc.strokeRect(squareStartX, squareStartY,
                    width, width);
        }
    }
    
    /**
     *
     * @param mouse gets coordinates
     */
    public void triangleStart (MouseEvent mouse){
        triangleStartX = mouse.getX();
        triangleStartY = mouse.getY();
    }
    
    /**
     *
     * @param mouse gets coordinates
     */
    public void triangleDrag(MouseEvent mouse){
        double width;
        
        if( (triangleEndX != mouse.getX()) || (triangleEndY != mouse.getY())){
            lc.canvasReload();
            triangleEndX = mouse.getX();
            triangleEndY = mouse.getY();

            width = Math.abs(triangleEndX - triangleStartX);
            
            //Quadrants 1, 2, 3, 4
            if(triangleEndX < triangleStartX && triangleEndY < triangleStartY){
                xCoords = new double[]{triangleStartX - width/2, triangleEndX, triangleStartX};
                yCoords = new double[]{triangleEndY, triangleStartY, triangleStartY};
            }else if(triangleEndY < triangleStartY){
                xCoords = new double[]{triangleEndX - width/2, triangleStartX, triangleEndX};
                yCoords = new double[]{triangleEndY, triangleStartY, triangleStartY};
            }else if(triangleEndX < triangleStartX){
                xCoords = new double[]{triangleStartX - width/2, triangleEndX, triangleStartX};
                yCoords = new double[]{triangleEndY, triangleStartY, triangleStartY};
            }else{
                xCoords = new double[]{triangleEndX - width/2, triangleStartX, triangleEndX};
                yCoords = new double[]{triangleEndY, triangleStartY, triangleStartY};
            }
            gc.fillPolygon(xCoords, yCoords, 3);
            gc.strokePolygon(xCoords, yCoords, 3);
        }
    }
    
    /**
     *
     * @param mouse gets coordinates
     */
    public void triangleRelease(MouseEvent mouse){
        gc.fillPolygon(xCoords, yCoords, 3);
        gc.strokePolygon(xCoords, yCoords, 3);
    }
    
    /**
     *
     * @param mouse gets coordinates
     * @return the new X coordinate
     */
    public Double polygonAddX(MouseEvent mouse){
        return mouse.getX();
    }
    
    /**
     *
     * @param mouse gets coordinates
     * @return the new Y coordinate
     */
    public Double polygonAddY(MouseEvent mouse){
        return mouse.getY();
    }
    
    /**
     *
     * @param mouse  gets coordinates
     * @param x all previously recorded x coordinates for current polygon
     * @param y all previously recorded y coordiantes for current polygon
     */
    public void polygonHover(MouseEvent mouse, Double[] x, Double[] y){
        if (x.length != 0){
            if (mouse.getX() != x[x.length-1] || mouse.getY() != y[y.length-1]){
                lc.canvasReload();
                if (x.length == 1){
                    gc.strokeLine(x[0], y[0], mouse.getX(), mouse.getY());
                }else{
                    for(int coord = 1; coord < x.length; coord++){
                        gc.strokeLine(x[coord-1], y[coord-1], x[coord], y[coord]);
                    }
                    gc.strokeLine(x[x.length-1], y[y.length-1], mouse.getX(), mouse.getY());
                }
            }
        }
    }
    
    /**
     *
     * @param mouse  gets coordinates
     * @param x all x coordinates of polygon
     * @param y all y coordinates of polygon
     */
    public void polygonRelease(MouseEvent mouse, Double[] x, Double[] y){
        lc.undoStackAdd(gc.getCanvas());
        
        for(int coord = 1; coord < x.length; coord++){
            gc.strokeLine(x[coord-1], y[coord-1], x[coord], y[coord]);
        }
        gc.strokeLine(x[x.length-1], y[y.length-1], x[0], y[0]);
        
    }
    
    /**
     *
     * @param mouse gets coordinates
     * @param text String to be stamped onto canvas
     * @param fontSize size of the stamped text
     */
    public void textStamp(MouseEvent mouse, String text, double fontSize){
        gc.setFont(new Font(text, fontSize));
        gc.fillText(text, mouse.getX(), mouse.getY());
    }
    
    /**
     *
     * @param mouse gets coordinates
     * @param size eraser size
     */
    public void eraserStart(MouseEvent mouse, double size){
        gc.clearRect(mouse.getX()-size, mouse.getY() - size, size*2, size*2);
        lastEraseX = mouse.getX();
        lastEraseY = mouse.getY();
    }

    /**
     *
     * @param mouse gets coordinates
     * @param size eraser size
     */
    public void eraserDrag(MouseEvent mouse, double size){
        if (mouse.getX() != lastEraseX || mouse.getY() != lastEraseY){
            lastEraseX = mouse.getX();
            lastEraseY = mouse.getY();
            gc.clearRect(lastEraseX - size, lastEraseY - size, size*2, size*2);
        }
    }
    
    /**************************Selection Function*****************************/
     
    /**
     * 
     * @param mouse gets coordinates
     */
    public void selectStart(MouseEvent mouse){
        selectStartX = mouse.getX();
        selectStartY = mouse.getY();
        selectEndX = mouse.getX();
        selectEndY = mouse.getY();
        selection[0] = (int)selectStartX;
        selection[1] = (int)selectStartY;
    }
    
    /**
     *
     * @param mouse gets coordinates
     */
    public void selectDrag(MouseEvent mouse){
        double widthX, widthY;
        if( (selectEndX != mouse.getX()) || (selectEndY != mouse.getY())){
            if (!selectOn){
                selectOn = true;
            }
            lc.canvasReload();
            selectEndX = mouse.getX();
            selectEndY = mouse.getY();
            widthX = Math.abs(selectEndX - selectStartX);
            widthY = Math.abs(selectEndY - selectStartY);
            
            
            if(selectEndX < selectStartX && selectEndY < selectStartY){
                gc.strokeRect(selectStartX-widthX, selectStartY-widthY, widthX, widthY);
            }else if(selectEndY < selectStartY){
                gc.strokeRect(selectStartX, selectStartY-widthY, widthX, widthY);
            }else if(selectEndX < selectStartX){
                gc.strokeRect(selectStartX-widthX, selectStartY, widthX, widthY);
            }else{
                gc.strokeRect(selectStartX, selectStartY, widthX, widthY);
            }
        }
    }
    
    /**
     *
     * @param mouse  gets coordinates
     */
    public void selectRelease(MouseEvent mouse){
        if( (selectStartX == selectEndX) && (selectStartY == selectEndY)){
            System.out.println(selectStartX + " = " + mouse.getX() +"\t" + selectStartY + " = " + mouse.getY());
            lc.canvasReload();
            selectStartX = 0;
            selectStartY = 0;
            selectEndX = 0;
            selectEndY = 0;
            selectOn = false;
        }else{
            selectEndX = mouse.getX();
            selectEndY = mouse.getY();
        }
        selection[0] = (int)selectStartX;
        selection[1] = (int)selectStartY;
        selection[2] = (int)selectEndX;
        selection[3] = (int)selectEndY;
        
        double widthX = Math.abs(selectEndX - selectStartX);
        double widthY = Math.abs(selectEndY - selectStartY);
        if(selectEndX < selectStartX && selectEndY < selectStartY){
            gc.strokeRect(selectStartX-widthX, selectStartY-widthY, widthX, widthY);
        }else if(selectEndY < selectStartY){
            gc.strokeRect(selectStartX, selectStartY-widthY, widthX, widthY);
        }else if(selectEndX < selectStartX){
            gc.strokeRect(selectStartX-widthX, selectStartY, widthX, widthY);
        }else{
            gc.strokeRect(selectStartX, selectStartY, widthX, widthY);
        }
    }
    
    /**
     *
     * @param mouse  gets coordinates
     * @param mainCanvas canvas to make image from
     */
    public void moveSelectStart(MouseEvent mouse, Canvas mainCanvas){
        if (selectOn){
            offsetX = Math.abs(mouse.getX() - selection[0]);
            offsetY = Math.abs(mouse.getY() - selection[1]);
            offsetBX = Math.abs(mouse.getX() - selection[2]);
            offsetBY = Math.abs(mouse.getY() - selection[3]);
            
            prevX = mouse.getX();
            prevY = mouse.getY();
            main = mainCanvas;
            if(!selectionMoved){
                selectionImage = makeSelectionImage();
                lc.getMainGraphics2D().clearRect(selection[0], selection[1], 
                        Math.abs(selection[0] - selection[2]), Math.abs(selection[1] - selection[3]));
                selectionMoved = true;
            }
            if(!filterMade){
                filterMade = true;
                mainCanvas.getScene().addEventFilter(KeyEvent.KEY_RELEASED, new EventHandler<KeyEvent>() {
                    @Override
                    public void handle(KeyEvent key) {
                        if(key.getCode().equals(key.getCode().ENTER)){
                            lc.canvasReload();
                            lc.getMainGraphics2D().drawImage(selectionImage, selectStartX, selectStartY, Math.abs(selectStartX-selectEndX), Math.abs(selectStartY - selectEndY),
                                    prevX-offsetX, prevY-offsetY, Math.abs(selectStartX-selectEndX), Math.abs(selectStartY - selectEndY));
                            
                            selectOn = false;
                            selectionMoved = false;
                            mainCanvas.getScene().removeEventFilter(KeyEvent.ANY, this);
                            filterMade = false;
                            
                        }
                    }
                });
            }
            
        }
    }
    
    /**
     *
     * @param mouse gets coordinates
     */
    public void moveSelectDrag(MouseEvent mouse){
        if (selectOn){
            if( (prevX != mouse.getX()) && (prevY != mouse.getY())){
                prevX = mouse.getX();
                prevY = mouse.getY();
                lc.canvasReload();
                
                gc.drawImage(selectionImage, selectStartX, selectStartY, Math.abs(selectStartX-selectEndX), Math.abs(selectStartY - selectEndY),
                            prevX-offsetX, prevY-offsetY, Math.abs(selectStartX-selectEndX), Math.abs(selectStartY - selectEndY));
                gc.strokeRect(prevX-offsetX, prevY-offsetY,
                    Math.abs(selectStartX-selectEndX), Math.abs(selectStartY - selectEndY));
            }
        }
    }
    
    /**
     *
     * @param mouse gets coordinates
     */
    public void moveSelectRelease(MouseEvent mouse){
        if(selectOn){
            if(selectionMoved){
                int topLeftX, topLeftY, bottomRightX, bottomRightY;
                offsetBX = Math.abs(selection[2] - offsetX);
                offsetBY = Math.abs(selection[3] - offsetY);
                topLeftX = (int)(Math.abs(prevX-offsetX));
                topLeftY = (int)(Math.abs(prevY-offsetY));
                bottomRightX = (int)(Math.abs(prevX-offsetBX));
                bottomRightY = (int)(Math.abs(prevX-offsetBY));

                if (topLeftX != selection[0]){
                    selection[0] = topLeftX;
                }
                if (topLeftY != selection[1]){
                    selection[1] = topLeftY;
                }
                if (bottomRightX != selection[2]){
                    selection[2] = bottomRightX;
                }
                if (bottomRightY != selection[3]){
                    selection[3] = bottomRightY;
                }
                
            }
        }
    }
    
    private Image makeSelectionImage(){
        Image snapshot = main.snapshot(null,null);
        return snapshot;
    }
}
