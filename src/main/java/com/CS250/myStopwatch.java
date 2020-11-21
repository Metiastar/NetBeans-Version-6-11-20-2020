/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.CS250;

import java.util.TimerTask;
import javafx.application.Platform;

/**
 *
 * @author Blazi
 */
public class myStopwatch extends TimerTask {
    private int minutes, seconds;
    private String CD;

    /**
     *
     */
    public myStopwatch(){
        minutes = 5;
        seconds = 0;
    }
    
    /**
     *
     * @return
     */
    public String getCD(){
        CD = minutes + ":" + seconds;
        if (seconds < 10){
           CD = minutes + ":0" + seconds; 
        }
        return CD;
    }
    
    /**
     *
     */
    @Override
    public void run() {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                if ((seconds-1) == -1 && minutes != 0){
                    minutes -= 1;
                    seconds = 59;
                }else if((seconds-1)== 0 && minutes == 0){
                    minutes = 5;
                    seconds = 0;
                }else{
                    seconds -=1;
                }
            }
        });
    }
    
}
