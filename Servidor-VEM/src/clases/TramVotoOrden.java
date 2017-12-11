package clases;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.rmi.RemoteException;
import java.util.List;
import org.json.simple.JSONObject;
import application.InicioVotoOrdenCtrl;
import application.LoginController;
import application.PantallaPrincipalCtrl;
import gad.manta.common.Usuario;
import gad.manta.common.Voto;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class TramVotoOrden {

	

	public static InicioVotoOrdenCtrl ivo;

	public void tramiteVotoOrden() {
		try {
			// lista que devuelve todos los votantes con sus votos
			List<Voto> lista = LoginController.servidor.votantes();

			List<Voto> aprueba = LoginController.servidor.votosAprobados();
			List<Voto> rechaza = LoginController.servidor.votosRechazados();

			List<Usuario> quorum = LoginController.servidor.consultaQuorum();

			ivo.lblAprobado.setText(String.valueOf(aprueba.size()));
			ivo.lblRechazado.setText(String.valueOf(rechaza.size()));
			
			System.out.println("El tamano del quorum es "+quorum.size());
			System.out.println("El tamano de la lista es"+lista.size());
			ivo.lblEspera.setText(String.valueOf(quorum.size() - lista.size()));

			// reiniciando los usuarios
			if (lista.size() == 0) {
				ivo.img1.setVisible(false);
				ivo.img2.setVisible(false);
				ivo.img3.setVisible(false);
				ivo.img4.setVisible(false);
				ivo.img5.setVisible(false);
				ivo.img6.setVisible(false);
				ivo.img7.setVisible(false);
				ivo.img8.setVisible(false);
				ivo.img9.setVisible(false);
				ivo.img10.setVisible(false);
				ivo.img11.setVisible(false);
				ivo.img12.setVisible(false);
				ivo.user1.setVisible(false);
				ivo.user2.setVisible(false);
				ivo.user3.setVisible(false);
				ivo.user4.setVisible(false);
				ivo.user5.setVisible(false);
				ivo.user6.setVisible(false);
				ivo.user7.setVisible(false);
				ivo.user8.setVisible(false);
				ivo.user9.setVisible(false);
				ivo.user10.setVisible(false);
				ivo.user11.setVisible(false);
				ivo.user12.setVisible(false);
				ivo.status1.setVisible(false);
				ivo.status2.setVisible(false);
				ivo.status3.setVisible(false);
				ivo.status4.setVisible(false);
				ivo.status5.setVisible(false);
				ivo.status6.setVisible(false);
				ivo.status7.setVisible(false);
				ivo.status8.setVisible(false);
				ivo.status9.setVisible(false);
				ivo.status10.setVisible(false);
				ivo.status11.setVisible(false);
				ivo.estatus12.setVisible(false);
				ivo.status1.setStyle("-fx-text-fill: #4caf50;");
				ivo.status2.setStyle("-fx-text-fill: #4caf50;");
				ivo.status3.setStyle("-fx-text-fill: #4caf50;");
				ivo.status4.setStyle("-fx-text-fill: #4caf50;");
				ivo.status5.setStyle("-fx-text-fill: #4caf50;");
				ivo.status6.setStyle("-fx-text-fill: #4caf50;");
				ivo.status7.setStyle("-fx-text-fill: #4caf50;");
				ivo.status8.setStyle("-fx-text-fill: #4caf50;");
				ivo.status9.setStyle("-fx-text-fill: #4caf50;");
				ivo.status10.setStyle("-fx-text-fill: #4caf50;");
				ivo.status11.setStyle("-fx-text-fill: #4caf50;");
				ivo.estatus12.setStyle("-fx-text-fill: #4caf50;");
			}

			for (int i = 0; i < lista.size(); i++) {

				if (i == 0) {
					ivo.img1.setVisible(true);
					ivo.img1.setImage(convertirImg(lista.get(i).getImg()));
					ivo.user1.setVisible(true);
					ivo.user1.setText(lista.get(i).getNombre());
					ivo.status1.setText(lista.get(i).getVoto());
					ivo.status1.setVisible(true);
					if (lista.get(i).getVoto().equals("RECHAZADO")) {
						ivo.status1.setStyle("-fx-text-fill: red;");
					}
				}
				if (i == 1) {
					ivo.img2.setImage(convertirImg(lista.get(i).getImg()));
					ivo.img2.setVisible(true);
					ivo.user2.setText(lista.get(i).getNombre());
					ivo.user2.setVisible(true);
					ivo.status2.setText(lista.get(i).getVoto());
					ivo.status2.setVisible(true);
					if (lista.get(i).getVoto().equals("RECHAZADO")) {
						ivo.status2.setStyle("-fx-text-fill: red;");
					}
				}
				if (i == 2) {
					ivo.img3.setImage(convertirImg(lista.get(i).getImg()));
					ivo.img3.setVisible(true);
					ivo.user3.setText(lista.get(i).getNombre());
					ivo.user3.setVisible(true);
					ivo.status3.setText(lista.get(i).getVoto());
					ivo.status3.setVisible(true);
					if (lista.get(i).getVoto().equals("RECHAZADO")) {
						ivo.status3.setStyle("-fx-text-fill: red;");
					}
				}
				if (i == 3) {
					ivo.img4.setImage(convertirImg(lista.get(i).getImg()));
					ivo.img4.setVisible(true);
					ivo.user4.setText(lista.get(i).getNombre());
					ivo.user4.setVisible(true);
					ivo.status4.setText(lista.get(i).getVoto());
					ivo.status4.setVisible(true);
					if (lista.get(i).getVoto().equals("RECHAZADO")) {
						ivo.status4.setStyle("-fx-text-fill: red;");
					}
				}
				if (i == 4) {
					ivo.img5.setImage(convertirImg(lista.get(i).getImg()));
					ivo.img5.setVisible(true);
					ivo.user5.setText(lista.get(i).getNombre());
					ivo.user5.setVisible(true);
					ivo.status5.setText(lista.get(i).getVoto());
					ivo.status5.setVisible(true);
					if (lista.get(i).getVoto().equals("RECHAZADO")) {
						ivo.status5.setStyle("-fx-text-fill: red;");
					}
				}
				if (i == 5) {
					ivo.img6.setImage(convertirImg(lista.get(i).getImg()));
					ivo.img6.setVisible(true);
					ivo.user6.setText(lista.get(i).getNombre());
					ivo.user6.setVisible(true);
					ivo.status6.setText(lista.get(i).getVoto());
					ivo.status6.setVisible(true);
					if (lista.get(i).getVoto().equals("RECHAZADO")) {
						ivo.status6.setStyle("-fx-text-fill: red;");
					}
				}
				if (i == 6) {
					ivo.img7.setImage(convertirImg(lista.get(i).getImg()));
					ivo.img7.setVisible(true);
					ivo.user7.setText(lista.get(i).getNombre());
					ivo.user7.setVisible(true);
					ivo.status7.setText(lista.get(i).getVoto());
					ivo.status7.setVisible(true);
					if (lista.get(i).getVoto().equals("RECHAZADO")) {
						ivo.status7.setStyle("-fx-text-fill: red;");
					}
				}
				if (i == 7) {
					ivo.img8.setImage(convertirImg(lista.get(i).getImg()));
					ivo.img8.setVisible(true);
					ivo.user8.setText(lista.get(i).getNombre());
					ivo.user8.setVisible(true);
					ivo.status8.setText(lista.get(i).getVoto());
					ivo.status8.setVisible(true);
					if (lista.get(i).getVoto().equals("RECHAZADO")) {
						ivo.status8.setStyle("-fx-text-fill: red;");
					}
				}
				if (i == 8) {
					ivo.img9.setImage(convertirImg(lista.get(i).getImg()));
					ivo.img9.setVisible(true);
					ivo.user9.setText(lista.get(i).getNombre());
					ivo.user9.setVisible(true);
					ivo.status9.setText(lista.get(i).getVoto());
					ivo.status9.setVisible(true);
					if (lista.get(i).getVoto().equals("RECHAZADO")) {
						ivo.status9.setStyle("-fx-text-fill: red;");
					}
				}
				if (i == 9) {
					ivo.img10.setImage(convertirImg(lista.get(i).getImg()));
					ivo.img10.setVisible(true);
					ivo.user10.setText(lista.get(i).getNombre());
					ivo.user10.setVisible(true);
					ivo.status10.setText(lista.get(i).getVoto());
					ivo.status10.setVisible(true);
					if (lista.get(i).getVoto().equals("RECHAZADO")) {
						ivo.status10.setStyle("-fx-text-fill: red;");
					}
				}
				if (i == 10) {
					ivo.img11.setImage(convertirImg(lista.get(i).getImg()));
					ivo.img11.setVisible(true);
					ivo.user11.setText(lista.get(i).getNombre());
					ivo.user11.setVisible(true);
					ivo.status11.setText(lista.get(i).getVoto());
					ivo.status11.setVisible(true);
					if (lista.get(i).getVoto().equals("RECHAZADO")) {
						ivo.status11.setStyle("-fx-text-fill: red;");
					}
				}
				if (i == 11) {
					ivo.img12.setImage(convertirImg(lista.get(i).getImg()));
					ivo.img12.setVisible(true);
					ivo.user12.setText(lista.get(i).getNombre());
					ivo.user12.setVisible(true);
					ivo.estatus12.setText(lista.get(i).getVoto());
					ivo.estatus12.setVisible(true);
					if (lista.get(i).getVoto().equals("RECHAZADO")) {
						ivo.estatus12.setStyle("-fx-text-fill: red;");
					}
				}

				if (lista.size() >= 0) {
					ivo.btn_finVoto.setDisable(false);
					ivo.btn_reVoto.setDisable(false);
				}

			}

		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	public void reiniciarVotoOrden() throws IOException {
		try {
			String message = LoginController.servidor.limpiarVotoOrden();
			System.out.println(message);
			data.header = "Votos Reiniciados";
			data.cuerpo = message;
			try {
				// String json = "{" + " 'name' : '" + data.name + "', 'message' : '" + msg +
				// "'" + "}";

				JSONObject js = new JSONObject();
				js.put("name", "REINICIAR");

				String json = js.toJSONString();

				System.out.println("Se envio:" + json);
				PantallaPrincipalCtrl.dos.writeUTF(json);

			} catch (IOException E) {
				E.printStackTrace();
			}
			Stage newStage = new Stage();
			AnchorPane pane = (AnchorPane) FXMLLoader.load(getClass().getResource("VentanaDialogo.fxml"));
			Scene scene = new Scene(pane);
			newStage.setScene(scene);
			newStage.initStyle(StageStyle.UNDECORATED);
			newStage.show();

		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ivo.btn_finVoto.setDisable(true);
		ivo.btn_reVoto.setDisable(true);
	}

	// meotodo para convertir la la imagen
	public Image convertirImg(byte[] bytes) throws IOException {
		ByteArrayInputStream bis = new ByteArrayInputStream(bytes);

		Image img = new Image(bis);
		return img;
	}

	@SuppressWarnings("unchecked")
	public void iniciarVotacion() {
		try {

			// String json = "{" + " 'name' : '" + data.name + "', 'message' : '" + msg +
			// "'" + "}";

			JSONObject js = new JSONObject();
			js.put("name", "InicioVotoOrden");

			String json = js.toJSONString();

			System.out.println("Se envio:" + json);

			PantallaPrincipalCtrl.dos.writeUTF(json);

		} catch (IOException E) {
			E.printStackTrace();
		}

	}
}
