package application;

import java.io.IOException;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.ResourceBundle;
import org.json.simple.JSONObject;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import clases.TramVoto;
import clases.VotoResumen;
import clases.data;
import clases.puntoATratar;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

public class InicioVotoCtrl implements Initializable {

	@FXML
	public JFXButton btn_finVoto;

	@FXML
	public JFXButton btn_reVoto;

	@FXML
	public AnchorPane panelInicioVoto;

	// imagenes
	@FXML
	public ImageView img1, img2, img3, img4, img5, img6, img7, img8, img9, img10, img11, img12;

	// nombre de usuarios
	@FXML
	public Label user1, user2, user3, user4, user5, user6, user7, user8, user9, user10, user11, user12;

	// Votacion de los usurios

	@FXML
	public Label status1, status2, status3, status4, status5, status6, status7, status8, status9, status10, status11,
			estatus12;

	// botones de reiniciar el voto individual

	@FXML
	public JFXButton btnRe1, btnRe2, btnRe3, btnRe4, btnRe5, btnRe6, btnRe7, btnRe8, btnRe9, btnRe10, btnRe11, btnRe12;

	@FXML
	public JFXTextArea label_titulo;

	@FXML
	public Label lbl_punto;

	@FXML
	public Label lbl_proponente;

	@FXML
	public Label lblAFavor;

	@FXML
	public Label lblEnContra;

	@FXML
	public Label lblBlanco;

	@FXML
	public Label lblSalvoVoto;

	@FXML
	public Label lblEspera;

	@SuppressWarnings("unchecked")
	@FXML
	void finVoto(ActionEvent event) throws IOException {

		try {
			//guardamos los votos al finalizar
			TramVoto t = new TramVoto();
			t.guardarVotos(puntoATratar.id_ordendia);
			
			
			try {

				// String json = "{" + " 'name' : '" + data.name + "', 'message' : '" + msg +
				// "'" + "}";

				JSONObject js = new JSONObject();
				js.put("name", "VOTO RESUMEN");

				String json = js.toJSONString();

				System.out.println("Se envio:" + json);

				PantallaPrincipalCtrl.dos.writeUTF(json);

			} catch (IOException E) {
				E.printStackTrace();
			}
			
			FXMLLoader loader = new FXMLLoader(getClass().getResource("RegistrarVoto.fxml"));
			AnchorPane quorum = (AnchorPane) loader.load();
			panelInicioVoto.getChildren().setAll(quorum);

		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		label_titulo.setText(puntoATratar.tema);
		lbl_punto.setText(puntoATratar.num_punto);
		lbl_proponente.setText(puntoATratar.proponente);
		btn_finVoto.setDisable(true);
		btn_reVoto.setDisable(true);

		try {
			LoginController.servidor.limpiarVoto();
			// Se inicio la votacion
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@FXML
	void reiniciarVoto(ActionEvent event) throws IOException {
		TramVoto t = new TramVoto();
		t.reiniciarVotos();
	}

	@FXML
	void re1(ActionEvent event) throws IOException {
		TramVoto t = new TramVoto(); 
		t.reiniciarVoto();
	}

	@FXML
	void re2(ActionEvent event) throws IOException {
		TramVoto t = new TramVoto(); 
		t.reiniciarVoto2();
	}

	@FXML
	void re3(ActionEvent event) throws IOException {
		TramVoto t = new TramVoto(); 
		t.reiniciarVoto3();
	}

	@FXML
	void re4(ActionEvent event) throws IOException {
		TramVoto t = new TramVoto(); 
		t.reiniciarVoto4();
	}

	@FXML
	void re5(ActionEvent event) throws IOException {
		TramVoto t = new TramVoto(); 
		t.reiniciarVoto5();
	}

	@FXML
	void re6(ActionEvent event) throws IOException {
		TramVoto t = new TramVoto(); 
		t.reiniciarVoto6();
	}

	@FXML
	void re7(ActionEvent event) throws IOException {
		TramVoto t = new TramVoto(); 
		t.reiniciarVoto7();
	}

	@FXML
	void re8(ActionEvent event) throws IOException {
		TramVoto t = new TramVoto(); 
		t.reiniciarVoto8();
	}

	@FXML
	void re9(ActionEvent event) throws IOException {
		TramVoto t = new TramVoto(); 
		t.reiniciarVoto9();
	}

	@FXML
	void re10(ActionEvent event) throws IOException {
		TramVoto t = new TramVoto(); 
		t.reiniciarVoto10();
	}

	@FXML
	void re11(ActionEvent event) throws IOException {
		TramVoto t = new TramVoto(); 
		t.reiniciarVoto11();
	}

	@FXML
	void re12(ActionEvent event) throws IOException {
		TramVoto t = new TramVoto(); 
		t.reiniciarVoto12();
	}

}
