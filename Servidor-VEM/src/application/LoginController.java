package application;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;

import clases.data;
import gad.manta.common.IServidor;
import gad.manta.common.Usuario;
import gad.manta.common.data_configuracion;
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
	public static IServidor servidor;
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
		try {
			Main.registry.unbind("VotoE");
			//UnicastRemoteObject.unexportObject(servidor, true);
		} catch (RemoteException | NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
		System.exit(0);
	}

	public Image convertirImg(byte[] bytes) throws IOException {
		ByteArrayInputStream bis = new ByteArrayInputStream(bytes);

		Image img = new Image(bis);
		return img;
	}

	@FXML
	void getImage(KeyEvent event) {
		try {
			Usuario user = servidor.obtener_img(txt_username.getText());
			if (user != null) {
				Image im = convertirImg(user.getImg());
				data.Imagen = im;
				System.out.println(user.getNombre());
				lbl_nombre.setVisible(true);
				lbl_nombre.setText(user.getNombre());
				cirlogin.setFill(new ImagePattern(im));
				cirlogin.setStroke(Color.SEAGREEN);
				cirlogin.setEffect(new DropShadow(+25d, 0d, +2d, Color.DARKGREEN));
			} else {
				System.out.println("No se encontro esta imagen");
			}

		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@FXML
	private void loginAction() throws IOException, NotBoundException {
		// get a handle to the stage

		Stage actualStage = (Stage) closeButton.getScene().getWindow();
		// do what you have to do
		actualStage.close();

		Stage newStage = new Stage();
		FXMLLoader loader = new FXMLLoader(getClass().getResource("Inicio.fxml"));
		String username = servidor.login(txt_username.getText(), txt_password.getText());
		data.name = username;

		if (username != null) {
			
			AnchorPane pane = loader.load();
			Scene scene = new Scene(pane);

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
		} else {
			System.out.println("Usuario incorrecto");
		}
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		File f = new File("C:\\GIT\\GADM-VE-v01\\Servidor-VEM\\src\\imgs\\logomanta.png");
		Image im = new Image(f.toURI().toString());
		logo.setImage(im);
		try {
			servidor = (IServidor) Naming.lookup("rmi://"+ data_configuracion.ip_rmi + "/VotoE");
		} catch (MalformedURLException | RemoteException | NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		/*txt_username.setText("secretaria");
		txt_password.setText("1234");*/

	}

}
