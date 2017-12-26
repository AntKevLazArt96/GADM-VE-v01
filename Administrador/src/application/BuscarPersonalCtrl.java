package application;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import clases.Conexion;
import clases.Usuarios;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

public class BuscarPersonalCtrl implements Initializable {

	private String cedula_modi = "";

	private Conexion conexion;
	@FXML
	private TableView<Usuarios> table;

	private ObservableList<Usuarios> users;

	@FXML
	private TableColumn<Usuarios, String> id;
	@FXML
	private TableColumn<Usuarios, String> cedula;
	@FXML
	private TableColumn<Usuarios, String> cargo;

	@FXML
	private TableColumn<Usuarios, String> nombre;
	@FXML
	private TableColumn<Usuarios, String> username;

	@FXML
	private AnchorPane panel;

	@FXML
	private JFXTextField txt_cedula;

	@FXML
	private JFXButton btn_buscar_usu;

	@FXML
	private AnchorPane panelDatos;

	@FXML
	private JFXButton btn_modificar;


	@FXML
	void buscar_usuario(ActionEvent event) {

	}

	@FXML
	void cerrar(ActionEvent event) {
		try {
			AnchorPane pane = (AnchorPane) FXMLLoader.load(getClass().getResource("Usuarios.fxml"));
			panel.getChildren().setAll(pane);

		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}


	@FXML
	void onModificar(ActionEvent event) {
		if (!cedula_modi.equals("")) {
			try {
				ModificacionPerCtrl.origen="update";
				ModificacionPerCtrl.cedula=cedula_modi;
				AnchorPane pane = (AnchorPane) FXMLLoader.load(getClass().getResource("ModificacionPersonal.fxml"));
				panel.getChildren().setAll(pane);

			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
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

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		btn_modificar.setDisable(true);
		conexion = new Conexion();
		consultarUsuarios();
	}

	private void consultarUsuarios() {
		conexion.establecerConexion();
		users = FXCollections.observableArrayList();
		Usuarios.consultaUsuario(conexion.getConnection(), users);
		ObservableList<Usuarios> datos = FXCollections.observableArrayList(users);
		id.setCellValueFactory(new PropertyValueFactory<>("id"));
		id.setVisible(false);
		cedula.setCellValueFactory(new PropertyValueFactory<>("cedula"));
		cargo.setCellValueFactory(new PropertyValueFactory<>("cargo"));
		nombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
		username.setCellValueFactory(new PropertyValueFactory<>("usuario"));
		table.setItems(datos);
		conexion.cerrarConexion();
	}

	@FXML
	void seleccionarUser(MouseEvent event) {
		cedula_modi = table.getSelectionModel().selectedItemProperty().getValue().getCedula();
		if (!cedula_modi.equals("")) {
			System.out.println(cedula_modi);
			btn_modificar.setDisable(false);
		}
	}

}
