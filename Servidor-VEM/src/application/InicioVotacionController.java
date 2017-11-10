/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package application;

import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Usuario
 */


public class InicioVotacionController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    @FXML
    private JFXButton btn_voz;
    
    @FXML
    private JFXButton btn_finVoto;
    
    @FXML
    private AnchorPane panelvoz;
    
    @FXML
    void finVoto(ActionEvent event) {
    	Stage stage = (Stage) btn_finVoto.getScene().getWindow();
	    // do what you have to do
	    stage.close();
    }
    
    @FXML
    void handleButtonAction(ActionEvent event) {
    	if(event.getSource()==btn_voz) {
    		
    		if(panelvoz.visibleProperty().getValue()==true)
    		{
    			panelvoz.setVisible(false);
    		}
    		else {
    			panelvoz.setVisible(true);
    		}
    		
    	}
    }
    
}
