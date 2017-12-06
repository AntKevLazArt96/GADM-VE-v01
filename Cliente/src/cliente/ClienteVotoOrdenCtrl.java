package cliente;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URL;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ResourceBundle;
import org.json.simple.JSONObject;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import cliente.PantallaPrincipalCtrl;
import gad.manta.common.Sesion;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;

public class ClienteVotoOrdenCtrl implements Initializable {

	@FXML
	private Label label_convocatoria;

	@FXML
	private JFXTextArea label_titulo;

	@FXML
	private JFXButton bnt_aprobar;

	@FXML
	private JFXButton bntRechazar;

	@FXML
	private AnchorPane panel_votoOrden;

	@SuppressWarnings("unchecked")
	public void seVoto() {
		try {

			JSONObject js = new JSONObject();
			js.put("name", "Se Voto");

			String json = js.toJSONString();

			System.out.println("Se envio:" + json);
			PantallaPrincipalCtrl.dos.writeUTF(json);

		} catch (IOException E) {
			E.printStackTrace();
		}
	}

	@FXML
	void onAprobar(ActionEvent event) throws NotBoundException, IOException {
		String login = LoginController.servidor.addVoto(data.name, "APROBADO", data.img);
		data.voto = "APROBADO";
		System.out.println(login);
		FXMLLoader loader = new FXMLLoader(getClass().getResource("ClienteMostrarVotoOrden.fxml"));
		AnchorPane Presesion = (AnchorPane) loader.load();
		panel_votoOrden.getChildren().setAll(Presesion);
		seVoto();

	}

	@FXML
	void onRechazar(ActionEvent event) throws NotBoundException, IOException {
		LoginController.servidor.addVoto(data.name, "RECHAZADO", data.img);
		data.voto = "RECHAZADO";
		FXMLLoader loader = new FXMLLoader(getClass().getResource("ClienteMostrarVotoOrden.fxml"));
		AnchorPane Presesion = (AnchorPane) loader.load();
		panel_votoOrden.getChildren().setAll(Presesion);
		seVoto();
	}

	public Image convertirImg(byte[] bytes) throws IOException {
		ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
		Image img = new Image(bis);
		return img;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		try {
			Sesion sesion = LoginController.servidor.consultarSesion();
			data.convocatoria = sesion.getConvocatoria();
			data.titulo = sesion.getDescription();
			label_titulo.setText(sesion.getDescription());
			label_convocatoria.setText(sesion.getConvocatoria());
		} catch (RemoteException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
}
