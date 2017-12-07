package cliente;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class VentanaDialogoCtrl implements Initializable {

	@FXML
	private Label lbl_header;

	@FXML
	private Label lbl_cuerpo;

	@FXML
	private JFXButton btn_aceptar;

	@FXML
	void onAceptar(ActionEvent event) throws IOException {
		Stage stage = (Stage) btn_aceptar.getScene().getWindow();
		// do what you have to do
		stage.close();
		System.exit(0);
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

	}

}
