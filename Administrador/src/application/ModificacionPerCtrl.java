package application;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;

import clases.Conexion;
import clases.Imagen;
import clases.Usuarios;
import clases.data;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.FileChooser.ExtensionFilter;

public class ModificacionPerCtrl implements Initializable {

	public static String origen = "";
	String ced_old;
	String username_old;
	public static String cedula = "";
	
	
	
	@FXML
	private JFXTextField txt_cedula;

	@FXML
	private JFXTextField txt_nombre;

	@FXML
	private JFXTextField txt_username;

	@FXML
	private JFXPasswordField txt_password;

	@FXML
	private JFXComboBox<String> cbx_cargo;
	ObservableList<String> tipo = FXCollections.observableArrayList("Administrador", "Concejal Principal",
			"Concejal Alterno", "Secretaria");

	@FXML
	private JFXButton btn_examinar;

	@FXML
	private ImageView lbl_foto;

	@FXML
	private JFXButton btn_guardar,btn_buscar_usu;
	@FXML
	private JFXButton btn_eliminar, btn_eliminar1;

	@FXML
	public AnchorPane panel, panelDatos;

	int id_img = 0;
	int id_usuario = 0;
	Conexion conexion = new Conexion();

	@FXML
	void onBuscarFoto(ActionEvent event) {
		try {
			FileChooser fc = new FileChooser();
			// fc.setInitialDirectory(new File("C:\\GIT\\GADM-VE-v01\\Servidor-VEM\\res"));
			fc.getExtensionFilters().addAll(new ExtensionFilter("PNG Files", "*.png"));
			File selectedf = fc.showOpenDialog(null);
			if (selectedf != null) {
				String path = selectedf.getAbsolutePath().toString();
				String file = path.substring(path.lastIndexOf('\\') + 1, path.lastIndexOf('.'));
				String exten = path.substring(path.lastIndexOf('.') + 1);
				System.out.println(file + ((int) (Math.random() * 10000) + 50) + "." + exten);
				String ruta = file + ((int) (Math.random() * 10000) + 50) + "." + exten;

				Imagen img = new Imagen(ruta, selectedf.getAbsolutePath().toString(), id_img);
				id_img = img.modificar_imagen();
				
				InputStream is = img.consultarImg(id_img);
				Image imgn = new Image(is);
				System.out.println(imgn);
				System.out.println(id_img);
				lbl_foto.setImage(imgn);
				btn_examinar.setVisible(false);
			}
		} catch (NullPointerException nl) {
			nl.printStackTrace();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	@FXML
	void eliminarImg(ActionEvent event) {
		conexion.establecerConexion();
		Imagen img = new Imagen();
		int resultado = img.eliminarImg(id_img, conexion.getConnection());
		conexion.cerrarConexion();
		if (resultado == 1) {
			imgBlanco();
		} else {
			System.out.println("Error en la consulta");
		}
	}

	private void limpiar() {
		txt_cedula.setText("");
		txt_nombre.setText("");
		txt_username.setText("");
		txt_password.setText("");
		cbx_cargo.setValue("");
		id_img = 0;
		imgBlanco();
	}

	private void bloquear() {
		panelDatos.setDisable(true);
	}

	private void desbloquear() {
		panelDatos.setDisable(false);
	}

	@FXML
	void onGuardar(ActionEvent event) throws SQLException {

		conexion.establecerConexion();
		// conecciï¿½n a la base de datos
		Connection db = conexion.getConnection();
		if (txt_cedula.getLength() == 0) {
			txt_cedula.requestFocus();
			mostrarMesaje("Falta ingresar el número de cédula");

		} else

		if (txt_nombre.getLength() == 0) {
			txt_nombre.requestFocus();
			mostrarMesaje("Falta ingresar el nombre del usuario");
		} else if (txt_username.getLength() == 0) {
			txt_username.requestFocus();
			mostrarMesaje("Falta ingresar el nombre de usuario");
		} else if (txt_password.getLength() == 0) {
			txt_password.requestFocus();
			mostrarMesaje("Falta ingresar la contraceña del usuario");
		} else if (cbx_cargo.getValue() == null) {
			cbx_cargo.requestFocus();
			mostrarMesaje("Falta ingresar el cargo del usuario");
		} else if (id_img == 0) {
			btn_examinar.requestFocus();
			mostrarMesaje("Falta ingresar la foto del usuario");
		} else {

			Usuarios user = new Usuarios(txt_cedula.getText(), cbx_cargo.getValue(), txt_nombre.getText(),
					txt_username.getText(), txt_password.getText(), id_img, "");
			int resultado = user.actualizarRegistro(db);

			if (resultado == 1) {
				limpiar();
				bloquear();
				mostrarMesaje("El usuario " + txt_nombre.getText() + " a sido modificado correctamente");
				if(origen.equals("update")) {
					try {
						AnchorPane pane = (AnchorPane) FXMLLoader.load(getClass().getResource("buscarPersonal.fxml"));
						panel.getChildren().setAll(pane);

					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			} else {
				mostrarMesaje("El usuario " + txt_nombre.getText() + " no se a podido modificar");
				if(origen.equals("update")) {
					try {
						AnchorPane pane = (AnchorPane) FXMLLoader.load(getClass().getResource("buscarPersonal.fxml"));
						panel.getChildren().setAll(pane);

					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}

		}

		conexion.cerrarConexion();
		// conexion.cerrarConexion();

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
	public void initialize(URL arg0, ResourceBundle arg1) {

		// txt_cedula.setText("123456787");
		cbx_cargo.setItems(tipo);
		conexion = new Conexion();
		if (origen.equals("update")) {
			btn_eliminar1.setText("");
			btn_eliminar1.setVisible(false);
			txt_cedula.setText(cedula);
			txt_cedula.setDisable(true);
			btn_buscar_usu.setDisable(true);
			try {
				buscar_usuario();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			btn_eliminar1.setText("");
			btn_eliminar1.setVisible(false);
			bloquear();
		}
	}

	private void imgBlanco() {
		btn_examinar.setVisible(true);
		InputStream is = new InputStream() {

			@Override
			public int read() throws IOException {
				// TODO Auto-generated method stub
				return 0;
			}
		};
		id_img = 0;
		lbl_foto.setImage(new Image(is));
	}

	@FXML
	void validar_cedula(KeyEvent e) {

		char car = e.getCharacter().charAt(0);
		if (!Character.isDigit(car)) {
			e.consume();
		}
		if (txt_cedula.getLength() == 10) {
			e.consume();
		}

	}

	@FXML
	void validar_nombre(KeyEvent e) {

		char car = e.getCharacter().charAt(0);
		if (!(Character.isDigit(car) || Character.isLetter(car) || Character.isSpaceChar(car))) {
			e.consume();
		}
		if (txt_nombre.getLength() == 100) {
			e.consume();
		}

	}

	@FXML
	void validar_usuario(KeyEvent e) {

		char car = e.getCharacter().charAt(0);
		if (!(Character.isDigit(car) || Character.isLetter(car))) {
			e.consume();
		}
		if (txt_username.getLength() == 30) {
			e.consume();
		}

	}

	@FXML
	void validar_contracena(KeyEvent e) {

		char car = e.getCharacter().charAt(0);
		if (!(Character.isDigit(car) || Character.isLetter(car))) {
			e.consume();
		}
		if (txt_password.getLength() == 30) {
			e.consume();
		}

	}

	@FXML
	void buscar_usuario() throws IOException {

		try {
			conexion.establecerConexion();
			// conecciï¿½n a la base de datos
			Connection db = conexion.getConnection();
			Statement st = db.createStatement();
			ResultSet resultado = st
					.executeQuery("select * from consulta_usuario_ced('" + txt_cedula.getText() + "');");

			if (resultado.next()) {
				id_usuario = resultado.getInt(1);
				ced_old = resultado.getString(2);
				txt_cedula.setText(resultado.getString(2));
				cbx_cargo.setValue(resultado.getString(3));
				txt_nombre.setText(resultado.getString(4));
				txt_username.setText(resultado.getString(5));
				username_old = resultado.getString(5);
				txt_password.setText(resultado.getString(6));
				id_img = resultado.getInt(7);
				desbloquear();
			} else {
				bloquear();
				limpiar();
				mostrarMesaje(
						"El usuario con cédula " + txt_cedula.getText() + " no se ecuentra registrado en el sistema");
			}

			System.out.println(id_img);
			ResultSet resultado2 = st.executeQuery("select * from Img_VE where id_img=" + id_img + ";");
			if (resultado2.next()) {
				Image img = convertirImg(resultado2.getBytes(3));
				lbl_foto.setImage(img);
			}

			conexion.cerrarConexion();

			btn_examinar.setVisible(true);

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public Image convertirImg(byte[] bytes) throws IOException {
		ByteArrayInputStream bis = new ByteArrayInputStream(bytes);

		Image img = new Image(bis);
		return img;
	}

	@FXML
	void eliminar_usuario(ActionEvent e) {

		try {
			conexion.establecerConexion();
			// conecciï¿½n a la base de datos
			Connection db = conexion.getConnection();
			Statement st = db.createStatement();
			ResultSet resultado1 = st
					.executeQuery("select * from consulta_usuario_ced('" + txt_cedula.getText() + "');");
			if (resultado1.next()) {
				PreparedStatement st2 = db.prepareStatement("DELETE FROM user_ve WHERE cedula_user=?;");
				st2.setString(1, txt_cedula.getText());
				st2.execute();
				PreparedStatement st3 = db.prepareStatement("DELETE FROM img_ve WHERE id_img=?");
				st3.setInt(1, id_img);
				st3.execute();
				mostrarMesaje("El usuario con cédula " + txt_cedula.getText() + " fue eliminado del sistema");
				limpiar();
				bloquear();
				if(origen.equals("update")) {
					try {
						AnchorPane pane = (AnchorPane) FXMLLoader.load(getClass().getResource("buscarPersonal.fxml"));
						panel.getChildren().setAll(pane);

					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			} else {
				mostrarMesaje(
						"El usuario con cédula " + txt_cedula.getText() + " no se ecuentra registrado en el sistema");
			}

			conexion.cerrarConexion();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

	}

	@FXML
	void cerrar(ActionEvent e) {
		try {
			if (origen.equals("inicio")) {
				AnchorPane pane = (AnchorPane) FXMLLoader.load(getClass().getResource("subInicio.fxml"));
				panel.getChildren().setAll(pane);
			} else if (origen.equals("usuario")) {
				AnchorPane pane = (AnchorPane) FXMLLoader.load(getClass().getResource("Usuarios.fxml"));
				panel.getChildren().setAll(pane);
			}else if(origen.equals("update")) {
				AnchorPane pane = (AnchorPane) FXMLLoader.load(getClass().getResource("buscarPersonal.fxml"));
				panel.getChildren().setAll(pane);
			}

		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

	}
}
