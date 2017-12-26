package application;

import java.io.IOException;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

public class UsuariosCtrl {

    @FXML
    private AnchorPane panel,panelBuscar;

    @FXML
    private JFXButton btn_newUser;

    @FXML
    void onNewUser(ActionEvent event) {
    	try {
    		IngresoPerCtrl.origen="usuario";
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
    		ModificacionPerCtrl.origen="usuario";
    		AnchorPane pane = (AnchorPane)FXMLLoader.load(getClass().getResource("ModificacionPersonal.fxml"));
			
			panel.getChildren().setAll(pane);
		    
    	} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

    }
    
    @FXML
    void onConsulUser(ActionEvent event) {
    	try {
    		//ModificacionPerCtrl.origen="usuario";
    		AnchorPane pane = (AnchorPane)FXMLLoader.load(getClass().getResource("buscarPersonal.fxml"));
			
			panel.getChildren().setAll(pane);
		    
    	} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

    }
    
    
 
    
    
    

}
