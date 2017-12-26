package application;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.rmi.NotBoundException;
import java.util.ResourceBundle;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;

import clases.Conexion;
import clases.Usuarios;
import clases.data;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class LoginController implements Initializable {
	Conexion conexion;
	@FXML
	private JFXTextField txt_username;
	@FXML
	private JFXPasswordField txt_password;
	@FXML
	private javafx.scene.control.Button closeButton;
	@FXML
	private javafx.scene.control.Button loginButton;
	@FXML
	private Label lbl_nombre;
	@FXML
	private Circle cirlogin;

	@FXML
	private ImageView logo;

	@FXML
	private void closeButtonAction() {
		// get a handle to the stage
		Stage stage = (Stage) closeButton.getScene().getWindow();
		// do what you have to do
		stage.close();

		System.exit(0);
	}

	public Image convertirImg(byte[] bytes) throws IOException {
		ByteArrayInputStream bis = new ByteArrayInputStream(bytes);

		Image img = new Image(bis);
		return img;
	}

	public void mostrarMesaje(String subtitulo) {

		try {
			data.header = "Aviso";
			data.cuerpo = subtitulo;

			Stage newStage = new Stage();
			AnchorPane pane;
			pane = (AnchorPane) FXMLLoader.load(getClass().getResource("VentanaDialogo.fxml"));
			
			Scene scene = new Scene(pane);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			newStage.setScene(scene);
			newStage.initStyle(StageStyle.UNDECORATED);
			newStage.show();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@FXML
	private void loginAction() throws IOException, NotBoundException {
		// get a handle to the stage
		if (txt_username.getLength() == 0) {
			txt_username.requestFocus();
			mostrarMesaje("Por favor ingrese su nombre de usuario");
		} else {
			if (txt_password.getLength() == 0) {
				txt_password.requestFocus();
				mostrarMesaje("Por Favor ingrese su contraceña");
			} else {
				conexion.establecerConexion();
				Usuarios user = Usuarios.login(conexion.getConnection(), txt_username.getText(), txt_password.getText());
				
				if (user != null) {
					if (!user.getCargo().equals("Administrador")) {
						mostrarMesaje("sus credenciales no son de un administrador");
						txt_username.requestFocus();
					} else if(user.getIsLooged()==1) {
						mostrarMesaje("este usuario ya esta logeado en el sistema");
					}else {
						data.username=txt_username.getText();
						data.name = user.getNombre();
						data.tipo_user=user.getCargo();
						Stage actualStage = (Stage) closeButton.getScene().getWindow();
						// do what you have to do
						actualStage.close();

						Stage newStage = new Stage();
						FXMLLoader loader = new FXMLLoader(getClass().getResource("Inicio.fxml"));
						AnchorPane pane = loader.load();
						Scene scene = new Scene(pane);
						scene.getStylesheets().add(getClass().getResource("inicio.css").toExternalForm());
						
						// Pantalla completa
						Screen screen = Screen.getPrimary();
						Rectangle2D bounds = screen.getVisualBounds();

						newStage.setX(bounds.getMinX());
						newStage.setY(bounds.getMinY());
						newStage.setWidth(bounds.getWidth());
						newStage.setHeight(bounds.getHeight());

						newStage.setScene(scene);
						newStage.initStyle(StageStyle.UNDECORATED);
						newStage.show();
					}
				} else {
					mostrarMesaje("Nombre de usuario o contracena incorrectos");
				}
				conexion.cerrarConexion();
			}

		}

	}

	public void buttonPressed(KeyEvent e) throws IOException, NotBoundException {
		if (e.getCode().toString().equals("ENTER")) {
			loginAction();
		}
	}

	public void pressedExit(KeyEvent e) throws IOException, NotBoundException {
		if (e.getCode().toString().equals("ENTER")) {
			closeButtonAction();
		}
	}

	@FXML
	void getImage(KeyEvent event) throws IOException {
		conexion.establecerConexion();
		Usuarios user = Usuarios.obtener_img(conexion.getConnection(), txt_username.getText());
		if (user != null) {
			Image image = convertirImg(user.getImg());
			data.Imagen = image;
			System.out.println(user.getNombre());
			lbl_nombre.setVisible(true);
			lbl_nombre.setText(user.getNombre());
			System.out.println(user.getImg());
			if(user.getImg().length<15) {
				mostrarMesaje("la imagen no es valida x favor actualize los datos");
				System.out.println("la imagen no es valida x favor actualize los datos");
			}else {
				cirlogin.setFill(new ImagePattern(image));
				cirlogin.setStroke(Color.SEAGREEN);
				cirlogin.setEffect(new DropShadow(+25d, 0d, +2d, Color.DARKGREEN));
			}
			
		} else {
			System.out.println("No se encontro esta imagen");
			File fi = new File("C:\\GIT\\GADM-VE-v01\\Servidor-VEM\\src\\imgs\\customer_person_people_woman_you_1627.png");
			Image i = new Image(fi.toURI().toString());
			cirlogin.setFill(new ImagePattern(i));
			cirlogin.setStroke(Color.WHITE);
			cirlogin.setEffect(new DropShadow(+25d, 0d, +2d, Color.WHITE));
		}
		conexion.cerrarConexion();
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		File fi = new File("C:\\GIT\\GADM-VE-v01\\Servidor-VEM\\src\\imgs\\customer_person_people_woman_you_1627.png");
		Image i = new Image(fi.toURI().toString());
		cirlogin.setFill(new ImagePattern(i));
		cirlogin.setStroke(Color.WHITE);
		cirlogin.setEffect(new DropShadow(+25d, 0d, +2d, Color.WHITE));
		
		
		conexion = new Conexion();
		// loginButton.setStyle("-fx-focus-color: gray;");
		File f = new File("C:\\GIT\\GADM-VE-v01\\Servidor-VEM\\src\\imgs\\logomanta.png");
		Image im = new Image(f.toURI().toString());
		logo.setImage(im);

	}

}
