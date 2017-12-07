package cliente;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.List;
import java.util.ResourceBundle;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import com.jfoenix.controls.JFXButton;
import gad.manta.common.Pdf;
import gad.manta.common.Sesion;
import gad.manta.common.Usuario;
import gad.manta.common.data_configuracion;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class PantallaPrincipalCtrl implements Initializable {

	@FXML
	private JFXButton btn_voz;

	@FXML
	private Label label_convocatoria;

	@FXML
	private Circle cirlogin;

	@FXML
	private Label lbl_nombre;

	@FXML
	private AnchorPane contenedor;

	Sesion sesion;

	// variables estaticas para socket
	public static Thread th2;
	Socket sock;
	public static DataOutputStream dos;
	DataInputStream dis;
	// llamando a los controladores de las clases
	ClienteSesionCtrl c;

	// inicializamos socket
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public PantallaPrincipalCtrl() {
		try {
			System.out.println(data_configuracion.ip);
			sock = new Socket(data_configuracion.ip, data_configuracion.port);
			dos = new DataOutputStream(sock.getOutputStream());
			dis = new DataInputStream(sock.getInputStream());

			dos.writeUTF(data.name);
			/*
			 * This Thread let the client recieve the message from the server for any time;
			 */
			th2 = new Thread(() -> {
				try {

					JSONParser parser = new JSONParser();

					while (true) {
						String newMsgJson = dis.readUTF();

						System.out.println("RE : " + newMsgJson);
						Mensage newMsg = new Mensage();

						Object obj = parser.parse(newMsgJson);
						JSONObject msg = (JSONObject) obj;

						newMsg.setName((String) msg.get("name"));
						newMsg.setMessage((String) msg.get("status"));
						Platform.runLater(new Runnable() {
							@Override
							public void run() {
								if (newMsg.getName().equals("InicioVotoOrden")) {

									try {
										FXMLLoader loader = new FXMLLoader(
												getClass().getResource("ClienteVotoOrden.fxml"));
										AnchorPane Presesion = (AnchorPane) loader.load();
										contenedor.getChildren().setAll(Presesion);

									} catch (IOException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}

								}
								if (newMsg.getName().equals("REINICIAR")) {
									try {
										FXMLLoader loader = new FXMLLoader(
												getClass().getResource("ClienteVotoOrden.fxml"));
										AnchorPane Presesion = (AnchorPane) loader.load();
										contenedor.getChildren().setAll(Presesion);

									} catch (IOException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
								}

								if (newMsg.getName().equals("InicioVoto")) {

									try {
										FXMLLoader loader = new FXMLLoader(getClass().getResource("ClienteVoto.fxml"));
										AnchorPane Presesion = (AnchorPane) loader.load();
										contenedor.getChildren().setAll(Presesion);

									} catch (IOException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}

								}
								if (newMsg.getName().equals("REINICIAR VOTOS")) {
									try {
										FXMLLoader loader = new FXMLLoader(getClass().getResource("ClienteVoto.fxml"));
										AnchorPane Presesion = (AnchorPane) loader.load();
										contenedor.getChildren().setAll(Presesion);

									} catch (IOException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
								}

								if (newMsg.getName().equals("REINICIAR1VOTO")) {
									try {
										FXMLLoader loader = new FXMLLoader(getClass().getResource("ClienteVoto.fxml"));
										AnchorPane Presesion = (AnchorPane) loader.load();
										contenedor.getChildren().setAll(Presesion);

									} catch (IOException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
								}

								if (newMsg.getName().contains("VOTO TERMINADO")) {

									try {
										FXMLLoader loader = new FXMLLoader(
												getClass().getResource("ClienteSesion.fxml"));
										AnchorPane Presesion = (AnchorPane) loader.load();
										contenedor.getChildren().setAll(Presesion);
										// obtengo el controlador y se lo designo a la variable global c
										c = loader.getController();

									} catch (IOException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}

								}

								if (newMsg.getName().contains("NO SE VOTO")) {

									try {
										FXMLLoader loader = new FXMLLoader(
												getClass().getResource("ClienteSesion.fxml"));
										AnchorPane Presesion = (AnchorPane) loader.load();
										contenedor.getChildren().setAll(Presesion);
										// obtengo el controlador y se lo designo a la variable global c
										c = loader.getController();

									} catch (IOException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}

								}

								if (newMsg.getName().contains("TERMINO LA SESION")) {

									// logica para cerrar sesion
									Stage actualStage = (Stage) contenedor.getScene().getWindow();
									// do what you have to do
									actualStage.close();

									Stage newStage = new Stage();

									try {
										AnchorPane pane = (AnchorPane) FXMLLoader
												.load(getClass().getResource("VentanaDialogo.fxml"));
										Scene scene = new Scene(pane);

										newStage.setScene(scene);
										newStage.initStyle(StageStyle.UNDECORATED);
										newStage.setOnCloseRequest(e -> {
											// e.consume();
											PantallaPrincipalCtrl.th2.stop();
											System.exit(0);
										});
										newStage.show();

									} catch (IOException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}

								}

								if (newMsg.getName().contains("APROBADO")) {
									Alert mensaje = new Alert(AlertType.INFORMATION);
									mensaje.setTitle("Orden del d�a Aprobado");
									mensaje.setContentText(
											"LA orden del d�a fue aprobada ahora se procedera a mostrar ");
									mensaje.setHeaderText("Orden del d�a Aprobado");
									mensaje.show();

									try {
										FXMLLoader loader = new FXMLLoader(
												getClass().getResource("ClienteSesion.fxml"));
										AnchorPane Presesion = (AnchorPane) loader.load();
										contenedor.getChildren().setAll(Presesion);
										// obtengo el controlador y se lo designo a la variable global c
										c = loader.getController();

									} catch (IOException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}

								}

								if (newMsg.getName().contains("InicioSesion")) {
									String punto = (String) msg.get("punto");
									String titulo = (String) msg.get("titulo");
									String proponente = (String) msg.get("proponente");
									String id_pdf = (String) msg.get("id_pdf");
									puntoATratar.num_punto = punto;
									puntoATratar.tema = titulo;
									puntoATratar.proponente = proponente;
									puntoATratar.id_pdf = Integer.valueOf(id_pdf);

									c.lbl_punto.setText("" + punto);
									c.label_titulo.setText(titulo);
									c.label_proponente.setText(proponente);

									try {
										List<Pdf> lista_pdfs = LoginController.servidor
												.consultarPdfsPunto(Integer.valueOf(id_pdf));
										TableColumn pdf = new TableColumn("id");
										pdf.setMinWidth(500);
										pdf.setVisible(false);
										pdf.setCellValueFactory(new PropertyValueFactory<>("id_pdf"));

										TableColumn nombre = new TableColumn("Nombre");
										nombre.setMinWidth(700);
										nombre.setResizable(true);
										nombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));

										ObservableList<Pdf> datos_pdf = FXCollections.observableArrayList(lista_pdfs);

										c.table_pdf.getColumns().addAll(pdf, nombre);

										c.table_pdf.setItems(datos_pdf);

									} catch (NumberFormatException | RemoteException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}

								}

							}
						});

					}
				} catch (Exception E) {
					E.printStackTrace();
				}

			});

			th2.start();

		} catch (IOException E) {
			E.printStackTrace();
		}

	}

	public Image convertirImg(byte[] bytes) throws IOException {
		ByteArrayInputStream bis = new ByteArrayInputStream(bytes);

		Image img = new Image(bis);
		return img;
	}

	@FXML
	void pedirPalabra(ActionEvent event) {

	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		try {
			sesion = LoginController.servidor.consultarSesion();
			label_convocatoria.setText(sesion.getConvocatoria());
		} catch (RemoteException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}

		lbl_nombre.setText(data.name);

		try {
			Usuario user = LoginController.servidor.usuario(data.name);
			data.img = user.getImg();
			Image im = convertirImg(data.img);
			data.Imagen = im;
			cirlogin.setFill(new ImagePattern(im));
			cirlogin.setStroke(Color.SEAGREEN);
			cirlogin.setEffect(new DropShadow(+25d, 0d, +2d, Color.DARKGREEN));

			FXMLLoader loader = new FXMLLoader(getClass().getResource("ClientePreSesion.fxml"));
			AnchorPane Presesion = (AnchorPane) loader.load();
			contenedor.getChildren().setAll(Presesion);

			// llamamos al controllador con todos sus componentes
			// q = loader.getController();

		} catch (RemoteException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
