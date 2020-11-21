/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.CS250;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.UnaryOperator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.TextFormatter.Change;
import javafx.stage.Popup;

/**
 * FXML Controller class
 *
 * @author Blazi
 */
public class CanvasResizeController implements Initializable {

    /**
     *
     */
    public Popup popup;

    /**
     *
     */
    public LayoutController lc;

    /**
     *
     */
    public int[] values = new int[3];
    
    @FXML
    private TextField textWidth;
    @FXML
    private TextField textHeight;
    @FXML
    private CheckBox checkImageResize;
    
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        checkImageResize.setSelected(false);
        
        //UnaryOperator from Uwe, found at https://stackoverflow.com/a/36436243
        UnaryOperator<Change> numberFilter = change -> {
            String text = change.getText();

            if (text.matches("[0-9]*")) {
                return change;
            }

            return null;
        };
        
        textWidth.setTextFormatter(new TextFormatter(numberFilter));
        textHeight.setTextFormatter(new TextFormatter(numberFilter));
    }    

    /**
     *
     * @param resize
     */
    public void setPopup(Popup resize) {
        popup = resize;
    }
    
    /**
     *
     * @param layout
     */
    public void setLayoutController(LayoutController layout){
        lc = layout;
    }
    
    /**
     *
     * @param initial
     */
    public void setInitialValues(int[] initial){
        String width = Integer.toString(initial[0]);
        String height = Integer.toString(initial[1]);
        textWidth.setPromptText(width + "px");
        textHeight.setPromptText(height + "px");
        values[0] = initial[0];
        values[1] = initial[1];
        values[2] = 0;
    }
    
    /**
     *
     */
    public void setValues(){
        String width = textWidth.getText();
        String height = textHeight.getText();
        if(! textWidth.getText().isEmpty()){
            values[0] = Integer.parseInt(width);
        }
        if(! textHeight.getText().isEmpty()){
            values[1] = Integer.parseInt(height);
        }
        
        if ( checkImageResize.isSelected()){
            values[2] = 1;
        }else{
            values[2] = 0;
        }
    }
    
    @FXML
    private void Exit(ActionEvent event) {
        popup.hide();
    }

    @FXML
    private void saveChanges(ActionEvent event) throws IOException {
        lc.undoStackAdd(lc.getCanvas());
        setValues();
        lc.resizeCanvas(values[0], values[1], values[2]);
        
        popup.hide();
    }

    
    
    

    
   
    
}
