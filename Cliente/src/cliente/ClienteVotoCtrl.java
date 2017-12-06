package cliente;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URL;
import java.rmi.NotBoundException;
import java.util.ResourceBundle;
import org.json.simple.JSONObject;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;

public class ClienteVotoCtrl implements Initializable {

	@FXML
	private JFXButton btn_favor;

	@FXML
	private JFXButton btn_contra;

	@FXML
	private JFXButton btn_blanco;

	@FXML
	private JFXButton btn_salvo;

	@FXML
	private JFXTextArea label_titulo;

	@FXML
	private Label label_punto;

	@FXML
	private Label lbl_proponente;

	@FXML
	private AnchorPane panel_voto;

	@SuppressWarnings("unchecked")
	public void seVoto() throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("ClienteMostrarVoto.fxml"));
		AnchorPane Presesion = (AnchorPane) loader.load();
		panel_voto.getChildren().setAll(Presesion);
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
	void onBlanco(ActionEvent event) throws IOException {
		LoginController.servidor.addVoto(data.name, "EN BLANCO", data.img);
		data.voto = "BLANCO";
		seVoto();
	}

	@FXML
	void onContra(ActionEvent event) throws IOException {
		LoginController.servidor.addVotoPunto(data.name, "EN CONTRA", data.img);
		data.voto = "CONTRA";
		seVoto();
	}

	@FXML
	void onFavor(ActionEvent event) throws NotBoundException, IOException {
		System.out.println(data.name + "  " + puntoATratar.proponente);
		if (data.name.equals(puntoATratar.proponente)) {
			String holi = LoginController.servidor.addVotoPunto(data.name, "PROPONENTE A FAVOR", data.img);
			data.voto = "FAVOR";
			System.out.println("El proponente voto" + holi);
		} else {
			String holi = LoginController.servidor.addVotoPunto(data.name, "A FAVOR", data.img);
			data.voto = "FAVOR";
			System.out.println("Persona normal voto" + holi);
		}
		seVoto();
	}

	@FXML
	void onSalvo(ActionEvent event) throws NotBoundException, IOException {
		LoginController.servidor.addVoto(data.name, "VOTO SALVADO", data.img);
		data.voto = "SALVO";
		seVoto();
	}

	public Image convertirImg(byte[] bytes) throws IOException {
		ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
		Image img = new Image(bis);
		return img;
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		label_punto.setText(puntoATratar.num_punto);
		label_titulo.setText(puntoATratar.tema);
		lbl_proponente.setText(puntoATratar.proponente);

	}

}
