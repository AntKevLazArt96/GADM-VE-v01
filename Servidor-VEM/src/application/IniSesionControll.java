package application;

import java.io.IOException;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.List;
import java.util.ResourceBundle;
import org.json.simple.JSONObject;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import clases.TramVoto;
import clases.puntoATratar;
import gad.manta.common.OrdenDia;
import gad.manta.common.Pdf;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class IniSesionControll implements Initializable {
	int posicionPersonaEnTabla;
	@FXML
	private JFXTextArea label_titulo;
	@FXML
	private Label label_proponente;

	@FXML
	private Label lbl_punto;
	@FXML
	private TableView<OrdenDia> tabla_ordenDia;

	ObservableList<OrdenDia> datos;

	@FXML
	private TableView<Pdf> table_pdf;

	@FXML
	private JFXButton btnIniVoto, btn_fin;

	@FXML
	private AnchorPane panel_inicioSesion;

	@FXML
	void IniVotoAction(ActionEvent event) throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("InicioVoto.fxml"));
		AnchorPane quorum = (AnchorPane) loader.load();
		panel_inicioSesion.getChildren().setAll(quorum);

		// llamamos al controllador con todos sus componentes
		TramVoto.iv = loader.getController();
		PantallaPrincipalCtrl.tramite = "voto";
		TramVoto t = new TramVoto();
		t.iniciarVotacion();

	}

	@FXML
	void finAction(ActionEvent event) {
		// get a handle to the stage
		Stage stage = (Stage) btn_fin.getScene().getWindow();
		// do what you have to do
		stage.close();
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		try {

			List<OrdenDia> lista_orden = LoginController.servidor.consultarOrden();

			// cargo el orden del dia en los labels
			lbl_punto.setText("Seleccione el punto..");
			label_titulo.setText("Esperando...");
			label_proponente.setText("Esperando...");

			TableColumn id = new TableColumn("id");
			id.setMinWidth(500);
			id.setVisible(false);
			id.setCellValueFactory(new PropertyValueFactory<>("id"));

			TableColumn num_punto = new TableColumn("#");
			num_punto.setMinWidth(50);
			num_punto.setCellValueFactory(new PropertyValueFactory<>("numeroPunto"));

			TableColumn descripcion = new TableColumn("Descripción");
			descripcion.setMinWidth(900);
			descripcion.setCellValueFactory(new PropertyValueFactory<>("tema"));

			TableColumn proponente = new TableColumn("Proponente");
			proponente.setMinWidth(300);
			proponente.setCellValueFactory(new PropertyValueFactory<>("proponente"));
			datos = FXCollections.observableArrayList(lista_orden);

			tabla_ordenDia.getColumns().addAll(id, num_punto, descripcion, proponente);
			tabla_ordenDia.setItems(datos);

		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public OrdenDia getTablaPersonasSeleccionada() {
		if (tabla_ordenDia != null) {
			List<OrdenDia> tabla = tabla_ordenDia.getSelectionModel().getSelectedItems();
			if (tabla.size() == 1) {
				final OrdenDia competicionSeleccionada = tabla.get(0);
				return competicionSeleccionada;
			}
		}
		return null;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void ponerPersonaSeleccionada() {
		final OrdenDia orden = getTablaPersonasSeleccionada();
		posicionPersonaEnTabla = datos.indexOf(orden);

		if (orden != null) {

			puntoATratar.num_punto = String.valueOf(orden.getNumeroPunto());
			puntoATratar.tema = orden.getTema();
			puntoATratar.proponente = orden.getProponente_nombre();

			// Pongo los textFields con los datos correspondientes
			lbl_punto.setText("" + orden.getNumeroPunto());
			label_titulo.setText(orden.getTema());
			label_proponente.setText(orden.getProponente_nombre());
			// cargar la documentacion
			System.out.println("El id de la orden del dia es" + orden.getId());
			try {

				List<Pdf> lista_pdfs = LoginController.servidor.consultarPdfsPunto(orden.getId());
				TableColumn pdf = new TableColumn("id");
				pdf.setMinWidth(500);
				pdf.setVisible(false);
				pdf.setCellValueFactory(new PropertyValueFactory<>("id_pdf"));

				TableColumn nombre = new TableColumn("Nombre");
				nombre.setMinWidth(700);
				nombre.setResizable(true);
				nombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));

				ObservableList<Pdf> datos_pdf = FXCollections.observableArrayList(lista_pdfs);
				table_pdf.getColumns().addAll(pdf, nombre);

				table_pdf.setItems(datos_pdf);

			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			// socket para envio de los principales componentes al momento de dar click en
			// el tableview
			try {
				String titulo = label_titulo.getText();
				String punto = lbl_punto.getText();
				String proponente = label_proponente.getText();
				String id_pdf = String.valueOf(orden.getId());
				// String json = "{" + " 'name' : '" + data.name + "', 'message' : '" + msg +
				// "'" + "}";

				JSONObject js = new JSONObject();
				js.put("name", "InicioSesion");
				js.put("titulo", titulo);
				js.put("punto", punto);
				js.put("proponente", proponente);
				js.put("id_pdf", id_pdf);

				String json = js.toJSONString();

				System.out.println("Se envio:" + json);

				PantallaPrincipalCtrl.dos.writeUTF(json);

			} catch (IOException E) {
				E.printStackTrace();
			}
		}
	}

	@FXML
	void prueba(MouseEvent event) {
		ponerPersonaSeleccionada();

	}

}
