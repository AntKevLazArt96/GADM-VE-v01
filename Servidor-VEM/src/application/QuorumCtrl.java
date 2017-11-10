package application;

import java.io.IOException;

import com.jfoenix.controls.JFXButton;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class QuorumCtrl {

    @FXML
    private JFXButton btn_voz;

    @FXML
    private JFXButton btnIniVoto;

    @FXML
    private JFXButton btnCerrar;

    @FXML
    void IniVotoAction(ActionEvent event) throws IOException {
    	Stage stage = (Stage) btnIniVoto.getScene().getWindow();
	    // do what you have to do
	    stage.close();
    	
    	Stage newStage = new Stage();
		
	    AnchorPane pane = (AnchorPane)FXMLLoader.load(getClass().getResource("PrincipalSecretaria.fxml"));
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
    void onCerrar(ActionEvent event) {
    	// get a handle to the stage
	    Stage stage = (Stage) btnCerrar.getScene().getWindow();
	    // do what you have to do
	    stage.close();
    }

}
