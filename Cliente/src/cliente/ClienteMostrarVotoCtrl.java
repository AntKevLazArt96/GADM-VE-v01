package cliente;

import java.net.URL;
import java.util.ResourceBundle;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;

public class ClienteMostrarVotoCtrl implements Initializable {

	@FXML
	private Label label_punto;

	@FXML
	private JFXTextArea label_titulo;

	@FXML
	private JFXButton verVoto;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// cargar header

		label_punto.setText(puntoATratar.num_punto);
		label_titulo.setText(puntoATratar.tema);

		if (data.voto.equals("FAVOR")) {
			verVoto.setText("A FAVOR");
			verVoto.setStyle("-fx-background-color:  #4CAF50;");
			 
		}
		if (data.voto.equals("CONTRA")) {
			verVoto.setText("EN CONTRA");
			verVoto.setStyle("-fx-background-color:  #F44336;");
		}

		if (data.voto.equals("SALVO")) {
			verVoto.setText("VOTO SALVADO");
			verVoto.setTextFill(Color.BLACK);
			verVoto.setStyle("-fx-background-color:  #B2EBF2;");
		}

		if (data.voto.equals("BLANCO")) {
			verVoto.setText("EN BLANCO");
			verVoto.setTextFill(Color.BLACK);
			verVoto.setStyle("-fx-background-color:  #F5F5F5;");
		}

	}

}
