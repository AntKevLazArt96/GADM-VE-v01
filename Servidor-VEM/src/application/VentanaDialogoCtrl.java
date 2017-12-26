package application;

import java.io.IOException;
import java.net.URL;
import java.rmi.NotBoundException;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;

import clases.data;
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
	private JFXButton btn_cancelar;

	@FXML
	void onAceptar() throws IOException {

		data.documentacion = 0;
		Stage stage = (Stage) btn_aceptar.getScene().getWindow();
		// do what you have to do
		stage.close();
		if (data.cerrar == true) {
			System.exit(0);
		}
	}

	@FXML
	void onCancel() {
		data.documentacion = 1;
		Stage stage = (Stage) btn_aceptar.getScene().getWindow();
		// do what you have to do
		stage.close();
	}

	public void buttonPressed(KeyEvent e) throws IOException {
		if (e.getCode().toString().equals("ENTER")) {
			onAceptar();
		}
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		btn_cancelar.setVisible(false);
		lbl_header.setText(data.header);
		lbl_cuerpo.setText(data.cuerpo);

		if (data.tipoMsgDial != null && data.tipoMsgDial.equals("Cancel")) {
			btn_cancelar.setVisible(true);
		}

	}

}
