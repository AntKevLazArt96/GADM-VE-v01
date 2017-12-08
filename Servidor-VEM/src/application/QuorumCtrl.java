package application;

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
import gad.manta.common.Asistencia;
import gad.manta.common.Conexion;
import gad.manta.common.Quorum;
import gad.manta.common.Sesion;
import gad.manta.common.Usuario;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;


public class QuorumCtrl implements Initializable {
	
	
	private Conexion conexion;

	@FXML
	public Label lblpresentes;

	@FXML
	public Label lblausentes;
	
	@FXML
	public AnchorPane panelQuorum;

	@FXML
	private JFXButton btnIniVoto;

	@FXML
	public JFXButton btnasistencia;

	// objetos del Quorum que se referenciaran en la pantalla principal
	@FXML
	public ImageView img1, img2, img3, img4, img5, img6, img7, img8, img9, img10, img11, img12;

	@FXML
	public Label user1, user2, user3, user4, user5, user6, user7, user8, user9, user10, user11, user12;

	@FXML
	public Label status1, status2, status3, status4, status5, status6, status7, status8, status9, status10, status11,
			estatus12;

	@FXML
	public JFXTextArea txtCumple;

	@FXML
	public JFXButton btn_finAsistencia;

	@FXML
	void IniAsistencia(ActionEvent event) throws IOException {
		//para abrir el ejecutable en los clientes
		Runtime.getRuntime().exec("cmd /c java -jar C:\\Cliente.jar");
		btn_finAsistencia.setVisible(true);
		btnasistencia.setVisible(false);
		Date fechaActual = new Date(Calendar.getInstance().getTime().getTime());
		// System.out.println(fechaActual);
		conexion = new Conexion();
		conexion.establecerConexion();
		Quorum q = new Quorum(fechaActual);
		data.idquorum = q.guardarRegistro(conexion.getConnection());
		System.out.println(data.convocatoria_sesion);
		Sesion s = new Sesion(data.idquorum, data.convocatoria_sesion);
		int d = s.addQuorum(conexion.getConnection());
		System.out.println(d);
		conexion.cerrarConexion();
		System.out.println("Se ha iniciado la asistencia");
		// Inicializo la asistencia de hoy
		try {
			List<Usuario> lista = LoginController.servidor.listaUsuarios();
			conexion.establecerConexion();
			for (int i = 0; i < lista.size(); i++) {
				Asistencia asiste = new Asistencia(lista.get(i).getId(), data.idquorum, "AUSENTE");
				asiste.guardarRegistro(conexion.getConnection());
			}
			conexion.cerrarConexion();

		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// designamos el tipo de tramite
		PantallaPrincipalCtrl.tramite = "quorum";

		// PENDIENTE AQUI SE TIENE Q INICAR EL LOGIN DE LOS CONCEJALES
	}

	@FXML
	void finAsistencia(ActionEvent event) throws IOException {

		try {

			FXMLLoader loader = new FXMLLoader(getClass().getResource("RegistrarAsistencia.fxml"));
			AnchorPane registrar = (AnchorPane) loader.load();

			panelQuorum.getChildren().setAll(registrar);

			// llamamos al controllador con todos sus componentes
			// c = loader.getController();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		
		

	}

}
