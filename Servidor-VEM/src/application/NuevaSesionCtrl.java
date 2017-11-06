package application;

import java.io.IOException;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.DatePicker;
import javafx.scene.layout.AnchorPane;

public class NuevaSesionCtrl {

    @FXML
    private AnchorPane panel;
    @FXML
    private JFXDatePicker date;
    
    @FXML
    private JFXDatePicker time;

    @FXML
    private JFXButton btn_cancelar;

    @FXML
    void onCancelAction(ActionEvent event) {
    	/*try {
    		AnchorPane pane = (AnchorPane)FXMLLoader.load(getClass().getResource("subSesiones.fxml"));
			
			panel.getChildren().setAll(pane);
		    
    	} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
    }

}
