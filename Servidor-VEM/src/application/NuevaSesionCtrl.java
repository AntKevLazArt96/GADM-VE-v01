package application;

import java.io.IOException;
import java.net.URL;
import java.time.LocalTime;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTimePicker;
import com.sun.glass.ui.Timer;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.layout.AnchorPane;

public class NuevaSesionCtrl implements Initializable{

    @FXML
    private AnchorPane panel;
    @FXML
    private JFXDatePicker date;
    
    @FXML
    private JFXTimePicker time;
    
    @FXML
    private JFXComboBox cbx_tipoSes;
    

    @FXML
    private JFXButton btn_cancelar;

    @FXML
    void onCancelAction(ActionEvent event) {
    	try {
    		AnchorPane pane = (AnchorPane)FXMLLoader.load(getClass().getResource("subSesiones.fxml"));
			
			panel.getChildren().setAll(pane);
		    
    	} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		time.setValue(LocalTime.of(16, 00));
		System.out.println("La hora es: "+time.getAccessibleText());
	}

}
