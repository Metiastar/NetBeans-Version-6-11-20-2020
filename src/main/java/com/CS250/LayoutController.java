/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.CS250;


import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.Stack;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.NodeOrientation;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import static javafx.scene.control.Alert.AlertType.*;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Slider;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.input.DragEvent;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import static javafx.scene.paint.Color.*;
import javafx.scene.shape.Circle;
import javafx.scene.shape.StrokeLineCap;
import javafx.scene.shape.StrokeLineJoin;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.FileChooser;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.util.converter.IntegerStringConverter;
import javax.imageio.ImageIO;

/**
 * FXML Controller class
 *
 * @author Blazi
 */
public class LayoutController implements Initializable {
   
    private Timer Logging = new Timer(true);
    private myCanvas[] myCanvi = new myCanvas[0];

    /**
     *
     */
    public final ToggleGroup toolsDrawing = new ToggleGroup();

    /**
     *
     */
    public final ToggleGroup toolsBrushes = new ToggleGroup();
    private ActionEvent trigger = null;

    /**
     *
     */
    @FXML
    public Canvas canvasTable;
    @FXML
    private Canvas tempCanvas = new Canvas();
    
    /**
     *
     */
    @FXML
    public MenuItem Save;

    /**
     *
     */
    @FXML
    public MenuItem Redo;

    /**
     *
     */
    @FXML
    public MenuItem Undo;
    @FXML
    private TextField lineWidth;
    @FXML
    private Slider sliderLineWidth;
    @FXML
    private TextField lineOpacity;
    @FXML
    private Slider sliderOpacity;
    @FXML
    private ToggleButton brushPen;
    @FXML
    private ToggleButton brushEraser;

    /**
     *
     */
    @FXML
    public ColorPicker colorPicker;
    @FXML
    private TextField colorHex;
    @FXML
    private Circle colorCircle;
    @FXML
    private Circle lineCircle;
    @FXML
    private ToggleButton toolText;
    @FXML
    private ToggleButton toolFreeDraw;
    @FXML
    private ToggleButton toolLine;
    @FXML
    private ToggleButton toolPolygon;
    @FXML
    private ToggleButton toolSquare;
    @FXML
    private ToggleButton toolCircle;
    @FXML
    private ToggleButton toolOval;
    @FXML
    private ToggleButton toolRectangle;
    @FXML
    private ToggleButton toolDropper;
    @FXML
    private ToggleButton toolSelectRect;
    @FXML
    private ToggleButton toolSelectMove;

    /**
     *
     */
    @FXML
    public Label lineText;

    /**
     *
     */
    @FXML
    public TextArea lineTextInput;
    @FXML
    private HBox lineTextOptions;

    /**
     *
     */
    @FXML
    public TabPane parentTabPane;
    @FXML
    private Tab initialTab;
    @FXML
    private CheckMenuItem checkAutosave;
    @FXML
    private BorderPane parentBorderPane;
    @FXML
    private CheckMenuItem checkAutosaveView;
    @FXML
    private ToggleButton toolTriangle;
    @FXML
    private MenuItem newFile;
    
    
    
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        GraphicsContext c = canvasTable.getGraphicsContext2D();
        c.setFill(TRANSPARENT);                                              
        c.fillRect(0,0,canvasTable.getWidth(),canvasTable.getHeight());
        c.setFill(BLACK);
        c.setStroke(BLACK);
        c.setLineWidth(2);
        c.setLineCap(StrokeLineCap.ROUND);
        colorPicker.setValue(BLACK);
        
        
        GraphicsContext tC = tempCanvas.getGraphicsContext2D();
        tC.setFill(TRANSPARENT);
        tC.fillRect(0,0,canvasTable.getWidth(),canvasTable.getHeight());
        tC.setFill(BLACK);
        tC.setStroke(BLACK);
        tC.setLineWidth(2);
        tC.setLineCap(StrokeLineCap.ROUND);
        
        lineWidth.setTextFormatter(new TextFormatter<>(new IntegerStringConverter())); 
        lineOpacity.setTextFormatter(new TextFormatter<>(new IntegerStringConverter()));
        lineWidth.setText("2");
        lineOpacity.setText("100");
        lineCircle.setRadius(1);
        lineTextInput.setText("");
        
        /*Set drawing tool toggle group*/
        toolText.setToggleGroup(toolsDrawing);
        toolFreeDraw.setToggleGroup(toolsDrawing);
        toolLine.setToggleGroup(toolsDrawing);
        toolSquare.setToggleGroup(toolsDrawing);
        toolRectangle.setToggleGroup(toolsDrawing);
        toolCircle.setToggleGroup(toolsDrawing);
        toolOval.setToggleGroup(toolsDrawing);
        toolTriangle.setToggleGroup(toolsDrawing);
        toolPolygon.setToggleGroup(toolsDrawing);
        toolDropper.setToggleGroup(toolsDrawing);
        toolSelectRect.setToggleGroup(toolsDrawing);
        toolSelectMove.setToggleGroup(toolsDrawing);
        toolFreeDraw.setSelected(true);
        setToolstring();
        
        /*Set brush tool toggle group*/
        brushPen.setToggleGroup(toolsBrushes);
        brushEraser.setToggleGroup(toolsBrushes);
        brushPen.setSelected(true);
        
        /*set tooltips*/
        toolText.setTooltip(new Tooltip("Stamps Text onto the Screen using text inputted under the text size"));
        toolPolygon.setTooltip(new Tooltip("Creates a polygon, via clicking on the screen: Press 'Shift + Enter' to close Polygon"));
        toolSelectMove.setTooltip(new Tooltip("Moves an already selected area"));
        toolDropper.setTooltip(new Tooltip("Click on the canvas to pick up a color"));
        
        myCanvas OG = new myCanvas(canvasTable, tempCanvas, stackDimensions, stackDimensionsRedo, stackImage, stackImageRedo, this);
        remakeCanvasArray(OG);
        
        Logging.scheduleAtFixedRate(new myLoggingTask(this), 0, 1000);
        initialTab.setOnCloseRequest(this.setCanvasTabClose());
        
        undoStackAdd(canvasTable);
    }    
    
    /**
     *
     * @return current Main Canvas
     */
    public Canvas getCanvas(){
        ScrollPane sc = (ScrollPane) parentTabPane.getSelectionModel().getSelectedItem().getContent();
        Canvas CS;
        Pane P = (Pane) sc.getContent();
        CS = (Canvas) P.getChildren().get(0);
        return CS;
    }

    /**
     *
     * @return current Temporary Canvas
     */
    public Canvas getTempCanvas(){
        ScrollPane sc = (ScrollPane) parentTabPane.getSelectionModel().getSelectedItem().getContent();
        Canvas CS;
        Pane P = (Pane) sc.getContent();
        CS = (Canvas) P.getChildren().get(1);
        return CS;
    }

    /**
     *
     * @return Main Canvas GraphicsContext
     */
    public GraphicsContext getMainGraphics2D(){
        Canvas CS = getCanvas();
        GraphicsContext GC = CS.getGraphicsContext2D();
        return GC;
    }

    /**
     *
     * @return temporary Canvas GraphicsCantext
     */
    public GraphicsContext getTempGraphics2D(){
        Canvas CS = getTempCanvas();
        GraphicsContext GC = CS.getGraphicsContext2D();
        return GC;
    }
    
    /*################## Window Functions ##################*/
    @FXML
    private void windowAbout(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(Main.class.getResource("About.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
       
        Stage About = new Stage();
        
        About.setTitle("About NBPaint");
        About.setScene(scene);
        About.show();
    }
    
    @FXML
    private void windowResize(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(Main.class.getResource("CanvasResize.fxml"));
        Parent root = loader.load();
        Popup resize = new Popup();
        
        
        resize.getContent().addAll(root);
        
        CanvasResizeController CR = loader.getController();
        
        int[] values = new int[2];
        Canvas canvas = getCanvas();
        values[0] = (int) canvas.getWidth();
        values[1] = (int) canvas.getHeight();
        CR.setInitialValues(values);
        CR.setPopup(resize);
        CR.setLayoutController(this);
        
        
        resize.show(canvasTable.getScene().getWindow());
    }
    
    
    /*################## File Functions ##################*/
    private FileChooser fc = new FileChooser();
    private FileChooser current_file = new FileChooser();
    private myAutosave[] autosaves = new myAutosave[0];
    private Timer[] timersAutosave = new Timer[0];
    
    /**
     *
     * @param filter extension originally used
     * @return Alertbox used for extension changes in file saves
     */
    public Alert handleFileExtensionAlert(String filter){
        Alert alertbox = new Alert(CONFIRMATION);
        alertbox.setHeaderText("Warning! There may be lossy conversion from " +
                                fc.getInitialFileName().substring(fc.getInitialFileName().length()-3) +
                                " to " + filter + ".");
        return alertbox;
    }
    
    @FXML
    private void handleImport(ActionEvent event) {       
        fc.setTitle("Import Image");                                            
        fc.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Image File (*.jpg) (*.png) (*.bmp)", "*.jpg","*.png","*.bmp"));
        File selected = fc.showOpenDialog(null);
        
        if(selected != null){
            try{
                Image image = new Image(selected.toURI().toURL().toString());  
                Canvas canvas = getCanvas();
                
                if(image.getWidth() > canvas.getWidth()){                  //This is just to resize the window incase the image file is too big, might change later on once a possible resize tool is added
                    canvas.setWidth(image.getWidth());    
                }
                if(image.getHeight() > canvas.getHeight()){
                    canvas.setHeight(image.getHeight());
                }

                canvas.getGraphicsContext2D().drawImage(image, 0, 0, image.getWidth(), image.getHeight());
                undoStackAdd(canvas);
            }catch(Exception e){
                System.out.println(e);
            }
            
            fc.getExtensionFilters().removeAll(fc.getExtensionFilters());
        }
        
    }

    @FXML
    private void handleSaveAs(ActionEvent event) {
        fc.setTitle("Save Image As");
        if (current_file.getInitialFileName() != null){
            fc.setInitialFileName(current_file.getInitialFileName());
        }
        
        fc.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("(*.png)", "*.png"),
                new FileChooser.ExtensionFilter("(*.jpg)", "*.jpg"),
                new FileChooser.ExtensionFilter("(*.bmp)", "*.bmp"));                               
        File outputSA = fc.showSaveDialog(canvasTable.getScene().getWindow());
        
        if (outputSA != null){
            try {
                String filter = fc.getSelectedExtensionFilter().getExtensions().get(0);                                     //puts correct extension on image file
                filter = filter.substring(2);
                
                Canvas canvas = getCanvas();
                WritableImage writableImage = new WritableImage((int)canvas.getWidth(), (int)canvas.getHeight());
                if(filter.contains("png")){
                    SnapshotParameters sp = new SnapshotParameters();
                    sp.setFill(TRANSPARENT);
                    canvas.snapshot(sp, writableImage);
                }else{
                    canvas.snapshot(null, writableImage);
                }
                                                                                  
                RenderedImage ri = SwingFXUtils.fromFXImage(writableImage, null);     
                
                
                
                if (fc.getInitialFileName() != null && !fc.getInitialFileName().contains(filter)){
                    Alert alert = handleFileExtensionAlert(filter);
                    Optional<ButtonType> result = alert.showAndWait();
                    if (result.isPresent() && result.get() == ButtonType.OK) {
                        fc.setInitialFileName(outputSA.getName() + filter);
                        ImageIO.write(ri, "png", outputSA);                         
                    }
                    
                }else if (fc.getInitialFileName() != null && fc.getInitialFileName().contains(filter)){
                    ImageIO.write(ri, "png", outputSA); 
                }else{
                    fc.setInitialFileName(outputSA.getName() + filter);
                    ImageIO.write(ri, "png", outputSA); 
                }
                
                current_file.setInitialDirectory(outputSA);
                current_file.setInitialFileName(outputSA.getName());
                parentTabPane.getSelectionModel().getSelectedItem().setText(
                        current_file.getInitialFileName());
                checkAutosaves();
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
        }
        fc.getExtensionFilters().removeAll(fc.getExtensionFilters());
    }

    /**
     *
     * @param event hotkey Ctrl+S or press Save menu item
     */
    @FXML
    public void handleSave(ActionEvent event) {
        if(current_file.getInitialDirectory() != null){
            File outputS = current_file.getInitialDirectory().getAbsoluteFile();
            try{
                Canvas canvas = getCanvas();
                WritableImage writableImage = new WritableImage((int)canvas.getWidth(), (int)canvas.getHeight()); 
                canvas.snapshot(null, writableImage);                                                                  
                RenderedImage ri = SwingFXUtils.fromFXImage(writableImage, null);                                           

                ImageIO.write(ri, "png", outputS);                              
                parentTabPane.getSelectionModel().getSelectedItem().setText(
                        current_file.getInitialFileName());
                checkAutosaves();
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
            
        } else {
            handleSaveAs(trigger);
        }
    }
    
    private void checkAutosaves(){
        if (checkAutosave.isSelected()){
            boolean hasAuto = false;
            for (int i = 0; i < autosaves.length; i++){
                if(autosaves[i].getCanvi() == getCanvas()){
                    hasAuto = true;
                    replaceAutosave(i);
                }
            }
            if (hasAuto == false){
                Timer nTimer = new Timer(true);
                myAutosave nCanvi = new myAutosave(getCanvas(), current_file.getInitialDirectory());
                
                nTimer.scheduleAtFixedRate(nCanvi, 0, 1000*60*5);
                addAutosave(nCanvi, nTimer);
                
            }
        }
    }
    
    private void addAutosave(myAutosave nCanvi, Timer nTimer){
        myAutosave[] saveTemp = new myAutosave[autosaves.length+1];
        Timer[] timerTemp = new Timer[timersAutosave.length + 1];

        
        System.arraycopy(autosaves, 0, saveTemp, 0, autosaves.length);
        autosaves = new myAutosave[saveTemp.length];
        System.arraycopy(saveTemp, 0, autosaves, 0, saveTemp.length);
        autosaves[autosaves.length-1] = nCanvi;

        System.arraycopy(timersAutosave, 0, timerTemp, 0, timersAutosave.length);
        timersAutosave = new Timer[timerTemp.length];
        System.arraycopy(timerTemp, 0, timersAutosave, 0, timerTemp.length);
        timersAutosave[timersAutosave.length-1] = nTimer;
        
    }
    
    /**
     * Remakes the autosave array to reset the autosave timer for the canvas being saved over
     * @param index the autosave being replaced in the autosave array
     */
    private void replaceAutosave(int index){
        Timer timer = new Timer(true);
        myAutosave canvi = new myAutosave(autosaves[index].getCanvi(), current_file.getInitialDirectory());
        
        timersAutosave[index].cancel();
        
        timer.scheduleAtFixedRate(canvi, 0, 1000*60*5);
        timersAutosave[index] = timer;
        autosaves[index] = canvi;
    }
    
    /**
     * makes a new canvas to draw on in the program
     * @param event clicking on the "new file" option under the File menu,
     *              or pressing the shortcut "CTRL + N"
     */
    @FXML
    private void newTabCanvas(ActionEvent event) {
        Tab nTab = new Tab();
        myCanvas nCanvas = new myCanvas(this);
        ScrollPane nScroll = new ScrollPane();
        Pane nPane = new Pane();
        
        nPane.getChildren().add(0, nCanvas.getCanvas());
        nPane.getChildren().add(1, nCanvas.getTempCanvas());
        nScroll.setContent(nPane);
        nTab.setContent(nScroll);
        nTab.setText("Untitled Image");
        
        Canvas tabC = nCanvas.getCanvas();
        Canvas tabTC = nCanvas.getTempCanvas();
        GraphicsContext c = tabC.getGraphicsContext2D();
        GraphicsContext tC = tabTC.getGraphicsContext2D();
        c.setFill(TRANSPARENT);
        c.fillRect(0, 0, tabC.getWidth(), tabC.getHeight());
        c.setLineWidth(2.0);
        tC.setFill(TRANSPARENT);
        tC.fillRect(0, 0, tabTC.getWidth(), tabTC.getHeight());
        tC.setLineWidth(2.0);
        
        
        remakeCanvasArray(nCanvas);
        parentTabPane.getTabs().add(nTab);
        colorChooser(trigger);
    }
    
    /**
     * remakes the myCanvas array to include the newly made canvas tab
     * @param nCanvas canvas being added into the array
     */
    public void remakeCanvasArray(myCanvas nCanvas){
        myCanvas[] temp = new myCanvas[myCanvi.length+1];
        System.arraycopy(myCanvi, 0, temp, 0, myCanvi.length);
        myCanvi = new myCanvas[temp.length];
        System.arraycopy(temp, 0, myCanvi, 0, temp.length);
        myCanvi[myCanvi.length-1] = nCanvas;
    }
    
    /**
     *
     * @return EventHandler if user wants to close a tab
     */
    public EventHandler<Event> setCanvasTabClose() {
        EventHandler<Event> handler = new EventHandler<Event>(){
            @Override
            public void handle(Event t) {
                Alert unsavedChanges = new Alert(AlertType.CONFIRMATION);
                unsavedChanges.setHeaderText("There are unsaved changes. Would you like to save them?");
                Optional<ButtonType> result = unsavedChanges.showAndWait();
                if (result.isPresent() && result.get() == ButtonType.OK) {
                    LayoutController.this.handleSave(trigger);
                }else{
                    t.consume();
                }            
            }
        };
        return handler;
    }
    /*################## Line Style Functions ##################*/
    
    /**
     * changes the width of the line used while drawing
     * @param event When a new number is entered into the respective textfield.
     *              Or when the respective slider is updated.
     */
    @FXML
    private void changeLineWidth(ActionEvent event) {
        int value = Integer.parseInt(lineWidth.getText());
        
        for(myCanvas canvi: myCanvi){
            GraphicsContext c = canvi.getMainGraphics2D();
            GraphicsContext tC = canvi.getTempGraphics2D();
            c.setLineWidth(value);
            tC.setLineWidth(value);
        }
        
        
        lineCircle.setRadius(value/2);
        lineText.setFont(new Font("Text", value));
        lineText.setTranslateX(-value/2);
        lineText.setTranslateY(-value/2);
        if(value != sliderLineWidth.getValue()){
            sliderLineWidth.adjustValue(value);
        }
        
    }
    @FXML
    private void changeLineWidthSlider(MouseEvent event) {
        String value = Integer.toString((int)sliderLineWidth.getValue());
        lineWidth.setText(value);
        changeLineWidth(trigger);
    }
    
    /**
     * Changes the color of the line and fill used for the regular drawing tools
     * for all opened canvases
     * @param event 
     */
    @FXML
    public void colorChooser(ActionEvent event){
        int opacity = Integer.parseInt(lineOpacity.getText());
        Color color = colorPicker.getValue();
        if (colorPicker.getValue().getOpacity() != opacity*0.01){
            color = new Color(color.getRed(),color.getGreen(),color.getBlue(), opacity*0.01);
        }
        
        for(myCanvas canvi: myCanvi){
            GraphicsContext c = canvi.getMainGraphics2D();
            c.setFill(color);
            c.setStroke(color);
            
            GraphicsContext tC = canvi.getTempGraphics2D();
            tC.setFill(color);
            tC.setStroke(color);
        }
        
        colorHex.setText("#"+ colorPicker.getValue().toString().substring(2, 8).toUpperCase());
        colorCircle.setFill(colorPicker.getValue());
        lineCircle.setFill(color);
        lineText.setTextFill(color);
        
        
    }
    @FXML
    private void changeColorHex(ActionEvent event) {
        
        String shortenColorCode = colorHex.getText().substring(1).toLowerCase();
        String shortencolorChooser = colorPicker.getValue().toString().substring(2,8);
        
        if( !shortencolorChooser.equals(shortenColorCode) ){
            Color h = Color.web(colorHex.getText());
            colorPicker.setValue(h);
            colorChooser(trigger);
        }
        
    }
    
    /**
     * Picks up the color on the main graphics context and
     * calls the color chooser function to change the color to the picked up color
     * @param mouse when the user uses the color dropper tool and clicks on the canvas
     */
    public void colorDrop(MouseEvent mouse){
        Canvas takeColor = getCanvas();
        WritableImage curCanvas = takeColor.snapshot(null, null);
        PixelReader pixels = curCanvas.getPixelReader();
        Color color = pixels.getColor((int)mouse.getX(), (int)mouse.getY());
        colorPicker.setValue(color);
        colorChooser(trigger);
    }
    
    /**
     * Changes the opacity of the drawing tool being used. Does not apply to Eraser brush.
     * @param event When the text field or slider are changed.
     */
    @FXML
    private void changeLineOpacity(ActionEvent event) {
        int value = Integer.parseInt(lineOpacity.getText());
        
        for(myCanvas canvi: myCanvi){
            GraphicsContext c = canvi.getMainGraphics2D();
            GraphicsContext tC = canvi.getTempGraphics2D();
            c.setGlobalAlpha(value*0.01);
            tC.setGlobalAlpha(value*0.01);
        }
        Color color = colorPicker.getValue();
        color = new Color(color.getRed(),color.getGreen(),color.getBlue(), value*0.01);
        colorPicker.setValue(color);
        lineCircle.setFill(color);
        if(value != sliderOpacity.getValue()){
            sliderOpacity.adjustValue(value);
        }
    }
    @FXML
    private void changeOpacitySlider(MouseEvent event) {
        String value = Integer.toString((int)sliderOpacity.getValue());
        lineOpacity.setText(value);
        changeLineOpacity(trigger);
    }
    
    /**
     * switches the brush cap presets according to the brush selected
     * @param event When a different brush is selected
     */
    @FXML
    public void switchBrush(ActionEvent event) {
        
        String brush = toolsBrushes.getSelectedToggle().toString();
        
        if(brush.contains("Pen")){
            for(myCanvas canvi: myCanvi){
                GraphicsContext mC = canvi.getMainGraphics2D();
                GraphicsContext tC = canvi.getTempGraphics2D();
                mC.setLineCap(StrokeLineCap.ROUND);
                mC.setLineJoin(StrokeLineJoin.ROUND);
                tC.setLineCap(StrokeLineCap.ROUND);
                tC.setLineJoin(StrokeLineJoin.ROUND);
            }
            toolsDrawing.selectToggle(toolFreeDraw);
            setToolstring();
            colorChooser(trigger);
        }
        /*Colors using background color; Once transparency is figured out, eraser will be changed to make canvas transparent*/
        if(brush.contains("Eraser")){
            toolsDrawing.selectToggle(null);
            toolstring = "Eraser";
        }
        
    }

    /**
     * Changes graphics context color and width to 
     * indicate the use of functions that require unique appearances.
     * 
     */
    @FXML
    private void specToolSwitch(ActionEvent event) {
        if ( toolSelectRect.isSelected() || toolSelectMove.isSelected()){
            
            for(myCanvas canvi: myCanvi){
                GraphicsContext c = canvi.getTempGraphics2D();
                c.setFill(GREY);
                c.setStroke(GREY);
                c.setLineDashes(15.0);
                c.setLineWidth(1.0);
                c.setGlobalAlpha(1);
            }
        }
        if ( toolText.isSelected()){
            lineCircle.setVisible(false);
            lineText.setVisible(true);
            lineTextOptions.setVisible(true);
        }
        if ( !toolsBrushes.getSelectedToggle().toString().contains("Eraser")){
            setToolstring();
        }
        
    }

    @FXML
    private void drawToolSwitch(ActionEvent event) {
        canvasReload();
        
        if (lineCircle.visibleProperty().get() == false){
            lineCircle.setVisible(true);
            lineText.setVisible(false);
            lineTextOptions.setVisible(false);
        }
        if ( brushEraser.isSelected()){
           toolsBrushes.selectToggle(brushPen);
        }
        setToolstring();
        for(myCanvas canvi: myCanvi){
            GraphicsContext mC = canvi.getMainGraphics2D();
            GraphicsContext tC = canvi.getTempGraphics2D();
            mC.setFill(colorPicker.getValue());
            mC.setStroke(colorPicker.getValue());
            mC.setLineWidth(Integer.parseInt(lineWidth.getText()));
            tC.setFill(colorPicker.getValue());
            tC.setStroke(colorPicker.getValue());
            tC.setLineWidth(Integer.parseInt(lineWidth.getText()));
            tC.setLineDashes(0);
        }
        
    }
    
    
    /*################## Canvas Functions ##################*/
    
    private boolean polyOn = false, dragged = false;
    private Double[] xCoord = new Double[0], yCoord = new Double[0];
    
    
    private myTools t = new myTools();

    /**
     *
     */
    public String toolstring;

    /**
     *
     * @return current tool name being used on graphics context or "Eraser if eraser brush selected.
     */
    public String getToolstring() {
        return toolstring;
    }

    /**
     *
     */
    public void setToolstring() {
        this.toolstring = toolsDrawing.getSelectedToggle().toString();
    }
    
    
   /**
    * Registers the selected ToggleButton and
    * prepares the GraphicsContext in accordance to the ToggleButton's name.
    * Does nothing if no ToggleButton is selected and Eraser brush isn't selected.
    * 
    *@param mouse
    */
    @FXML
    public void drawInit(MouseEvent mouse) {
        if (!parentTabPane.getSelectionModel().getSelectedItem().getText().contains("*")){
            parentTabPane.getSelectionModel().getSelectedItem().setText(parentTabPane.getSelectionModel().getSelectedItem().getText()+ " *");
        }
        
        if(toolsDrawing.getSelectedToggle() != null || toolstring.contains("Eraser")){
            Canvas tCS = getTempCanvas();
            GraphicsContext c = getMainGraphics2D();
            GraphicsContext tempC = getTempGraphics2D();
            
            
            
            if(toolstring.contains("FreeDraw") || toolstring.contains("Text") || toolstring.contains("Dropper") || toolstring.contains("Eraser")){
                t.setGraphicsContext(c);
            }else{
                t.setGraphicsContext(tempC);
            }
            
            /**************Drawing Tools**************/
            if (toolstring.contains("Eraser")){
                t.eraserStart(mouse, lineCircle.getRadius());
            }
            else if ( toolstring.contains("FreeDraw") ){
                t.freeDrawStart(mouse);
            }
            if( toolstring.contains("Line")){
                t.lineStart(mouse);
            }
            if( toolstring.contains("Circle") || toolstring.contains("Oval")){
                t.ellipseStart(mouse);
            }
            if( toolstring.contains("Square") || toolstring.contains("Rectangle")){
                t.rectangleStart(mouse);
            }
            if( toolstring.contains("Polygon")){
                if(polyOn == false){
                    xCoord = new Double[0];
                    yCoord = new Double[0];
                    polyOn = true;
                    dragged = true;
                    tCS.getScene().addEventFilter(KeyEvent.KEY_RELEASED, new EventHandler<KeyEvent>() {
                        @Override
                        public void handle(KeyEvent key) {
                            if(key.isShiftDown() && key.getCode().equals(key.getCode().ENTER)){
                                getTempGraphics2D().clearRect(0, 0, tempCanvas.getWidth(), tempCanvas.getHeight());
                                t.setGraphicsContext(getMainGraphics2D());
                                
                                t.polygonRelease(mouse, xCoord, yCoord);
                                polyOn = false;
                                tCS.getScene().removeEventFilter(KeyEvent.ANY, this);
                                
                            }
                        }
                    });
                }
                xCoord = addCoord(xCoord, t.polygonAddX(mouse));
                yCoord = addCoord(yCoord, t.polygonAddY(mouse));
            }
            if( toolstring.contains("Text")){
                Text text = new Text();
                if(!"".equals(lineTextInput.getText())){
                    text.setText(lineTextInput.getText());
                    text.setFont(lineText.getFont());
                    t.textStamp(mouse, text.getText(), text.getFont().getSize());
                }
                
            }
            if( toolstring.contains("Triangle")){
                t.triangleStart(mouse);
            }
            
            
            /**************Fill Tools**************/
            if( toolstring.contains("Dropper")){
                colorDrop(mouse);
            }
            
            /************Selection Tools************/
            if( toolstring.contains("SelectRect")){
                t.selectStart(mouse);
            }
            if( toolstring.contains("SelectMove")){
                t.moveSelectStart(mouse, getCanvas());
            }
        }
        
    }
    
    /**
     * Uses the currently selected ToggleButton to
     * draw a graphic onto the GraphicsContext.
     * Does nothing if no ToggleButton is selected and if Eraser isn't selected.
     * 
     * @param mouse
     */
    @FXML
    public void drawDrag(MouseEvent mouse) {
        GraphicsContext c = getMainGraphics2D();
        GraphicsContext tempC = getTempGraphics2D();
        
        if(toolsDrawing.getSelectedToggle() != null || toolstring.contains("Eraser")){
            if (dragged == false){
                Stack redoI = getStackImageRedo();
                Stack redoD = getStackDimensionsRedo();
                redoI.clear();
                redoD.clear();
                undoStackAdd(getCanvas());
                
                dragged = true;
            }
            
            if (!toolstring.contains("FreeDraw") && !toolstring.contains("Text") && !toolstring.contains("Eraser")){
               t.setGraphicsContext(tempC);
            }else{
               t.setGraphicsContext(c); 
            }
            
            t.setLayoutController(this);
            
            /**************Shape Tools**************/
            if (toolstring.contains("Eraser")){
                t.eraserDrag(mouse, lineCircle.getRadius());
            }
            if (toolstring.contains("FreeDraw")){
                t.freeDrawDrag(mouse);
            }
            if( toolstring.contains("Line")){
                t.lineDrag(mouse);
            }
            if( toolstring.contains("Circle")){
                t.circleDrag(mouse);
            }
            if( toolstring.contains("Oval")){
                t.ellipseDrag(mouse);
            }
            if( toolstring.contains("Square")){
                t.squareDrag(mouse);
            }
            if( toolstring.contains("Rectangle")){
                t.rectangleDrag(mouse);
            }
            if( toolstring.contains("Triangle")){
                t.triangleDrag(mouse);
            }
            
            /************Selection Tools************/
            if( toolstring.contains("SelectRect")){
                t.selectDrag(mouse);
            }
            if(toolstring.contains("SelectMove")){
                t.moveSelectDrag(mouse);
            }
            
            
        }
        
    }
    
    /**
     * For the polygon tool, allows user to see the next line being made in the polygon before clicking
     * @param mouse where the user's mouse is currently hovered on
     */
    @FXML
    public void drawHover(MouseEvent mouse){
        
        if (toolsDrawing.getSelectedToggle() != null){
            GraphicsContext tempC = getTempGraphics2D();
            t.setGraphicsContext(tempC);
            t.setLayoutController(this);
            
            if (toolstring.contains("Polygon")){
                if (polyOn == true){
                    t.polygonHover(mouse, xCoord, yCoord);
                }
            }
        }
        
    }
    
    /**
     * Draws the finished shape onto the main graphics context.
     * @param mouse the ending coordinates for most drawing functions
     */
    @FXML
    public void drawReleased(MouseEvent mouse) {
        if(toolsDrawing.getSelectedToggle() != null){
            if(toolstring.contains("SelectMove")){
                t.moveSelectRelease(mouse);
            }
            if(toolstring.contains("SelectRect")){
                t.selectRelease(mouse);
            }
            else if(dragged && !toolstring.contains("SelectMove") && !toolstring.contains("SelectRect") ){
                getTempGraphics2D().clearRect(0, 0, tempCanvas.getWidth(), tempCanvas.getHeight());
                t.setGraphicsContext(getMainGraphics2D());
                
                if (toolstring.contains("FreeDraw")){
                    t.freeDrawRelease(mouse);
                }
                if (toolstring.contains("Line")){
                    t.lineRelease(mouse);
                }
                if (toolstring.contains("Rectangle")){
                    t.rectangleRelease(mouse);
                }
                if (toolstring.contains("Square")){
                    t.squareRelease(mouse);
                }
                if (toolstring.contains("Oval")){
                    t.ellipseRelease(mouse);
                }
                if (toolstring.contains("Circle")){
                    t.circleRelease(mouse);
                }
                if (toolstring.contains("Triangle")){
                    t.triangleRelease(mouse);
                }
                dragged = false;
            }
            
        }
    }

    /**
     * Adds in new coordinates into the polygon array.
     * 
     * @param coord Coordinate array being added to
     * @param add Coordinate to be added into the array
     * @return The new coordinate array
     */
    private Double[] addCoord(Double[] coord, Double add){
        Double[] temp = new Double[coord.length+1];
        System.arraycopy(coord, 0, temp, 0, coord.length);
        temp[temp.length-1] = add;
        
        return temp;
    }
    
    /*################## Stack Functions ##################*/
    private Stack<Double[]> stackDimensions = new Stack<Double[]>();
    private Stack<Double[]> stackDimensionsRedo = new Stack<Double[]>();
    private Stack<Image> stackImage = new Stack<>();
    private Stack<Image> stackImageRedo = new Stack<>();
    
    /**
     *
     * @return current stackDimensions
     */
    public Stack<Double[]> getStackDimensions() {
        Canvas canvas = getCanvas();
        Stack<Double[]> dimensionsStack = stackDimensions;
        if(!getCanvas().equals(canvasTable)){
            for (myCanvas canvi : myCanvi) {
                if (canvas.equals(canvi.getCanvas())) {
                    dimensionsStack = canvi.getDimensionStack();
                }
            }
        }
        
        return dimensionsStack;
    }

    /**
     *
     * @return current stackDimensionsRedo
     */
    public Stack<Double[]> getStackDimensionsRedo() {
        Canvas canvas = getCanvas();
        Stack<Double[]> dimensionsStackRedo = stackDimensionsRedo;
        if(!getCanvas().equals(canvasTable)){
            for (myCanvas canvi : myCanvi) {
                if (canvi.getCanvas().equals(canvas)) {
                    dimensionsStackRedo = canvi.getRedoDimensionStack();
                }
            }
        }
        
        return dimensionsStackRedo;
    }

    /**
     *
     * @return stackImage
     */
    public Stack<Image> getStackImage() {
        Canvas canvas = getCanvas();
        Stack<Image> imageStack = stackImage;
        if(!getCanvas().equals(canvasTable)){
            for (myCanvas canvi : myCanvi) {
                if (canvi.getCanvas().equals(canvas)) {
                    imageStack = canvi.getImageStack();
                }
            }
        }
        
        return imageStack;
    }

    /**
     *
     * @return stackImageRedo
     */
    public Stack<Image> getStackImageRedo() {
        Canvas canvas = getCanvas();
        Stack<Image> redoImageStack = stackImageRedo;
        if(!getCanvas().equals(canvasTable)){
            for (myCanvas canvi : myCanvi) {
                if (canvi.getCanvas().equals(canvas)) {
                    redoImageStack = canvi.getRedoImageStack();
                }
            }
        }
        
        return redoImageStack;
    }
    
    /**
     *
     * @param canvas canvas to identify canvas stack to add to
     */
    public void undoStackAdd(Canvas canvas){
        DimensionStackAdd(canvas);
        ImageStackAdd(canvas);
    }

    /**
     *
     * @param canvas canvas to identify canvas stack to add to
     */
    public void DimensionStackAdd(Canvas canvas){
        Stack<Double[]> dimenStack = stackDimensions;
        
        if (!canvas.equals(canvasTable)){
            dimenStack = getStackDimensions();
        }
        
        Double[] dimensions = new Double[2];
        dimensions[0] = canvas.getWidth();
        dimensions[1] = canvas.getHeight();
        dimenStack.push(dimensions);
    }

    /**
     *
     * @param canvas canvas to identify canvas stack to add to
     */
    public void ImageStackAdd(Canvas canvas){
        Image image = canvas.snapshot(null, null);
        Stack<Image> imageStack = stackImage;
        
        if (!canvas.equals(canvasTable)){
            imageStack = getStackImage();
        }
        
        imageStack.push(image);
    }
    
    /**
     * Redraws the canvas in accordance to the newest entries of the redoDimension and redoURL stacks.
     * After which, those entries are placed into the dimension and URL stacks.
     * 
     * @param event 'Redo' option in 'Edit' menu. Alternatively,
     *              use keyboard shortcut, Default: 'CTRL + Shift + Z'
     */
    @FXML
    public void handleRedo(ActionEvent event) {
        Stack<Double[]> redoDStack = stackDimensionsRedo, dimenStack = stackDimensions;
        Stack<Image> redoImageStack = stackImageRedo, imageStack = stackImage;
        Canvas canvas = canvasTable, tCanvas = tempCanvas;
        
        if (!getCanvas().equals(canvasTable)){
            canvas = getCanvas();
            tCanvas = getTempCanvas();
            redoDStack = getStackDimensionsRedo();
            redoImageStack = getStackImageRedo();
            imageStack = getStackImage();
            dimenStack = getStackDimensions();
        }
        if(!redoImageStack.empty()){
            WritableImage wim = new WritableImage((int)canvas.getWidth(),(int)canvas.getHeight());
            imageStack.push(canvas.snapshot(null, wim));
            dimenStack.push(new Double[]{canvas.getWidth(),canvas.getHeight()});

            Double[] redoD = redoDStack.pop();
            canvas.setWidth(redoD[0]);
            canvas.setHeight(redoD[1]);
            tCanvas.setWidth(redoD[0]);
            tCanvas.setHeight(redoD[1]);

            getMainGraphics2D().clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
            redraw(redoImageStack.pop());
        }
    }
    
    /**
     * Redraws the main canvas in accordance to the newest entries of the dimension and URL stacks.
     * After which, those entries are placed into the redoDimension and redoURL stacks.
     * 
     * @param event 'Redo' option in 'Edit' menu. Alternatively,
     *              use keyboard shortcut, Default: 'CTRL + Z'
     */
    @FXML
    public void handleUndo(ActionEvent event) {
        Stack<Double[]> redoDStack = stackDimensionsRedo, dimenStack = stackDimensions;
        Stack<Image> redoImageStack = stackImageRedo, imageStack = stackImage;
        Canvas canvas = canvasTable, tCanvas = tempCanvas;
        
        if (!getCanvas().equals(canvasTable)){
            canvas = getCanvas();
            tCanvas = getTempCanvas();
            redoDStack = getStackDimensionsRedo();
            dimenStack = getStackDimensions();
            redoImageStack = getStackImageRedo();
            imageStack = getStackImage();
        }
        if(!imageStack.empty()){
            WritableImage wim = new WritableImage((int)canvas.getWidth(),(int)canvas.getHeight());
            redoImageStack.push(canvas.snapshot(null, wim));
            redoDStack.push(new Double[]{canvas.getWidth(),canvas.getHeight()});

            Double[] undoD = dimenStack.pop();
            canvas.setWidth(undoD[0]);
            canvas.setHeight(undoD[1]);
            tCanvas.setWidth(undoD[0]);
            tCanvas.setHeight(undoD[1]);

            getMainGraphics2D().clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
            redraw(imageStack.pop());
        }
    }
    
    /**
     * Clears the temporary canvas
     * 
     */
    public void canvasReload(){
        Canvas canvas = getTempCanvas();
        GraphicsContext GC = getTempGraphics2D();
        
        GC.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
    }
    
    /**
     * Replaces the drawImage function, due to successive redo's causing blurriness
     * has some pixelized artifacts, but doesn't become more blurry or more pixelized
     *
     * @param image image to be redrawn
     */
    public void redraw(Image image){
        PixelReader reader = image.getPixelReader();
        PixelWriter writer = getMainGraphics2D().getPixelWriter();
        for (int x = 0; x < image.getWidth(); x++  ) {
            for (int y = 0; y < image.getHeight(); y++  ) {
                int argb = reader.getArgb(x, y);
                Color color = reader.getColor(x, y);
                
                if (color.getOpacity() != 0.0) {
                  writer.setArgb(x, y, argb);
                }
            }
        }
    }
    
    /*################## Canvas View Functions ##################*/
    private Timer timerCountdown = new Timer(true);
    
    /**
     * Recreates the canvas to match the specified width and height.
     * 
     * @param width The width of the new canvas.
     * @param height The height of the new canvas.
     * 
     * @param keep Determines whether the current image will be resized to fit the new dimensions.
     *              If 0 and new canvas dimensions are smaller than previous dimensions, image will be cut off by canvas.
     *              If 1, the current image will be resized to fit the new dimensions.
     */
    public void resizeCanvas(int width, int height, int keep){
        Canvas canvas = getCanvas();
        GraphicsContext gc = getMainGraphics2D();
        undoStackAdd(canvas);
        
        Image curImg = canvas.snapshot(null, null);
            
        if(width != canvas.getWidth()){                  
            canvas.setWidth(width);
        }
        if(height != canvas.getHeight()){
            canvas.setHeight(height);
        }
        
        gc.setFill(WHITE);
        gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
        gc.setFill(colorPicker.getValue());
        
        if(keep == 1){
            Image resampled = resampler(curImg, width, height);
            gc.drawImage(resampled, 0,0, width, height);
        }else{
            canvasReload();
        }
    }
    
    /**
     * Resizes the given image to the specified width and height parameters.
     * 
     * @param input The image to be resized
     * @param newW The new width
     * @param newH The new height
     * @return The resized image
     */
    public Image resampler(Image input, int newW, int newH){
        ImageView imageView = new ImageView(input);
        imageView.setFitWidth(newW);
        imageView.setFitHeight(newH);
        imageView.setPreserveRatio(false);
        return imageView.snapshot(null, null);
    }

    /**
     * Changes the zoom factor of the scroll pane of the current canvas, changing by +-.25.
     * resetting the zoom, puts the factor back to 0
     * @param event 
     */
    @FXML
    private void zoomIn(ActionEvent event) {
        ScrollPane sc = (ScrollPane) parentTabPane.getSelectionModel().getSelectedItem().getContent();
        sc.setScaleX(sc.getScaleX() + .25);
        sc.setScaleY(sc.getScaleY() + .25);
    }
    @FXML
    private void zoomOut(ActionEvent event) {
        ScrollPane sc = (ScrollPane) parentTabPane.getSelectionModel().getSelectedItem().getContent();
        sc.setScaleX(sc.getScaleX() - .25);
        sc.setScaleY(sc.getScaleY() - .25);
    }
    @FXML
    private void zoomReset(ActionEvent event) {
        ScrollPane sc = (ScrollPane) parentTabPane.getSelectionModel().getSelectedItem().getContent();
        sc.setScaleX(1);
        sc.setScaleY(1);
    } 

    /**
     * Adds in a countdown timer to the bottom of the screen for the currently selected canvas tab.
     * 
     * @param event When the "Enable Autosave Timer" is checked
     */
    @FXML
    private void handleAutosaveView(ActionEvent event) {
        if(checkAutosaveView.isSelected()){
            HBox hbox = new HBox();
            Label label = new Label();
            
            hbox.setAlignment(Pos.CENTER_LEFT);
            label.setPadding(new Insets(0,0,0,63));
            label.setTextAlignment(TextAlignment.CENTER);
            label.setText(getCountdown());
            hbox.getChildren().add(label);
            parentBorderPane.setBottom(hbox);
            
            
            timerCountdown.scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {
                    Platform.runLater(new Runnable(){ 
                        @Override
                        public void run(){
                            label.setText(getCountdown());
                        }
                    });
                }
            }, 0, 1000);
            
        }else{
            parentBorderPane.setBottom(null);
            timerCountdown.purge();
        }
    }
    
    /**
     * Gets the countdown for the autosave timer currently in view
     * @return The autosave timer's countdown string
     */
    private String getCountdown(){
        String countdown = "Please enable autosaving, then save the current file to see a countdown.";
        
        for (myAutosave autosave : autosaves) {
            if (autosave.getCanvi().equals(getCanvas())){
                countdown = autosave.getCD();
            }
        }
        
        return countdown;
    }

    

}
