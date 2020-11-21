/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.CS250;

import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;
import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.WritableImage;
import javax.imageio.ImageIO;

/**
 *
 * @author Blazi
 */
public class myAutosave extends TimerTask{
    private final Canvas canvi;
    private final File autosavesDirectory;
    private final File autosavesFile;
    private final myStopwatch stc = new myStopwatch();
    private final Timer stopwatch = new Timer(true);
    
    /**
     *
     * @param canvas
     * @param directory
     */
    public myAutosave(Canvas canvas, File directory){
        canvi = canvas;
        stopwatch.scheduleAtFixedRate(stc, 0, 1000);
        
        //makes the path for the new autosave directory using the directory of where the current canvas image is being saved
        int parseDirectory = directory.getAbsolutePath().indexOf(directory.getName());
        autosavesDirectory = new File(directory.getAbsolutePath().substring(0, parseDirectory) +
        "/NBPaint_autosaves");
        
        //using the new directory, appends the original file's name to the end to create a new file in the directory in the run command
        autosavesFile = new File(directory.getAbsolutePath().substring(0, parseDirectory) +
        "/NBPaint_autosaves/" +
        directory.getName());
        
        autosavesDirectory.mkdir();
    }

    /**
     *
     * @return
     */
    public Canvas getCanvi() {
        return canvi;
    }
    
    /**
     *
     * @return
     */
    public String getCD(){
        String cd = stc.getCD();
        return cd;
    }
    
    /**
     *
     */
    public void cancelStopwatch(){
        stopwatch.cancel();
    }
    
    /**
     *
     */
    @Override
    public void run() {
    
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                if (autosavesDirectory.exists()) {
                    
                    File outputS = autosavesFile;
                    try {
                        Canvas canvas = canvi;
                        WritableImage writableImage = new WritableImage((int) canvas.getWidth(), (int) canvas.getHeight());
                        canvas.snapshot(null, writableImage);
                        RenderedImage ri = SwingFXUtils.fromFXImage(writableImage, null);
                        
                        ImageIO.write(ri, "png", outputS);
                    } catch (IOException ex) {
                        System.out.println(ex.getMessage());
                    }
                    
                }
            }
        });
    
    }
}
