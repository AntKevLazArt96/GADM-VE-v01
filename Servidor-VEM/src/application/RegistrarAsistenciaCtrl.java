package application;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URL;
import java.rmi.RemoteException;
import java.sql.Date;
import java.util.Calendar;
import java.util.List;
import java.util.ResourceBundle;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import clases.data;
import gad.manta.common.Conexion;
import gad.manta.common.Quorum;
import gad.manta.common.Sesion;
import gad.manta.common.Usuario;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;


public class RegistrarAsistenciaCtrl implements Initializable {
	private Conexion conexion;
	
	@FXML
	public AnchorPane panelRegistrar;
	
	
	@FXML
	private Label lblpresentes;

	@FXML
	private Label lblausentes;
	@FXML
	private JFXButton btnIniVoto;

	@FXML
	private JFXButton btnasistencia;

	@FXML
	private ImageView img2;

	@FXML
	private ImageView img1;

	@FXML
	private ImageView img3;

	@FXML
	private ImageView img4;

	@FXML
	private ImageView img5;

	@FXML
	private ImageView img6;

	@FXML
	private Label user1;

	@FXML
	private Label status1;

	@FXML
	private Label user2;

	@FXML
	private Label user3;

	@FXML
	private Label user4;

	@FXML
	private Label user5;

	@FXML
	private Label user6;

	@FXML
	private Label status3;

	@FXML
	private Label status5;

	@FXML
	private Label status6;

	@FXML
	private Label status2;

	@FXML
	private Label status4;

	@FXML
	private ImageView img8;

	@FXML
	private ImageView img7;

	@FXML
	private ImageView img9;

	@FXML
	private ImageView img10;

	@FXML
	private ImageView img11;

	@FXML
	private ImageView img12;

	@FXML
	private Label user7;

	@FXML
	private Label status7;

	@FXML
	private Label user8;

	@FXML
	private Label user9;

	@FXML
	private Label user10;

	@FXML
	private Label user11;

	@FXML
	private Label user12;

	@FXML
	private Label status8;

	@FXML
	private Label status9;

	@FXML
	private Label status11;

	@FXML
	private Label status10;

	@FXML
	private Label estatus12;
	@FXML
	private JFXTextArea txtCumple;

	// meotodo para convertir la la imagen
	public Image convertirImg(byte[] bytes) throws IOException {
		ByteArrayInputStream bis = new ByteArrayInputStream(bytes);

		Image img = new Image(bis);
		return img;
	}

	int idquorum = 0;

	@FXML
	void IniAsistencia(ActionEvent event) {
		btnasistencia.setVisible(false);
		Date fechaActual = new Date(Calendar.getInstance().getTime().getTime());
		conexion = new Conexion();
		conexion.establecerConexion();
		Quorum q = new Quorum(fechaActual);
		idquorum = q.guardarRegistro(conexion.getConnection());

		Sesion s = new Sesion(idquorum, data.convocatoria_sesion);
		s.addQuorum(conexion.getConnection());
		conexion.cerrarConexion();
		System.out.println("Se ha iniciado la asistencia");
		// PENDIENTE AQUI SE TIENE Q INICAR EL LOGIN DE LOS CONCEJALES
	}

	@FXML
	void IniVotoAction(ActionEvent event) throws IOException {
		try {

			FXMLLoader loader = new FXMLLoader(getClass().getResource("PrincipalSecretaria.fxml"));
			AnchorPane registrar = (AnchorPane) loader.load();

			panelRegistrar.getChildren().setAll(registrar);

			// llamamos al controllador con todos sus componentes
			// c = loader.getController();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		

		try {
			
			List<Usuario> lista = LoginController.servidor.asistenciaUsuarios(data.idquorum);
			int presentes = LoginController.servidor.presentes();
			
			lblpresentes.setText(String.valueOf(presentes));

			lblausentes.setText(String.valueOf(lista.size() - presentes));

			for (int i = 0; i < lista.size(); i++) {
				if (i == 0) {
					img1.setVisible(true);
					img1.setImage(convertirImg(lista.get(i).getImg()));
					user1.setVisible(true);
					user1.setText(lista.get(i).getNombre());
					status1.setText(lista.get(i).getStatus());
					status1.setVisible(true);
					if (lista.get(i).getStatus().equals("AUSENTE")) {
						status1.setStyle("-fx-text-fill: #F44336;");
					}
				}

				if (i == 1) {
					img2.setImage(convertirImg(lista.get(i).getImg()));
					img2.setVisible(true);
					user2.setText(lista.get(i).getNombre());
					user2.setVisible(true);
					status2.setText(lista.get(i).getStatus());
					status2.setVisible(true);
					if (lista.get(i).getStatus().equals("AUSENTE")) {
						status2.setStyle("-fx-text-fill: #F44336;");
					}

				}

				if (i == 2) {
					img3.setImage(convertirImg(lista.get(i).getImg()));
					img3.setVisible(true);
					user3.setText(lista.get(i).getNombre());
					user3.setVisible(true);
					status3.setText(lista.get(i).getStatus());
					status3.setVisible(true);
					if (lista.get(i).getStatus().equals("AUSENTE")) {
						status3.setStyle("-fx-text-fill: #F44336;");
					}

				}
				if (i == 3) {
					img4.setImage(convertirImg(lista.get(i).getImg()));
					img4.setVisible(true);
					user4.setText(lista.get(i).getNombre());
					user4.setVisible(true);
					status4.setText(lista.get(i).getStatus());
					status4.setVisible(true);
					if (lista.get(i).getStatus().equals("AUSENTE")) {
						status4.setStyle("-fx-text-fill: #F44336;");
					}

				}
				if (i == 4) {
					img5.setImage(convertirImg(lista.get(i).getImg()));
					img5.setVisible(true);
					user5.setText(lista.get(i).getNombre());
					user5.setVisible(true);
					status5.setText(lista.get(i).getStatus());
					status5.setVisible(true);
					if (lista.get(i).getStatus().equals("AUSENTE")) {
						status5.setStyle("-fx-text-fill: #F44336;");
					}
				}
				if (i == 5) {
					img6.setImage(convertirImg(lista.get(i).getImg()));
					img6.setVisible(true);
					user6.setText(lista.get(i).getNombre());
					user6.setVisible(true);
					status6.setText(lista.get(i).getStatus());
					status6.setVisible(true);
					if (lista.get(i).getStatus().equals("AUSENTE")) {
						status6.setStyle("-fx-text-fill: #F44336;");
					}
				}
				if (i == 6) {
					img7.setImage(convertirImg(lista.get(i).getImg()));
					img7.setVisible(true);
					user7.setText(lista.get(i).getNombre());
					user7.setVisible(true);
					status7.setText(lista.get(i).getStatus());
					status7.setVisible(true);
					if (lista.get(i).getStatus().equals("AUSENTE")) {
						status7.setStyle("-fx-text-fill: #F44336;");
					}
				}
				if (i == 7) {
					img8.setImage(convertirImg(lista.get(i).getImg()));
					img8.setVisible(true);
					user8.setText(lista.get(i).getNombre());
					user8.setVisible(true);
					status8.setText(lista.get(i).getStatus());
					status8.setVisible(true);
					if (lista.get(i).getStatus().equals("AUSENTE")) {
						status8.setStyle("-fx-text-fill: #F44336;");
					}
				}
				if (i == 8) {
					img9.setImage(convertirImg(lista.get(i).getImg()));
					img9.setVisible(true);
					user9.setText(lista.get(i).getNombre());
					user9.setVisible(true);
					status9.setText(lista.get(i).getStatus());
					status9.setVisible(true);
					if (lista.get(i).getStatus().equals("AUSENTE")) {
						status9.setStyle("-fx-text-fill: #F44336;");
					}
				}
				if (i == 9) {
					img10.setImage(convertirImg(lista.get(i).getImg()));
					img10.setVisible(true);
					user10.setText(lista.get(i).getNombre());
					user10.setVisible(true);
					status10.setText(lista.get(i).getStatus());
					status10.setVisible(true);
					if (lista.get(i).getStatus().equals("AUSENTE")) {
						status10.setStyle("-fx-text-fill: #F44336;");
					}
				}
				if (i == 10) {
					img11.setImage(convertirImg(lista.get(i).getImg()));
					img11.setVisible(true);
					user11.setText(lista.get(i).getNombre());
					user11.setVisible(true);
					status11.setText(lista.get(i).getStatus());
					status11.setVisible(true);
					if (lista.get(i).getStatus().equals("AUSENTE")) {
						status11.setStyle("-fx-text-fill: #F44336;");
					}
				}
				if (i == 11) {
					img12.setImage(convertirImg(lista.get(i).getImg()));
					img12.setVisible(true);
					user12.setText(lista.get(i).getNombre());
					user12.setVisible(true);
					estatus12.setText(lista.get(i).getStatus());
					estatus12.setVisible(true);
					if (lista.get(i).getStatus().equals("AUSENTE")) {
						estatus12.setStyle("-fx-text-fill: #F44336;");
					}
				}

				if (lista.size() >= 0) {
					txtCumple.setText("Cumple con el mínimo de miembros para inicar la sesión");

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


}
