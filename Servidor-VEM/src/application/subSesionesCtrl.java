package application;

import java.io.IOException;

import com.jfoenix.controls.JFXButton;

import clases.data;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

public class subSesionesCtrl {

	@FXML
    private AnchorPane panel;

    @FXML
    private JFXButton bnt_nuevo;
    @FXML
    private JFXButton btn_modifi;
    @FXML
    void btnNuevoAction(ActionEvent event) {
    	try {
    		AnchorPane pane = (AnchorPane)FXMLLoader.load(getClass().getResource("NuevaSesion.fxml"));
			
			panel.getChildren().setAll(pane);
		    
    	} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

    }
    @FXML
    void btnModiAction(ActionEvent event) {
    	data.verSesion=false;
    	try {
    		AnchorPane pane = (AnchorPane)FXMLLoader.load(getClass().getResource("ModificacionSesion.fxml"));
			
			panel.getChildren().setAll(pane);
		    
    	} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

    }
    

}

