package application;

import java.io.IOException;

import com.jfoenix.controls.JFXButton;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

public class subInicioCtrl {

    @FXML
    private AnchorPane panel;

    @FXML
    private JFXButton btn_newUser;

    @FXML
    void onNewUser(ActionEvent event) {
    	try {
    		IngresoPerCtrl.origen="inicio";
    		AnchorPane pane = (AnchorPane)FXMLLoader.load(getClass().getResource("IngresoPersonal.fxml"));
			
			panel.getChildren().setAll(pane);
		    
    	} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

    }
    @FXML
    void onModiUser(ActionEvent event) {
    	try {
    		ModificacionPerCtrl.origen="inicio";
    		AnchorPane pane = (AnchorPane)FXMLLoader.load(getClass().getResource("ModificacionPersonal.fxml"));
			
			panel.getChildren().setAll(pane);
		    
    	} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

    }
    @FXML
    void onConfi(ActionEvent event) {
    	try {
    		ConfiguracionCtrl.origen="inicio";
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
    		ConfiguracionIniCtrl.origen="inicio";
    		ConfiguracionIniCtrl.isPanel=true;
    		AnchorPane pane = (AnchorPane)FXMLLoader.load(getClass().getResource("ConfiguracionIni.fxml"));	
			panel.getChildren().setAll(pane);
    	} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

    }
    
    

}
