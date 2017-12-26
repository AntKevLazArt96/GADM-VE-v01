package application;

import java.io.IOException;

import com.jfoenix.controls.JFXButton;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

public class PanelControlCtrl {

    @FXML
    private AnchorPane panel;


   
    @FXML
    void onConfi(ActionEvent event) {
    	try {
    		ConfiguracionCtrl.origen="panel";
    		AnchorPane pane = (AnchorPane)FXMLLoader.load(getClass().getResource("Configuracion.fxml"));
			
			panel.getChildren().setAll(pane);
		    
    	} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

    }
    
    @FXML
    void onPref(ActionEvent event) {
    	try {
    		ConfiguracionIniCtrl.isPanel=true;
    		ConfiguracionIniCtrl.origen="panel";
    		AnchorPane pane = (AnchorPane)FXMLLoader.load(getClass().getResource("ConfiguracionIni.fxml"));	
			panel.getChildren().setAll(pane);
    	} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

    }
    

}
