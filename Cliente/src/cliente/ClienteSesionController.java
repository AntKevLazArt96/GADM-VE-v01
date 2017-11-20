package cliente;

import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;

import gad.manta.common.Sesion;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;

public class ClienteSesionController implements Initializable {

    @FXML
    private JFXButton btn_voz;

    @FXML
    private Label label_convocatoria;

    @FXML
    private Circle cirlogin;

    @FXML
    private Label lbl_nombre;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		lbl_nombre.setText(data.name);
		cirlogin.setFill(new ImagePattern(data.imagen));
        cirlogin.setStroke(Color.SEAGREEN);
        cirlogin.setEffect(new DropShadow(+25d, 0d, +2d, Color.DARKGREEN));
		label_convocatoria.setText(data.convocatoria);
		
	}

}
