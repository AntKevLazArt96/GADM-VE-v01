package application;

//import java.awt.Desktop;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.time.LocalTime;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;
import java.util.List;
import java.util.ResourceBundle;
import java.util.UUID;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTimePicker;

import clases.data;
import gad.manta.common.ActaPdf;
import gad.manta.common.Conexion;
import gad.manta.common.IServidor;
import gad.manta.common.OrdenDia;
import gad.manta.common.Pdf;
import gad.manta.common.Sesion;
import gad.manta.common.Usuario;
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
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.FileChooser.ExtensionFilter;

public class ModificacionSesionCtrl implements Initializable {
	public static IServidor servidor;

	public static String convocatoria = "";
	public static Integer idPdf = 0;
	public static Integer idActa = 0;
	public static Integer idOrden = 0;
	public static String ruta_acta = "";
	public static String nombre_acta = "";
	ObservableList<String> tipoSesion = FXCollections.observableArrayList("ORDINARIA", "EXTRAORDINARIA");

	private Conexion conexion;
	private ObservableList<Sesion> lis_convocatoria;
	@FXML
	private ObservableList<Usuario> proponentes;

	@FXML
	private ObservableList<OrdenDia> listaOrden;
	@FXML
	private ObservableList<Pdf> lista_pdf;
	@FXML
	private AnchorPane panel;
	@FXML
	private JFXDatePicker date;

	@FXML
	private JFXTimePicker time;

	@FXML
	private JFXComboBox<String> cbx_tipoSes;

	@FXML
	private JFXComboBox<Sesion> cbx_convocatoria;

	@FXML
	private JFXComboBox<Usuario> cbx_proponente;

	@FXML
	private JFXTextField PuntoOrden;

	@FXML
	private Label lbl_file;

	@FXML
	private JFXTextArea txt_descripcion;

	@FXML
	private TableView<OrdenDia> tabla;
	@FXML
	private TableColumn<OrdenDia, String> id_punto;
	@FXML
	private TableColumn<OrdenDia, String> punto;
	@FXML
	private TableColumn<OrdenDia, String> descripcion;

	@FXML
	private TableColumn<OrdenDia, String> nombre_pdf;
	@FXML
	private TableColumn<OrdenDia, String> ruta_pdf;
	@FXML
	private JFXButton btn_elimSesion;
	@FXML
	private JFXButton btn_cancelar;
	@FXML
	private JFXButton btn_examinar;
	@FXML
	private JFXButton btn_examinarActa;
	@FXML
	private JFXButton btn_addOrden;
	@FXML
	private JFXButton btn_modOrden;
	@FXML
	private JFXButton btn_modSesion;
	@FXML
	private JFXButton btn_elimOrden;
	@FXML
	private JFXButton btn_nuevo;
	@FXML
	private JFXButton btn_ver;
	@FXML
	private JFXButton btn_eli_lista_pdf;
	@FXML
	private JFXButton btn_ActSesion;

	@FXML
	private TableView<Pdf> list_pdf;

	@FXML
	private JFXListView<String> pdf_acta;

	private static int id_punto_od = 0;

	@SuppressWarnings("unchecked")
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// date.setConverter(new LocalDateStringConverter(FormatStyle.FULL));
		conexion = new Conexion();
		time.setValue(LocalTime.of(16, 00));
		cbx_tipoSes.setValue("ORDINARIA");
		cbx_tipoSes.setItems(tipoSesion);
		PuntoOrden.setDisable(true);
		txt_descripcion.setDisable(true);
		cbx_proponente.setDisable(true);
		btn_examinar.setDisable(true);
		list_pdf.setDisable(true);
		conexion.establecerConexion();
		proponentes = FXCollections.observableArrayList();
		Sesion.llenarInformacion(conexion.getConnection(), proponentes);

		cbx_proponente.setItems(proponentes);

		conexion.cerrarConexion();

		conexion.establecerConexion();
		lis_convocatoria = FXCollections.observableArrayList();
		Sesion.llenarInformacion_sesion(conexion.getConnection(), lis_convocatoria);
		cbx_convocatoria.setItems(lis_convocatoria);

		conexion.cerrarConexion();

		try {
			servidor = (IServidor) Naming.lookup("rmi://192.168.1.6/VotoE");
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		bloquear_control_pdf();
		btn_modOrden.setDisable(true);
		btn_modSesion.setDisable(true);
		btn_addOrden.setDisable(true);
		btn_elimOrden.setDisable(true);
		btn_ActSesion.setVisible(false);
		btn_modSesion.setVisible(false);
		btn_elimSesion.setVisible(false);
		btn_nuevo.setDisable(true);
		@SuppressWarnings("rawtypes")
		TableColumn nombre = new TableColumn("Nombre");
		nombre.setMinWidth(360);
		nombre.setVisible(true);
		nombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));

		@SuppressWarnings("rawtypes")
		TableColumn ruta_pdf = new TableColumn("Ruta");
		ruta_pdf.setMinWidth(80);
		ruta_pdf.setVisible(false);
		ruta_pdf.setCellValueFactory(new PropertyValueFactory<>("ruta_pdf"));

		list_pdf.getColumns().addAll(nombre, ruta_pdf);
		lista_pdf = FXCollections.observableArrayList();

		@SuppressWarnings("rawtypes")
		TableColumn id_punto = new TableColumn("No. Punto");
		id_punto.setMinWidth(50);
		id_punto.setVisible(false);
		id_punto.setCellValueFactory(new PropertyValueFactory<>("id"));

		@SuppressWarnings("rawtypes")
		TableColumn num_punto = new TableColumn("No. Punto");
		num_punto.setMinWidth(80);
		num_punto.setCellValueFactory(new PropertyValueFactory<>("numeroPunto"));
		@SuppressWarnings("rawtypes")
		TableColumn descrip = new TableColumn("DescripciÃ³n");
		descrip.setMinWidth(300);
		descrip.setCellValueFactory(new PropertyValueFactory<>("tema"));

		tabla.getColumns().addAll(id_punto, num_punto, descrip);

	}

	public void bloquear() {

		cbx_tipoSes.setDisable(true);
		date.setDisable(true);
		time.setDisable(true);
		btn_examinarActa.setDisable(true);
	}

	public void activar() {
		cbx_convocatoria.setDisable(false);
		cbx_tipoSes.setDisable(false);
		date.setDisable(false);
		time.setDisable(false);
		btn_examinarActa.setDisable(false);
		btn_modSesion.setDisable(false);
		btn_modSesion.setVisible(true);

	}

	public void limpiar() {
		PuntoOrden.setText(String.valueOf(data.num_punto));
		txt_descripcion.setText(null);
		cbx_proponente.setValue(null);
		rutapdf = "";
		list_pdf.getItems().clear();
		lista_pdf.clear();
		btn_modOrden.setDisable(true);

	}

	public void limpiar_sesion() {

		cbx_convocatoria.setValue(null);
		date.setValue(null);
		pdf_acta.getItems().clear();
	}

	public void bloquear_control_pdf() {
		btn_ver.setDisable(true);

		btn_eli_lista_pdf.setDisable(true);

	}

	public void desbloquear_control_pdf() {
		btn_ver.setDisable(false);

		btn_eli_lista_pdf.setDisable(false);
	}

	@FXML
	void onModSesion(ActionEvent event) throws NotBoundException, IOException, SQLException {

		if (cbx_convocatoria.getValue().getConvocatoria() == null) {
			mostrarMesaje("Falta ingresar la convocatoria");
		} else if (date.getValue() == null) {
			mostrarMesaje("Falta selecionar la fecha de intervenciÃ³n");
		} else if (pdf_acta.getItems().size() == 0) {
			mostrarMesaje("Falta agregar el acta de la sesiÃ³n");
		} else {

			String txtconvocatoria = cbx_convocatoria.getValue().getConvocatoria();
			String[] meses = { "ENERO", "ENERO", "FEBRERO", "MARZO", "ABRIL", "MAYO", "JUNIO", "JULIO", "AGOSTO",
					"SEPTIEMBRE", "OCTUBRE", "NOVIEMBRE", "DICIEMBRE" };
			String fechaCompleta = date.getValue().getDayOfMonth() + " DE " + meses[date.getValue().getMonthValue()]
					+ " DEL " + date.getValue().getYear();
			String horaIntervencion = time.getValue().toString();
			String titulo = "SESIÃ“N " + cbx_tipoSes.getValue()
					+ " DEL CONCEJO DEL GOBIERNO AUTÃ“NOMO DESCENTRALIZADO MUNICIPAL DEL CANTÃ“N MANTA, CORRESPONDIENTE AL DÃ�A "
					+ fechaCompleta + ", A lAS " + horaIntervencion + " EN EL SALON DE ACTOS DEL GADMC-MANTA";

			String tipo_sesion = cbx_tipoSes.getValue();
			Date fechaIntervencion = Date.valueOf(date.getValue());
			Date fechaRegistro = new Date(Calendar.getInstance().getTime().getTime());

			
			String sql = " UPDATE public.sesion_ve SET convocatoria_sesion='" + txtconvocatoria
					+ "', description_sesion='" + titulo + "', tipo_sesion='" + tipo_sesion + "', register_sesion='"
					+ fechaRegistro + "'," + " intervention_sesion='" + fechaIntervencion + "', hour_sesion='"
					+ horaIntervencion + "' WHERE convocatoria_sesion='" + convocatoria + "';";

			String sql2 = "UPDATE public.acta_ve SET nombre_pdf=?, archivo_pdf=? WHERE id_pdf=" + idActa + ";";

			try {
				conexion.establecerConexion();
				// conecci�n a la base de datos
				conexion.getConnection();
				Connection db =conexion.getConnection();
				PreparedStatement instruccion = db.prepareStatement(sql);

				File pdf = new File(ruta_acta);
				FileInputStream fis = new FileInputStream(pdf);
				PreparedStatement ps = db.prepareStatement(sql2);

				ps.setString(1, nombre_acta);
				ps.setBinaryStream(2, fis, (int) pdf.length());
				ps.execute();
				fis.close();

				if (!(instruccion.execute() && ps.execute())) {
					mostrarMesaje("La convocatoria a sido atualizada");
					btn_modSesion.setDisable(false);
					btn_ActSesion.setVisible(true);
					btn_modSesion.setVisible(true);
					btn_elimSesion.setVisible(true);
					bloquear();
				} else {
					mostrarMesaje("No se a podido actualizar la convocatoria");
				}
				;
				conexion.cerrarConexion();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				// e1.printStackTrace();
				mostrarMesaje("Ya existe una convocatoria " + txtconvocatoria + ", en el sistema");
			}

		}
	}

	@FXML
	void onFinAction(ActionEvent event) {
		try {
			AnchorPane pane = (AnchorPane) FXMLLoader.load(getClass().getResource("subSesiones.fxml"));

			panel.getChildren().setAll(pane);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// variable global para guardar el path del pdf
	private static String rutapdf = "";

	@FXML
	void onExaAction(ActionEvent event) throws IOException {

		try {
			FileChooser fc = new FileChooser();
			// fc.setInitialDirectory(new File(""));
			fc.getExtensionFilters().addAll(new ExtensionFilter("PDF Files", "*.pdf"));
			File selectedf = fc.showOpenDialog(null);
			if (selectedf != null) {
				String filePath = "C:\\Documentos-GAD_VOTO";
				String dbpath = "C:\\Documentos-GAD_VOTO\\" + selectedf.getName();
				// origen
				Path FROM = Paths.get(selectedf.getAbsolutePath());
				// destino
				Path TO = Paths.get(dbpath);
				// crea en dirrectorio si no existe
				File folder = new File(filePath);
				// pdf.guardarRegistro_pdf(conexion.getConnection());
				if (!folder.exists()) {
					folder.mkdir();
				}
				File fichero = new File(dbpath);

				// lista_pdf.set(0,new Pdf(fichero.getName(),dbpath));
				lista_pdf.add(new Pdf(fichero.getName(), dbpath));

				ObservableList<Pdf> datos = FXCollections.observableArrayList(lista_pdf);
				list_pdf.setItems(datos);

				// agrega al lisview
				// copia el archivo al directorio
				CopyOption[] opciones = new CopyOption[] { StandardCopyOption.REPLACE_EXISTING,
						StandardCopyOption.COPY_ATTRIBUTES };
				Files.copy(FROM, TO, opciones);

			}
		} catch (NullPointerException nl) {
			nl.printStackTrace();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	@FXML
	void onExaActa(ActionEvent event) throws IOException {
		String dbpath = "";
		try {
			FileChooser fc = new FileChooser();
			// fc.setInitialDirectory(new File(""));
			fc.getExtensionFilters().addAll(new ExtensionFilter("PDF Files", "*.pdf"));
			File selectedf = fc.showOpenDialog(null);
			if (selectedf != null) {
				String filePath = "C:\\Documentos-GAD_VOTO";
				dbpath = "C:\\Documentos-GAD_VOTO\\" + selectedf.getName();
				// origen
				Path FROM = Paths.get(selectedf.getAbsolutePath());
				// destino
				Path TO = Paths.get(dbpath);
				// crea en dirrectorio si no existe
				File folder = new File(filePath);

				if (!folder.exists()) {
					folder.mkdir();
				}
				File fichero = new File(dbpath);

				// agrega al lisview
				pdf_acta.getItems().clear();
				pdf_acta.getItems().add(selectedf.getName());
				// copia el archivo al directorio
				CopyOption[] opciones = new CopyOption[] { StandardCopyOption.REPLACE_EXISTING,
						StandardCopyOption.COPY_ATTRIBUTES };
				Files.copy(FROM, TO, opciones);
				ruta_acta = dbpath;
				nombre_acta = fichero.getName();

			}
		} catch (NullPointerException nl) {
			nl.printStackTrace();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	@FXML
	void activar_controles_pdf(MouseEvent event) throws RemoteException {

		desbloquear_control_pdf();

	}

	@FXML
	void mostrar_acta(MouseEvent event) throws RemoteException {

		try {

			File archivo = new File(ruta_acta);
			data.archivo_pff = archivo;
			data.tipo_lectura = 3;
			Stage newStage = new Stage();

			AnchorPane pane;
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

		} catch (IOException ex) {
			ex.printStackTrace();
		}

	}

	@SuppressWarnings("unchecked")
	@FXML
	void onAddOrden(ActionEvent event) throws IOException {

		System.out.println("La convocatoria es: " + convocatoria);
		if (convocatoria == "") {
			mostrarMesaje("Primero tiene que agregar la sesión");
		} else {

			int longitud_lista = 0;
			longitud_lista = list_pdf.getItems().size();

			if (txt_descripcion.getLength() == 0) {
				mostrarMesaje("Falta ingresar la descripción del punto");
			} else if (cbx_proponente.getValue() == null) {
				mostrarMesaje("Falta selecionar el Proponente del punto");
			} else {

				if (longitud_lista == 0) {
					mostrarMesaje("No se a agredado documentaciónn " + "para el punto " + PuntoOrden.getText() + "");
				}
				if (data.documentacion == 0) {

					OrdenDia orden = new OrdenDia(convocatoria, Integer.valueOf(PuntoOrden.getText()),
							txt_descripcion.getText(), cbx_proponente.getValue().getId());
					conexion.establecerConexion();
					idOrden = orden.guardarRegistro(conexion.getConnection());
					conexion.cerrarConexion();

					conexion.establecerConexion();

					while (longitud_lista > 0)
						try {
							{
								System.out.println(list_pdf.getItems().get(longitud_lista - 1).getRuta_pdf());
								Pdf pdf = new Pdf(idOrden, list_pdf.getItems().get(longitud_lista - 1).getRuta_pdf());
								pdf.guardarRegistro_pdfs(conexion.getConnection());
								longitud_lista--;
							}
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					conexion.cerrarConexion();

					data.num_punto = data.num_punto + 1;
					limpiar();

				}

			}

			List<OrdenDia> listaOrden = FXCollections.observableArrayList();
			conexion.establecerConexion();

			OrdenDia.llenarInformacion(conexion.getConnection(), listaOrden, convocatoria);
			conexion.cerrarConexion();

			@SuppressWarnings("rawtypes")
			TableColumn id_punto = new TableColumn("No. Punto");
			id_punto.setMinWidth(50);
			id_punto.setVisible(false);
			id_punto.setCellValueFactory(new PropertyValueFactory<>("id"));

			@SuppressWarnings("rawtypes")
			TableColumn num_punto = new TableColumn("No. Punto");
			num_punto.setMinWidth(80);
			num_punto.setCellValueFactory(new PropertyValueFactory<>("numeroPunto"));
			@SuppressWarnings("rawtypes")
			TableColumn descrip = new TableColumn("Descripción");
			descrip.setMinWidth(300);
			descrip.setCellValueFactory(new PropertyValueFactory<>("tema"));
			ObservableList<OrdenDia> datos = FXCollections.observableArrayList(listaOrden);

			tabla.getColumns().addAll(id_punto, num_punto, descrip);
			tabla.setItems(datos);
		}
	}

	@SuppressWarnings("unchecked")
	@FXML
	public void onModOrden(ActionEvent event) throws IOException {

		int longitud_lista = 0;
		longitud_lista = list_pdf.getItems().size();

		if (txt_descripcion.getLength() == 0) {
			mostrarMesaje("Falta ingresar la descripción del punto");
		} else if (cbx_proponente.getValue().getId() == 0) {
			mostrarMesaje("Falta selecionar el Proponente del punto");
		} else {

			if (longitud_lista == 0) {
				mostrarMesaje("No se a agredado documentación " + "para el punto " + PuntoOrden.getText() + "");
			}
			if (data.documentacion == 0) {

				
				String sql = "UPDATE public.ordendia_ve SET  convocatoria_sesion='"
						+ cbx_convocatoria.getValue().getConvocatoria() + "', numpunto_ordendia="
						+ Integer.valueOf(PuntoOrden.getText()) + ", descrip_ordendia='" + txt_descripcion.getText()
						+ "', id_user=" + cbx_proponente.getValue().getId() + " WHERE id_ordendia=" + id_punto_od + ";";
				String sql2 = "DELETE FROM public.pdf_ve WHERE id_ordendia=" + id_punto_od + ";";
				String sql3 = "INSERT INTO pdf_ve(id_ordendia,nombre_pdf,archivo_pdf) VALUES (?, ?, ?);";

				try {
					conexion.establecerConexion();
					// conecci�n a la base de datos
					conexion.getConnection();
					Connection db =conexion.getConnection();
					PreparedStatement instruccion = db.prepareStatement(sql);

					PreparedStatement instruccion2 = db.prepareStatement(sql2);

					if (!(instruccion.execute()) && !(instruccion2.execute())) {
						mostrarMesaje("El punto a sido atualizada");
					} else {
						mostrarMesaje("No se a podido actualizar el punto");
					}
					;

					while (longitud_lista > 0) {

						PreparedStatement instruccion3 = db.prepareStatement(sql3);
						File pdf = new File(list_pdf.getItems().get(longitud_lista - 1).getRuta_pdf());
						FileInputStream fis = new FileInputStream(pdf);
						instruccion3.setInt(1, id_punto_od);
						instruccion3.setString(2, list_pdf.getItems().get(longitud_lista - 1).getNombre());
						instruccion3.setBinaryStream(3, fis, (int) pdf.length());
						instruccion3.executeUpdate();
						instruccion3.close();
						fis.close();
						longitud_lista--;
					}
					conexion.cerrarConexion();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
					// mostrarMesaje("Ya existe una convocatoria "+txtconvocatoria+", en el
					// sistema");
				}

				limpiar();
				PuntoOrden.setDisable(true);

			}

		}

		List<OrdenDia> listaOrden = FXCollections.observableArrayList();
		conexion.establecerConexion();

		OrdenDia.llenarInformacion(conexion.getConnection(), listaOrden, convocatoria);
		conexion.cerrarConexion();

		@SuppressWarnings("rawtypes")
		TableColumn id_punto = new TableColumn("No. Punto");
		id_punto.setMinWidth(50);
		id_punto.setVisible(false);
		id_punto.setCellValueFactory(new PropertyValueFactory<>("id"));

		@SuppressWarnings("rawtypes")
		TableColumn num_punto = new TableColumn("No. Punto");
		num_punto.setMinWidth(80);
		num_punto.setCellValueFactory(new PropertyValueFactory<>("numeroPunto"));
		@SuppressWarnings("rawtypes")
		TableColumn descrip = new TableColumn("Descripción");
		descrip.setMinWidth(300);
		descrip.setCellValueFactory(new PropertyValueFactory<>("tema"));
		ObservableList<OrdenDia> datos = FXCollections.observableArrayList(listaOrden);

		tabla.getColumns().addAll(id_punto, num_punto, descrip);
		tabla.setItems(datos);

	}

	@FXML
	public void mostrar_pdf(ActionEvent event) {
		try {
			String ruta = "";
			ruta = list_pdf.getSelectionModel().selectedItemProperty().getValue().getRuta_pdf();
			File archivo = new File(ruta);
			data.archivo_pff = archivo;
			data.tipo_lectura = 3;
			Stage newStage = new Stage();

			AnchorPane pane;
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
			// bloquear_control_pdf();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	@FXML
	public void eli_lista_pdf(ActionEvent event) {
		lista_pdf.remove(list_pdf.getSelectionModel().getSelectedIndex());
		list_pdf.getItems().remove(list_pdf.getSelectionModel().getSelectedIndex());
		bloquear_control_pdf();

	}

	@FXML
	public void onNewOrden(ActionEvent event) {

		btn_addOrden.setDisable(false);
		PuntoOrden.setDisable(true);
		txt_descripcion.setDisable(false);
		cbx_proponente.setDisable(false);
		btn_examinar.setDisable(false);
		btn_elimOrden.setDisable(true);
		list_pdf.setDisable(false);

		PuntoOrden.setText(String.valueOf(data.num_punto));
	}

	@FXML
	public void onActSesion(ActionEvent event) {

		activar();
		btn_elimSesion.setVisible(false);
		btn_ActSesion.setVisible(false);

	}

	@FXML
	public void onElimSesion(ActionEvent event) throws SQLException {
		conexion.establecerConexion();
		// conecci�n a la base de datos
		conexion.getConnection();
		Connection db =conexion.getConnection();
		Statement st = db.createStatement();
		ResultSet resultado = st.executeQuery("SELECT id_pdf  FROM public.sesion_ve where convocatoria_sesion='"
				+ cbx_convocatoria.getValue().getConvocatoria() + "';");

		if (resultado.next()) {

			String sql = "DELETE FROM public.acta_ve WHERE id_pdf=" + resultado.getInt(1) + ";";
			PreparedStatement instruccion = db.prepareStatement(sql);
			if (!instruccion.execute()) {
				mostrarMesaje("La sesión" + cbx_convocatoria.getValue().getConvocatoria()
						+ " a sido eliminada correctamemte");
				data.num_punto = 0;
				limpiar();
				activar();
				limpiar_sesion();

				try {
					PreparedStatement in = db.prepareStatement("Delete from quorum_ve where id_quorum=" + id_quorum);
					in.execute();
				} catch (Exception e) {
					e.getMessage();
				}

				btn_ActSesion.setVisible(false);
				btn_modSesion.setVisible(false);
				btn_elimSesion.setVisible(false);
				cbx_convocatoria.getItems().clear();
				;
				conexion.establecerConexion();
				lis_convocatoria = FXCollections.observableArrayList();
				Sesion.llenarInformacion_sesion(conexion.getConnection(), lis_convocatoria);
				cbx_convocatoria.setItems(lis_convocatoria);
				conexion.cerrarConexion();

			} else {
				mostrarMesaje("Hubo un error al eliminar sesión" + cbx_convocatoria.getValue().getConvocatoria() + "");
			}
		}
		conexion.cerrarConexion();

	}

	@SuppressWarnings("unchecked")
	@FXML
	public void onElimOrden(ActionEvent event) {

		
		String sql = "DELETE FROM public.ordendia_ve WHERE id_ordendia=" + id_punto_od + ";";
		String sql2 = "DELETE FROM public.pdf_ve WHERE id_ordendia=" + id_punto_od + ";";

		try {
			conexion.establecerConexion();
			// conecci�n a la base de datos
			conexion.getConnection();
			Connection db =conexion.getConnection();
			PreparedStatement instruccion = db.prepareStatement(sql);
			PreparedStatement instruccion2 = db.prepareStatement(sql2);

			if (!(instruccion.execute() && instruccion2.execute())) {
				mostrarMesaje("El punto a sido eliminado correctamemte");
			} else {
				mostrarMesaje("No se a podido eliminar el el punto");
			}
			;
			conexion.cerrarConexion();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			// mostrarMesaje("Ya existe una convocatoria "+txtconvocatoria+", en el
			// sistema");
			mostrarMesaje("No se a podido eliminar el el punto");
		}

		btn_ActSesion.setVisible(false);
		List<OrdenDia> listaOrden = FXCollections.observableArrayList();
		conexion.establecerConexion();

		OrdenDia.llenarInformacion(conexion.getConnection(), listaOrden, convocatoria);
		conexion.cerrarConexion();

		@SuppressWarnings("rawtypes")
		TableColumn id_punto = new TableColumn("No. Punto");
		id_punto.setMinWidth(50);
		id_punto.setVisible(false);
		id_punto.setCellValueFactory(new PropertyValueFactory<>("id"));

		@SuppressWarnings("rawtypes")
		TableColumn num_punto = new TableColumn("No. Punto");
		num_punto.setMinWidth(80);
		num_punto.setCellValueFactory(new PropertyValueFactory<>("numeroPunto"));
		@SuppressWarnings("rawtypes")
		TableColumn descrip = new TableColumn("Descripción");
		descrip.setMinWidth(300);
		descrip.setCellValueFactory(new PropertyValueFactory<>("tema"));
		ObservableList<OrdenDia> datos = FXCollections.observableArrayList(listaOrden);

		tabla.getColumns().addAll(id_punto, num_punto, descrip);
		tabla.setItems(datos);
		limpiar();

	}

	@FXML
	public void mostrar_punto(MouseEvent event) throws IOException {
		btn_modOrden.setDisable(false);
		btn_nuevo.setDisable(false);
		btn_elimOrden.setDisable(false);
		PuntoOrden.setDisable(false);
		btn_addOrden.setDisable(true);
		PuntoOrden.setDisable(true);
		txt_descripcion.setDisable(false);
		cbx_proponente.setDisable(false);
		btn_examinar.setDisable(false);
		list_pdf.setDisable(false);
		PuntoOrden.setDisable(false);

		id_punto_od = tabla.getSelectionModel().selectedItemProperty().getValue().getId();

		txt_descripcion.setText(tabla.getSelectionModel().selectedItemProperty().getValue().getTema());
		PuntoOrden
				.setText(String.valueOf(tabla.getSelectionModel().selectedItemProperty().getValue().getNumeroPunto()));
		int id_pro = tabla.getSelectionModel().selectedItemProperty().getValue().getProponente();
		int log = proponentes.size();
		int bandera = 0;
		while (log >= 1) {
			if (id_pro == proponentes.get(bandera).getId()) {
				cbx_proponente.setValue(proponentes.get(bandera));
			}
			log--;
			bandera++;

		}

		List<Pdf> pdf;

		pdf = Pdf.consultarPDFS_Modificacion(id_punto_od);
		list_pdf.getItems().clear();
		lista_pdf.clear();
		int log_pdf = pdf.size();
		int bandera_2 = 0;
		list_pdf.getItems().clear();
		while (log_pdf > 0) {
			Pdf pdf_file = Pdf.pdf_punto(pdf.get(bandera_2).getId());
			File n = new File(convertirPdf(pdf_file.getPdf()));
			lista_pdf.addAll(new Pdf(pdf_file.getNombre(), n.getPath()));
			log_pdf--;
			bandera_2++;
		}

		ObservableList<Pdf> datos = FXCollections.observableArrayList(lista_pdf);

		list_pdf.setItems(datos);

	}

	public String convertirPdf(byte[] bytes) throws IOException {
		String tmpDir = System.getProperty("java.io.tmpdir") + "\\tmp\\";
		String tmpFileName = UUID.randomUUID().toString();
		if (!new File(tmpDir).exists()) {
			if (!new File(tmpDir).mkdirs()) {
				System.out.print("Imposible crear directorio temporal");
				return null;
			}

		}
		OutputStream out = new FileOutputStream(tmpDir + tmpFileName + ".pdf");
		out.write(bytes);
		out.close();
		File file = new File(tmpDir + tmpFileName + ".pdf");
		file.deleteOnExit();

		return file.getPath();
	}

	static int id_quorum;

	@FXML
	void llenar_sesion(ActionEvent event) throws IOException {
		List<gad.manta.common.Sesion> lista_sesion = null;
		tabla.getItems().clear();

		String a = cbx_convocatoria.getValue().getConvocatoria();

		if (a != null) {
			lista_sesion = servidor.consultarSesion_Modificacion(a);
		}

		id_quorum = lista_sesion.get(0).getId_quorum();
		System.out.println("el idquorum es: " + id_quorum);
		cbx_tipoSes.setValue(lista_sesion.get(0).getTipo_sesion());
		date.setValue(lista_sesion.get(0).getFechaIntervencion().toLocalDate());
		time.setValue(LocalTime.parse(lista_sesion.get(0).getHoraIntervencion()));
		int id_acta = lista_sesion.get(0).getId_pdf();

		convocatoria = a;
		ActaPdf pdf_file = ActaPdf.pdf_acta(id_acta);

		File n = new File(convertirPdf(pdf_file.getPdf()));
		ruta_acta = n.getPath();
		nombre_acta = pdf_file.getNombre();
		// agrega al lisview
		pdf_acta.getItems().clear();
		pdf_acta.getItems().add(nombre_acta);

		List<OrdenDia> listaOrden = FXCollections.observableArrayList();
		conexion.establecerConexion();

		OrdenDia.llenarInformacion(conexion.getConnection(), listaOrden, a);
		conexion.cerrarConexion();
		bloquear();

		int longi = 0;
		int bandera = 0;
		longi = listaOrden.size();
		int numMax = 0;

		while (longi > 0) {
			if (listaOrden.get(bandera).getNumeroPunto() > numMax) {
				numMax = listaOrden.get(bandera).getNumeroPunto();
			}
			bandera++;
			longi--;
		}
		data.num_punto = numMax + 1;
		PuntoOrden.setText(String.valueOf(data.num_punto));
		btn_modSesion.setDisable(false);

		btn_modSesion.setVisible(false);
		btn_elimSesion.setVisible(true);
		btn_ActSesion.setVisible(true);
		btn_nuevo.setDisable(false);
		PuntoOrden.setDisable(false);

		ObservableList<OrdenDia> datos = FXCollections.observableArrayList(listaOrden);
		tabla.setItems(datos);

	}

	@FXML
	void validar_punto(KeyEvent e) {

		char car = e.getCharacter().charAt(0);
		if (!Character.isDigit(car)) {
			e.consume();
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

}
