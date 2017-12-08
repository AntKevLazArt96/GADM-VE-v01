package cliente;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.List;
import java.util.ResourceBundle;
import com.jfoenix.controls.JFXTextArea;
import gad.manta.common.Voto;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

public class ResumenVotoCtrl implements Initializable {

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
	public Label lbl_estado;


	// meotodo para convertir la la imagen
	public Image convertirImg(byte[] bytes) throws IOException {
		ByteArrayInputStream bis = new ByteArrayInputStream(bytes);

		Image img = new Image(bis);
		return img;
	}

	void limpiar() {
		img1.setVisible(false);
		img2.setVisible(false);
		img3.setVisible(false);
		img4.setVisible(false);
		img5.setVisible(false);
		img6.setVisible(false);
		img7.setVisible(false);
		img8.setVisible(false);
		img9.setVisible(false);
		img10.setVisible(false);
		img11.setVisible(false);
		img12.setVisible(false);
		user1.setVisible(false);
		user2.setVisible(false);
		user3.setVisible(false);
		user4.setVisible(false);
		user5.setVisible(false);
		user6.setVisible(false);
		user7.setVisible(false);
		user8.setVisible(false);
		user9.setVisible(false);
		user10.setVisible(false);
		user11.setVisible(false);
		user12.setVisible(false);
		status1.setVisible(false);
		status2.setVisible(false);
		status3.setVisible(false);
		status4.setVisible(false);
		status5.setVisible(false);
		status6.setVisible(false);
		status7.setVisible(false);
		status8.setVisible(false);
		status9.setVisible(false);
		status10.setVisible(false);
		status11.setVisible(false);
		estatus12.setVisible(false);
		status1.setStyle("-fx-text-fill: #4caf50;");
		status2.setStyle("-fx-text-fill: #4caf50;");
		status3.setStyle("-fx-text-fill: #4caf50;");
		status4.setStyle("-fx-text-fill: #4caf50;");
		status5.setStyle("-fx-text-fill: #4caf50;");
		status6.setStyle("-fx-text-fill: #4caf50;");
		status7.setStyle("-fx-text-fill: #4caf50;");
		status8.setStyle("-fx-text-fill: #4caf50;");
		status9.setStyle("-fx-text-fill: #4caf50;");
		status10.setStyle("-fx-text-fill: #4caf50;");
		status11.setStyle("-fx-text-fill: #4caf50;");
		estatus12.setStyle("-fx-text-fill: #4caf50;");
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		label_titulo.setText(puntoATratar.tema);
		lbl_punto.setText(puntoATratar.num_punto);
		lbl_proponente.setText(puntoATratar.proponente);
		limpiar();
		List<Voto> lista;
		List<Voto> favor;
		List<Voto> contra;
		List<Voto> salvados;
		List<Voto> blanco;
		try {
			// lista que devuelve todos los votantes con sus votos
			lista = LoginController.servidor.votantesPunto();
			favor = LoginController.servidor.votosAFavor();
			contra = LoginController.servidor.votosEnContra();
			salvados = LoginController.servidor.votosSalvados();
			blanco = LoginController.servidor.votosBlanco();
			System.out.println(lista.size());
			lblAFavor.setText(String.valueOf(favor.size()));
			lblEnContra.setText(String.valueOf(contra.size()));
			lblSalvoVoto.setText(String.valueOf(salvados.size()));
			lblBlanco.setText(String.valueOf(blanco.size()));

			for (int i = 0; i < lista.size(); i++) {
				if (i == 0) {
					img1.setVisible(true);
					img1.setImage(convertirImg(lista.get(i).getImg()));
					user1.setVisible(true);
					user1.setText(lista.get(i).getNombre());
					status1.setText(lista.get(i).getVoto());
					status1.setVisible(true);

					// cambiamos el color de acuerdo al voto
					if (lista.get(i).getVoto().equals("EN CONTRA")) {
						status1.setStyle("-fx-text-fill: #F44336;");
					}
					if (lista.get(i).getVoto().equals("VOTO SALVADO")) {
						status1.setStyle("-fx-text-fill: #B2EBF2;");

					}
					if (lista.get(i).getVoto().equals("EN BLANCO")) {
						status1.setStyle("-fx-text-fill: black;");
					}
				}
				if (i == 1) {
					img2.setImage(convertirImg(lista.get(i).getImg()));
					img2.setVisible(true);
					user2.setText(lista.get(i).getNombre());
					user2.setVisible(true);
					status2.setText(lista.get(i).getVoto());
					status2.setVisible(true);

					if (lista.get(i).getVoto().equals("EN CONTRA")) {
						status2.setStyle("-fx-text-fill: #F44336;");
					}
					if (lista.get(i).getVoto().equals("VOTO SALVADO")) {
						status2.setStyle("-fx-text-fill: #B2EBF2;");

					}
					if (lista.get(i).getVoto().equals("EN BLANCO")) {
						status2.setStyle("-fx-text-fill: black;");
					}
				}
				if (i == 2) {
					img3.setImage(convertirImg(lista.get(i).getImg()));
					img3.setVisible(true);
					user3.setText(lista.get(i).getNombre());
					user3.setVisible(true);
					status3.setText(lista.get(i).getVoto());
					status3.setVisible(true);

					if (lista.get(i).getVoto().equals("EN CONTRA")) {
						status3.setStyle("-fx-text-fill: #F44336;");
					}
					if (lista.get(i).getVoto().equals("VOTO SALVADO")) {
						status3.setStyle("-fx-text-fill: #B2EBF2;");

					}
					if (lista.get(i).getVoto().equals("EN BLANCO")) {
						status3.setStyle("-fx-text-fill: black;");
					}
				}
				if (i == 3) {
					img4.setImage(convertirImg(lista.get(i).getImg()));
					img4.setVisible(true);
					user4.setText(lista.get(i).getNombre());
					user4.setVisible(true);
					status4.setText(lista.get(i).getVoto());
					status4.setVisible(true);

					if (lista.get(i).getVoto().equals("EN CONTRA")) {
						status4.setStyle("-fx-text-fill: #F44336;");
					}
					if (lista.get(i).getVoto().equals("VOTO SALVADO")) {
						status4.setStyle("-fx-text-fill: #B2EBF2;");

					}
					if (lista.get(i).getVoto().equals("EN BLANCO")) {
						status4.setStyle("-fx-text-fill: black;");
					}
				}
				if (i == 4) {
					img5.setImage(convertirImg(lista.get(i).getImg()));
					img5.setVisible(true);
					user5.setText(lista.get(i).getNombre());
					user5.setVisible(true);
					status5.setText(lista.get(i).getVoto());
					status5.setVisible(true);

					if (lista.get(i).getVoto().equals("EN CONTRA")) {
						status5.setStyle("-fx-text-fill: #F44336;");
					}
					if (lista.get(i).getVoto().equals("VOTO SALVADO")) {
						status5.setStyle("-fx-text-fill: #B2EBF2;");

					}
					if (lista.get(i).getVoto().equals("EN BLANCO")) {
						status5.setStyle("-fx-text-fill: black;");
					}
				}
				if (i == 5) {
					img6.setImage(convertirImg(lista.get(i).getImg()));
					img6.setVisible(true);
					user6.setText(lista.get(i).getNombre());
					user6.setVisible(true);
					status6.setText(lista.get(i).getVoto());
					status6.setVisible(true);

					if (lista.get(i).getVoto().equals("EN CONTRA")) {
						status6.setStyle("-fx-text-fill: #F44336;");
					}
					if (lista.get(i).getVoto().equals("VOTO SALVADO")) {
						status6.setStyle("-fx-text-fill: #B2EBF2;");

					}
					if (lista.get(i).getVoto().equals("EN BLANCO")) {
						status6.setStyle("-fx-text-fill: black;");
					}
				}
				if (i == 6) {
					img7.setImage(convertirImg(lista.get(i).getImg()));
					img7.setVisible(true);
					user7.setText(lista.get(i).getNombre());
					user7.setVisible(true);
					status7.setText(lista.get(i).getVoto());
					status7.setVisible(true);

					if (lista.get(i).getVoto().equals("EN CONTRA")) {
						status7.setStyle("-fx-text-fill: #F44336;");
					}
					if (lista.get(i).getVoto().equals("VOTO SALVADO")) {
						status7.setStyle("-fx-text-fill: #B2EBF2;");

					}
					if (lista.get(i).getVoto().equals("EN BLANCO")) {
						status7.setStyle("-fx-text-fill: black;");
					}
				}
				if (i == 7) {
					img8.setImage(convertirImg(lista.get(i).getImg()));
					img8.setVisible(true);
					user8.setText(lista.get(i).getNombre());
					user8.setVisible(true);
					status8.setText(lista.get(i).getVoto());
					status8.setVisible(true);

					if (lista.get(i).getVoto().equals("EN CONTRA")) {
						status8.setStyle("-fx-text-fill: #F44336;");
					}
					if (lista.get(i).getVoto().equals("VOTO SALVADO")) {
						status8.setStyle("-fx-text-fill: #B2EBF2;");

					}
					if (lista.get(i).getVoto().equals("EN BLANCO")) {
						status8.setStyle("-fx-text-fill: black;");
					}
				}
				if (i == 8) {
					img9.setImage(convertirImg(lista.get(i).getImg()));
					img9.setVisible(true);
					user9.setText(lista.get(i).getNombre());
					user9.setVisible(true);
					status9.setText(lista.get(i).getVoto());
					status9.setVisible(true);

					if (lista.get(i).getVoto().equals("EN CONTRA")) {
						status9.setStyle("-fx-text-fill: #F44336;");
					}
					if (lista.get(i).getVoto().equals("VOTO SALVADO")) {
						status9.setStyle("-fx-text-fill: #B2EBF2;");

					}
					if (lista.get(i).getVoto().equals("EN BLANCO")) {
						status9.setStyle("-fx-text-fill: black;");
					}
				}
				if (i == 9) {
					img10.setImage(convertirImg(lista.get(i).getImg()));
					img10.setVisible(true);
					user10.setText(lista.get(i).getNombre());
					user10.setVisible(true);
					status10.setText(lista.get(i).getVoto());
					status10.setVisible(true);

					if (lista.get(i).getVoto().equals("EN CONTRA")) {
						status10.setStyle("-fx-text-fill: #F44336;");
					}
					if (lista.get(i).getVoto().equals("VOTO SALVADO")) {
						status10.setStyle("-fx-text-fill: #B2EBF2;");

					}
					if (lista.get(i).getVoto().equals("EN BLANCO")) {
						status10.setStyle("-fx-text-fill: black;");
					}
				}
				if (i == 10) {
					img11.setImage(convertirImg(lista.get(i).getImg()));
					img11.setVisible(true);
					user11.setText(lista.get(i).getNombre());
					user11.setVisible(true);
					status11.setText(lista.get(i).getVoto());
					status11.setVisible(true);

					if (lista.get(i).getVoto().equals("EN CONTRA")) {
						status11.setStyle("-fx-text-fill: #F44336;");
					}
					if (lista.get(i).getVoto().equals("VOTO SALVADO")) {
						status11.setStyle("-fx-text-fill: #B2EBF2;");

					}
					if (lista.get(i).getVoto().equals("EN BLANCO")) {
						status11.setStyle("-fx-text-fill: black;");
					}
				}
				if (i == 11) {
					img12.setImage(convertirImg(lista.get(i).getImg()));
					img12.setVisible(true);
					user12.setText(lista.get(i).getNombre());
					user12.setVisible(true);
					estatus12.setText(lista.get(i).getVoto());
					estatus12.setVisible(true);

					if (lista.get(i).getVoto().equals("EN CONTRA")) {
						estatus12.setStyle("-fx-text-fill: #F44336;");
					}
					if (lista.get(i).getVoto().equals("VOTO SALVADO")) {
						estatus12.setStyle("-fx-text-fill: #B2EBF2;");

					}
					if (lista.get(i).getVoto().equals("EN BLANCO")) {
						estatus12.setStyle("-fx-text-fill: black;");
					}
				}

				

			}

		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			LoginController.servidor.limpiarVoto();
			/*VotoResumen.si=0;
			VotoResumen.no=0;
			VotoResumen.blanco=0;
			VotoResumen.salvo=0;*/
			// Se inicio la votacion
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
