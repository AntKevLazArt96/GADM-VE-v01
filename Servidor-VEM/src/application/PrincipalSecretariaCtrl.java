package application;

//import java.awt.Desktop;
import java.io.IOException;
import java.net.URL;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
//import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import org.json.simple.JSONObject;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import clases.TramVotoOrden;
import clases.data;
import gad.manta.common.Documentacion;
import gad.manta.common.OrdenDia;
import gad.manta.common.Sesion;
import gad.manta.common.data_configuracion;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class PrincipalSecretariaCtrl implements Initializable {
	int sesionDe = 0;

	@FXML
	private AnchorPane panel_psecretaria;

	@FXML
	private JFXButton btnIniSesion;

	@FXML
	private JFXTextArea label_titulo;
	@FXML

	private TableView<OrdenDia> tabla_ordenDia;
	@FXML
	private Label label_punto;

	@FXML
	private TableView<Documentacion> table_documentacion;

	@FXML
	private JFXTextField sesionA;

	@FXML
	private JFXButton btn_pdf;

	@FXML
	private JFXButton btn_modificar;

	@FXML
	private Label lblOrden;

	public Sesion sesion;

	@FXML
	void iniSesion(ActionEvent event) throws IOException, NotBoundException {

		FXMLLoader loader = new FXMLLoader(getClass().getResource("InicioVotoOrden.fxml"));
		AnchorPane quorum = (AnchorPane) loader.load();
		panel_psecretaria.getChildren().setAll(quorum);

		// llamamos al controllador con todos sus componentes
		TramVotoOrden.ivo = loader.getController();
		PantallaPrincipalCtrl.tramite = "votoOrden";
		TramVotoOrden t = new TramVotoOrden();
		t.iniciarVotacion();
	}

	@SuppressWarnings("unchecked")
	@FXML
	void modificar_sesion(ActionEvent event) {

		try {
			data.tipo_modi=2;
			
			// get a handle to the stage
			Stage actualStage = (Stage) btn_modificar.getScene().getWindow();
			// do what you have to do
			actualStage.close();
			Stage newStage = new Stage();
			AnchorPane pane;
			pane = (AnchorPane) FXMLLoader.load(getClass().getResource("ModificacionSesion.fxml"));
			Scene scene = new Scene(pane);
			newStage.setScene(scene);
			newStage.initStyle(StageStyle.UNDECORATED);
			newStage.show();
			//no se
			JSONObject js = new JSONObject();
			js.put("name", "ordenModificada	");
			String json = js.toJSONString();
			System.out.println("Se envio:" + json);
			PantallaPrincipalCtrl.dos.writeUTF(json);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@FXML
	void mostrar_acta(ActionEvent event) {

		Stage newStage = new Stage();
		AnchorPane pane;
		try {
			data.tipo_lectura=1;
			
			data.id_acta = sesion.getId_pdf();
			pane = (AnchorPane) FXMLLoader.load(getClass().getResource("LecturaPDF.fxml"));
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

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@FXML
	void mostrar_pdf(MouseEvent event) {

		Stage newStage = new Stage();

		AnchorPane pane;
		try {

			data.id_acta = 0;
			data.tipo_lectura=2;
			data.id_pdf = table_documentacion.getSelectionModel().selectedItemProperty().get().getId_pdf();
			System.out.println(data.id_pdf);

			pane = (AnchorPane) FXMLLoader.load(getClass().getResource("LecturaPDF.fxml"));
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

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		try {

			sesion = LoginController.servidor.consultarSesion();
			label_titulo.setText(sesion.getDescription());

			List<OrdenDia> lista_orden = LoginController.servidor.consultarOrden();

			TableColumn num_punto = new TableColumn("No. Punto");
			num_punto.setMinWidth(50);
			num_punto.setCellValueFactory(new PropertyValueFactory<>("numeroPunto"));

			TableColumn descripcion = new TableColumn("Descripci�n");
			descripcion.setMinWidth(900);
			descripcion.setCellValueFactory(new PropertyValueFactory<>("tema"));

			TableColumn proponente = new TableColumn("Proponente");
			proponente.setMinWidth(300);
			proponente.setCellValueFactory(new PropertyValueFactory<>("proponente_nombre"));
			ObservableList<OrdenDia> datos = FXCollections.observableArrayList(lista_orden);

			tabla_ordenDia.getColumns().addAll(num_punto, descripcion, proponente);
			tabla_ordenDia.setItems(datos);

			List<Documentacion> lista_documentacion = LoginController.servidor.mostrarDocumentacion();
			TableColumn pdf = new TableColumn("id");
			pdf.setMinWidth(500);
			pdf.setVisible(false);
			pdf.setCellValueFactory(new PropertyValueFactory<>("id_pdf"));

			TableColumn punto = new TableColumn("Punto");
			punto.setMinWidth(100);
			punto.setCellValueFactory(new PropertyValueFactory<>("punto"));
			TableColumn nombre = new TableColumn("Nombre");
			nombre.setMinWidth(700);
			nombre.setResizable(true);

			nombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));

			ObservableList<Documentacion> datos_pdf = FXCollections.observableArrayList(lista_documentacion);
			table_documentacion.getColumns().addAll(pdf, punto, nombre);

			table_documentacion.setItems(datos_pdf);

		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
