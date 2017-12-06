package cliente;

import java.net.URL;
import java.util.ResourceBundle;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

public class ClienteMostrarVotoOrdenCtrl implements Initializable {
	@FXML
	private Label label_convocatoria;

	@FXML
	private JFXTextArea label_titulo;

	@FXML
	private JFXButton verVoto;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// cargar header

		label_titulo.setText(data.titulo);
		label_convocatoria.setText(data.convocatoria);

		if (data.voto.equals("APROBADO")) {
			verVoto.setText("APROBADO");
		}
		if (data.voto.equals("RECHAZADO")) {
			verVoto.setText("RECHAZADO");
			verVoto.setStyle("-fx-background-color: red;");
		}

	}

}
