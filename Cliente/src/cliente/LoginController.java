package cliente;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import cliente.data;
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
		Connection db;
		try {
			System.out.println(data_configuracion.ipBaseDatos);
			db = DriverManager.getConnection(
					"jdbc:postgresql://" + data_configuracion.ipBaseDatos + "/" + data_configuracion.nombre_bd_postgres,
					data_configuracion.usu_db_postgres, data_configuracion.conta_usu_postgres);
			Statement st = db.createStatement();
			ResultSet resultado = st.executeQuery("select * from configuracion_ve where id_confi=1;");
			resultado.next();
			// rmi
			data_configuracion.ip_rmi = resultado.getString(2);
			data_configuracion.puerto_rmi = resultado.getInt(4);
			System.out.println(data_configuracion.ip_rmi);

			// bd
			data_configuracion.nombre_bd = resultado.getString(6);

			data_configuracion.usu_db = resultado.getString(7);

			data_configuracion.conta_usu = resultado.getString(8);

			// socket
			data_configuracion.ip = resultado.getString(3);
			data_configuracion.port = resultado.getInt(5);
			db.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		if (Main.cargar()) {
			data_configuracion.ipBaseDatos = Main.config.get(0);
			data_configuracion.puerto_postgres = Integer.valueOf(Main.config.get(1));
			data_configuracion.usu_db_postgres = Main.config.get(2);
			data_configuracion.conta_usu_postgres = Main.config.get(3);
			data_configuracion.nombre_bd_postgres = Main.config.get(4);
			/*
			 * txt_puerto.setText(ConfiguracionIniCtrl.config.get(1));
			 * txt_userdb.setText(ConfiguracionIniCtrl.config.get(2));
			 * txt_contradb.setText(ConfiguracionIniCtrl.config.get(3));
			 * txt_nombrebd.setText(ConfiguracionIniCtrl.config.get(4));
			 */
		}

		configuraciones();
		File f = new File("C:\\GIT\\GADM-VE-v01\\Cliente\\src\\imgs\\logomanta.png");
		Image im = new Image(f.toURI().toString());
		logo.setImage(im);
		try {
			System.out.println(data_configuracion.ip_rmi);
			servidor = (IServidor) Naming.lookup("rmi://" + data_configuracion.ip_rmi + "/VotoE");
		} catch (MalformedURLException | RemoteException | NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		/*
		 * txt_username.setText("concejal1"); txt_password.setText("1234");
		 */
	}

}
