package application;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;
import com.jfoenix.controls.JFXButton;
import clases.data;
import gad.manta.common.Conexion;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class InicioController implements Initializable {

	@FXML
	private ImageView logo;
	@FXML
	private ImageView logo2;
	@FXML
	private JFXButton inicioButton;

	@FXML
	private JFXButton sesionButton;

	@FXML
	private JFXButton buttonControlPane;

	@FXML
	private JFXButton closeButton;

	@FXML
	public AnchorPane panel;

	@FXML
	private Label txt_username;
	@FXML
	private ImageView img_secretaria;

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		Conexion con = new Conexion();

		try {
			con.establecerConexion();
			Connection db = con.getConnection();
			PreparedStatement st1 = db.prepareStatement("update user_ve set isLogged=1 where username_user=?");
			st1.setString(1, data.name);
			st1.execute();
			con.cerrarConexion();

		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		File f = new File("C:\\GIT\\GADM-VE-v01\\Servidor-VEM\\src\\imgs\\logomanta.png");
		Image im = new Image(f.toURI().toString());
		logo.setImage(im);

		File f2 = new File("C:\\GIT\\GADM-VE-v01\\Servidor-VEM\\src\\imgs\\GADM-de-Manta-01-01.png");
		Image im2 = new Image(f2.toURI().toString());
		logo2.setImage(im2);

		img_secretaria.setImage(data.Imagen);
		txt_username.setText(data.name);
		try {
			AnchorPane pane = (AnchorPane) FXMLLoader.load(getClass().getResource("SubInicio.fxml"));
			panel.getChildren().setAll(pane);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@FXML
	void closeButtonAction(ActionEvent event) throws IOException {
		Stage actualStage = (Stage) closeButton.getScene().getWindow();
		// do what you have to do
		actualStage.close();
		LoginController.servidor.cerrarSesion(data.username);
		Stage newStage = new Stage();
		AnchorPane pane = (AnchorPane) FXMLLoader.load(getClass().getResource("login.fxml"));
		Scene scene = new Scene(pane);

		newStage.setScene(scene);
		newStage.initStyle(StageStyle.UNDECORATED);
		newStage.show();
	}

	@FXML
	void inicioButtonAction(ActionEvent event) {
		try {
			AnchorPane pane = (AnchorPane) FXMLLoader.load(getClass().getResource("SubInicio.fxml"));
			panel.getChildren().setAll(pane);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	@FXML
	void sessionButtonAction(ActionEvent event) {
		try {
			AnchorPane pane = (AnchorPane) FXMLLoader.load(getClass().getResource("SubSesiones.fxml"));
			pane.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			panel.getChildren().setAll(pane);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
