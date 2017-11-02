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
import javafx.scene.layout.AnchorPane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class subInicioCtrl {

    @FXML
    private JFXButton btn_inicio;

    @FXML
    void iniciarAction(ActionEvent event) throws IOException {
    	/*Stage actualStage = (Stage) btn_inicio.getScene().getWindow();
	    // do what you have to do
	    actualStage.close();*/
	    
	    Stage newStage = new Stage();
		
	    AnchorPane pane = (AnchorPane)FXMLLoader.load(getClass().getResource("InicioSesion.fxml"));
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
