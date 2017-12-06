package application;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.Socket;
import java.net.URL;


import java.util.ResourceBundle;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.jfoenix.controls.JFXButton;

import clases.TramQuorum;
import clases.TramVoto;
import clases.TramVotoOrden;
import clases.data;
import gad.manta.common.Conexion;
import gad.manta.common.Sesion;
import gad.manta.common.Usuario;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;

public class PantallaPrincipalCtrl implements Initializable {
	public static Thread th1;
	Socket sock;
	public static DataOutputStream dos;
	DataInputStream dis;

	// variable para tomar lo que se esta haciendo
	public static String tramite;
	// llamando a los controlladores de los paneles

	public InicioVotoOrdenCtrl ivo;

	Conexion conexion;
	public Sesion sesion;

	public PantallaPrincipalCtrl() {

		try {

			// Iniciamos el servicio socket
			sock = new Socket(data.ip, data.port);
			dos = new DataOutputStream(sock.getOutputStream());
			dis = new DataInputStream(sock.getInputStream());
			// Enviamos al sistema el nombre de la secretaria
			dos.writeUTF(data.name);
			/*
			 * This Thread let the client recieve the message from the server for any time;
			 */
			th1 = new Thread(() -> {
				try {

					JSONParser parser = new JSONParser();

					while (true) {
						String newMsgJson = dis.readUTF();

						Usuario newMsg = new Usuario();

						Object obj = parser.parse(newMsgJson);
						JSONObject msg = (JSONObject) obj;

						newMsg.setNombre((String) msg.get("name"));
						newMsg.setStatus((String) msg.get("message"));

						Platform.runLater(new Runnable() {
							@Override
							public void run() {

								if (tramite != null) {
									if (tramite.equals("quorum")) {
										System.out.println("Se inicio el Tramite quorum");
										TramQuorum t = new TramQuorum();
										t.tramiteQuorum();
									}

									if (tramite.equals("votoOrden")) {
										System.out.println("Se inicio el Tramite Voto Orden");
										TramVotoOrden tvo = new TramVotoOrden();
										tvo.tramiteVotoOrden();
									}
									
									if (tramite.equals("voto")) {
										System.out.println("Se inicio el Tramite Voto");
										TramVoto tv = new TramVoto();
										tv.tramiteVoto();
									}
								} else {
									System.out.println("El tramite es null");
								}

							}
						});

					}
				} catch (Exception E) {
					E.printStackTrace();
				}
			});
			th1.start();
		} catch (IOException E) {
			E.printStackTrace();
		}
	}

	// controles para esta clase
	@FXML
	private JFXButton btn_voz;

	@FXML
	private Label label_convocatoria;

	@FXML
	private Circle cirlogin;

	@FXML
	private Label lbl_nombre;

	@FXML
	private AnchorPane panelvoz;

	@FXML
	public AnchorPane contenido;

	@FXML
	private JFXButton btn_fin;

	// Acciones para esta clase
	@FXML
	void finAction(ActionEvent event) {

	}

	@FXML
	void handleButtonAction(ActionEvent event) {

	}

	// se ejecuta cada vez que se inicia el programa
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		lbl_nombre.setText(data.name);
		cirlogin.setStroke(Color.SEAGREEN);
		File f = new File("C:\\librerias\\concejal1.png");
		Image im = new Image(f.toURI().toString());
		cirlogin.setFill(new ImagePattern(im));
		cirlogin.setEffect(new DropShadow(+25d, 0d, +2d, Color.DARKSEAGREEN));

		try {
			sesion = LoginController.servidor.consultarSesion();
			label_convocatoria.setText(sesion.getConvocatoria());

			FXMLLoader loader = new FXMLLoader(getClass().getResource("Quorum.fxml"));
			AnchorPane quorum = (AnchorPane) loader.load();
			contenido.getChildren().setAll(quorum);

			// llamamos al controllador con todos sus componentes
			TramQuorum.q = loader.getController();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
