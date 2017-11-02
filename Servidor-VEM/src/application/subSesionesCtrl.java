package application;

import java.io.IOException;

import com.jfoenix.controls.JFXButton;

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
    void btnNuevoAction(ActionEvent event) {
    	try {
			AnchorPane pane = (AnchorPane)FXMLLoader.load(getClass().getResource("NuevaSesion.fxml"));
			panel.getParent();
    	} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

    }

}

