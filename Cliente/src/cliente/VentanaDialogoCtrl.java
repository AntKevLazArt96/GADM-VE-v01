package cliente;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

public class VentanaDialogoCtrl implements Initializable {

	@FXML
	private Label lbl_header;

	@FXML
	private Label lbl_cuerpo;

	@FXML
	private JFXButton btn_aceptar;

	@FXML
	void onAceptar() throws IOException {
		Stage stage = (Stage) btn_aceptar.getScene().getWindow();
		// do what you have to do
		stage.close();
		if (!data.header.equals("Aviso")) {
			System.exit(0);
		}
	}

	public void buttonPressed(KeyEvent e) throws IOException {
		if (e.getCode().toString().equals("ENTER")) {
			onAceptar();
		}
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		lbl_header.setText(data.header);
		lbl_cuerpo.setText(data.cuerpo);
	}

}
