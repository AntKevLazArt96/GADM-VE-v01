package cliente;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;

import cliente.data;
import gad.manta.common.Config;
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
			if(user!=null) {
				Image im = convertirImg(user.getImg());
				data.Imagen = im;
				
				System.out.println(user.getNombre());
				lbl_nombre.setVisible(true);
				lbl_nombre.setText(user.getNombre());
				cirlogin.setFill(new ImagePattern(im));
				cirlogin.setStroke(Color.SEAGREEN);
				cirlogin.setEffect(new DropShadow(+25d, 0d, +2d, Color.DARKGREEN));
			}else{
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

		String login = servidor.login(txt_username.getText(), txt_password.getText());

		System.out.println("Clicked");

		data.name = login;
		System.out.println(login);
		AnchorPane pane = (AnchorPane) FXMLLoader.load(getClass().getResource("PantallaPrincipal.fxml"));
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
	}

	public static void configuraciones() {
		try {
			Config config = servidor.obtenerConfiguracion();
			data_configuracion.ip = config.getIp_s();
			data_configuracion.port = config.getPort_s();
			System.out.println(data_configuracion.ip);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void mostrarMesaje(String subtitulo) {

		try {
			data.header = "Aviso";
			data.cuerpo = subtitulo;
			Stage newStage = new Stage();
			AnchorPane pane;
			pane = (AnchorPane) FXMLLoader.load(getClass().getResource("VentanaDialogo.fxml"));
			Scene scene = new Scene(pane);
			newStage.setScene(scene);
			newStage.initStyle(StageStyle.UNDECORATED);
			newStage.show();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		File f = new File("C:\\GIT\\GADM-VE-v01\\Cliente\\src\\imgs\\logomanta.png");
		Image im = new Image(f.toURI().toString());
		logo.setImage(im);
		try {
			servidor = (IServidor) Naming.lookup("rmi://192.168.1.6/VotoE");
			if(servidor!=null) {
				configuraciones();
			}
			
		} catch (MalformedURLException | RemoteException | NotBoundException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			
				mostrarMesaje("No exixte conecion con el sevidor");
		
		}
		
		txt_username.setText("concejal1");
		txt_password.setText("1234");
	}

}
