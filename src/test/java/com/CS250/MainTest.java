/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.CS250;

import static java.util.function.Predicate.isEqual;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Menu;
import javafx.scene.control.Tab;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.stage.Stage;
import static org.hamcrest.Matchers.comparesEqualTo;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.not;
import org.junit.Test;
import org.loadui.testfx.GuiTest;
import org.testfx.api.FxAssert;
import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.assertions.api.Assertions.assertThat;
import org.testfx.framework.junit.ApplicationTest;
import org.testfx.matcher.base.NodeMatchers;
import org.testfx.service.query.NodeQuery;
import org.testfx.util.WaitForAsyncUtils;
import static org.testfx.util.WaitForAsyncUtils.waitForFxEvents;

/**
 *
 * @author Blazi
 */
public class MainTest extends ApplicationTest{
    final String[] tools = new String[]{"#toolText", "#toolFreeDraw", "#toolPolygon", "#toolLine",
        "#toolCircle", "#toolOval", "#toolSquare", "#toolRectangle", "#toolTriangle",
        "#toolDropper", "#toolSelectMove", "#toolSelectRect"};
    final String canvas = "#canvasTable";
    private LayoutController lc;
    
    @Override
    public void start(Stage stage) throws Exception{
        Parent root;
        Scene scene;
        
        FXMLLoader loader = new FXMLLoader(Main.class.getResource("Layout.fxml"));
        root = loader.load();
        lc = loader.getController();
        
        stage.setTitle("Paint in Netbeans");
        scene = new Scene(root);
        
        stage.setScene(scene);
        stage.show();
    }
    
    /*
    *
    */
    @Test
    public void testToolbarToggle(){
        for ( String tool: tools){
            verifyThat(tool, NodeMatchers.isNotNull());
            clickOn(tool);
        }
    }
    
    @Test
    public void newFileResponse(){
        Menu menu = lc.Save.getParentMenu();
        menu.setId("FileMenu");
        clickOn("#FileMenu");
        clickOn("#newFile");
        menu.setId(null);
        
        verifyThat(lc.parentTabPane.getTabs().size(), comparesEqualTo(2));
    }

    @Test
    public void checkIfImageStackDifferent(){
        Menu menu = lc.Save.getParentMenu();
        menu.setId("FileMenu");
        clickOn("#FileMenu");
        clickOn("#newFile");
        menu.setId(null);
        
        lc.parentTabPane.getSelectionModel().selectLast();
        clickOn("#toolLine");
        Canvas nCanvas = lc.getCanvas();
        nCanvas.setId("tempID");
        
        Image compare = lc.getStackImage().peek();
        drag("#tempID");
        dropBy(150,150);
        Image actual = lc.getStackImage().peek();
        
        verifyThat(compare, not(isEqual(actual)));
    }
    
    
}
