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
import servidor.Servidor;

public class PrincipalSecretariaCtrl {

    @FXML
    private JFXButton btnIniSesion;

    @FXML
    void iniSesion(ActionEvent event) throws IOException {
    	
    	
    	Servidor servidor = new Servidor();
    	servidor.iniciarSesion();
    	
    	
    	
    	
    	
    	Stage stage = (Stage) btnIniSesion.getScene().getWindow();
	    // do what you have to do
	    stage.close();
    	
    	Stage newStage = new Stage();
		
	    AnchorPane pane = (AnchorPane)FXMLLoader.load(getClass().getResource("inicioSesion.fxml"));
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

}
