package application;

import java.io.IOException;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.ResourceBundle;
import org.json.simple.JSONObject;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import clases.TramVotoOrden;
import clases.data;
import gad.manta.common.Sesion;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

public class InicioVotoOrdenCtrl implements Initializable {

	@FXML
	public JFXTextArea label_titulo;

	@FXML
	public JFXButton btn_finVoto;

	@FXML
	public JFXButton btn_reVoto;

	// objetos del Quorum que se referenciaran en la pantalla principal
	@FXML
	public ImageView img1, img2, img3, img4, img5, img6, img7, img8, img9, img10, img11, img12;

	@FXML
	public Label user1, user2, user3, user4, user5, user6, user7, user8, user9, user10, user11, user12;

	@FXML
	public Label status1, status2, status3, status4, status5, status6, status7, status8, status9, status10, status11,
			estatus12;

	@FXML
	public Label lblAprobado;

	@FXML
	public Label lblRechazado;

	@FXML
	public Label lblEspera;

	@FXML
	public AnchorPane panelInicioVotoOrden;

	@SuppressWarnings("unchecked")
	@FXML
	void finVoto(ActionEvent event) throws IOException {
		if (Integer.valueOf(lblAprobado.getText()) >= 1) {
			// para guardar los votos de la sesion aprobada
			data.voto = "APROBADO";
			try {
				JSONObject js = new JSONObject();
				js.put("name", "APROBADO");

				String json = js.toJSONString();

				System.out.println("Se envio:" + json);
				PantallaPrincipalCtrl.dos.writeUTF(json);

			} catch (IOException E) {
				E.printStackTrace();
			}
			FXMLLoader loader = new FXMLLoader(getClass().getResource("InicioSesion.fxml"));
			AnchorPane quorum = (AnchorPane) loader.load();
			panelInicioVotoOrden.getChildren().setAll(quorum);
		} else {

		}

	}

	@FXML
	void reiniciarVoto(ActionEvent event) throws IOException {
		TramVotoOrden t = new TramVotoOrden();
		t.reiniciarVotoOrden();
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		try {
			Sesion sesion = LoginController.servidor.consultarSesion();
			label_titulo.setText(sesion.getDescription());
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		btn_finVoto.setDisable(true);
		btn_reVoto.setDisable(true);

	}

}
