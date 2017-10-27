package cliente;

import java.io.IOException;

import com.jfoenix.controls.JFXButton;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class ClientePrincipalController {

    @FXML
    private JFXButton endSesionButton;

    @FXML
    void endSesion(ActionEvent event) throws IOException {
    	// logica para cerrar sesion
	    Stage actualStage = (Stage) endSesionButton.getScene().getWindow();
	    // do what you have to do
	    actualStage.close();
	    
		Stage newStage = new Stage();
		AnchorPane pane = (AnchorPane)FXMLLoader.load(getClass().getResource("login.fxml"));
        Scene scene = new Scene(pane);

        newStage.setScene(scene);
        newStage.initStyle(StageStyle.UNDECORATED);
        newStage.show();
    }

}
