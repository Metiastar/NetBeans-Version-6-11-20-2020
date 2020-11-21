/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.CS250;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Blazi
 */
public class myLoggingTask extends TimerTask {

    /**
     *
     */
    public LayoutController controller;

    /**
     *
     * @param LC
     */
    public myLoggingTask(LayoutController LC){
        controller = LC;
    }
    
    /**
     *
     */
    @Override
    public void run() {
        String selectedTool;
                
        File logs = new File("Logs.txt");
        
        String date = java.time.LocalTime.now().toString();
        if(controller.toolsBrushes.getSelectedToggle().toString().contains("Eraser")){
            selectedTool = "Eraser";
        }else{
            selectedTool = controller.getToolstring();
            int parseTool = selectedTool.lastIndexOf("id=");
            int endParse = selectedTool.indexOf(",");
            selectedTool = selectedTool.substring(parseTool, endParse);
        }
        String selectedFile = controller.parentTabPane.getSelectionModel().getSelectedItem().getText();
        
        
        try {
            if (!logs.createNewFile()){
                /*
                * appends the logs file with the buffer(new) data
                */
                String buffer = "\n" + selectedFile + "\t\t" + selectedTool + "\t\t" + date;
                try (FileWriter writer = new FileWriter(logs, true)) {
                    writer.write(buffer);
                    writer.close();
                } catch (IOException ex) {
                    Logger.getLogger(myLoggingTask.class.getName()).log(Level.SEVERE, null, ex);
                }
            }else{
                try {
                    String buffer = selectedFile + "\t\t" + selectedTool + "\t\t" + date;
                    try (FileWriter writer = new FileWriter(logs, false)) {
                        writer.write(buffer);
                        writer.close();
                        logs.deleteOnExit();
                    }
                    
                } catch (IOException ex) {
                    Logger.getLogger(myLoggingTask.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(myLoggingTask.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
