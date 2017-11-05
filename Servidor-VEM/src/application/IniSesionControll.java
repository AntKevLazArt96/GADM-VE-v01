package application;

import java.io.IOException;
import java.rmi.Naming;

import com.jfoenix.controls.JFXButton;

import gad.manta.common.IServidor;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class IniSesionControll {

    @FXML
    private JFXButton btnIniVoto,btn_fin,btn_voz;

    @FXML
    void IniVotoAction(ActionEvent event) throws IOException {
    	/*// get a handle to the stage
	    Stage actualStage = (Stage) btnIniVoto.getScene().getWindow();
	    // do what you have to do
	    actualStage.close();
	    */
	    
	    Stage newStage = new Stage();
		
	   
	   AnchorPane pane = (AnchorPane)FXMLLoader.load(getClass().getResource("InicioVotacion.fxml"));
        Scene scene = new Scene(pane);
        
        //Pantalla completa
        Screen screen = Screen.getPrimary();
		Rectangle2D bounds = screen.getVisualBounds();

		newStage.setX(bounds.getMinX());
		newStage.setY(bounds.getMinY());
		newStage.setWidth(bounds.getWidth());
		newStage.setHeight(bounds.getHeight());
        
        
        newStage.setScene(scene);
        newStage.initStyle(StageStyle.UNDECORATED);
        newStage.show();
    }

    @FXML
    private AnchorPane panelvoz;
    @FXML
    void finAction(ActionEvent event) {
    	// get a handle to the stage
	    Stage stage = (Stage) btn_fin.getScene().getWindow();
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
