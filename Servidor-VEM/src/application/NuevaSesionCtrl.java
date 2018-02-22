package application;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.rmi.NotBoundException;
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
import gad.manta.common.OrdenDia;
import gad.manta.common.Pdf;
import gad.manta.common.Sesion;
import gad.manta.common.Usuario;
import gad.manta.common.Voto;
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
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class NuevaSesionCtrl implements Initializable {
	// variable para la conexion
	private Conexion conexion;
	// para llenar el combo box tipo sesion
	ObservableList<String> tipoSesion = FXCollections.observableArrayList("ORDINARIA", "EXTRAORDINARIA");
	// para agregar un pdf tipo acta a la base de datos
	String ruta_acta = "";
	String nombre_acta = "";
	// Para guardar el acta
	Integer idActa = 0;
	// para Agregar los puntos del orden del dia
	String convocatoria = "";
	// para la lista de los proponentes
	@FXML
	private ObservableList<Usuario> proponentes;
	// para la lista de los pdfs
	@FXML
	private ObservableList<Pdf> lista_pdf;

	// contador para los pdf
	@SuppressWarnings("unused")
	private int contador = 0;

	// despues de que se guarda el orden del dia
	Integer idOrden = 0;
	// inicializar la variable
	ObservableList<OrdenDia> datos;
	// punto old
	private int id_punto_od;

	// columan idPunto
	@FXML
	private TableColumn<OrdenDia, String> id_punto;

	// variable para el boton nuevo
	boolean clickTable;

	@FXML
	private AnchorPane panel;

	@FXML
	private AnchorPane panelOrden;

	@FXML
	private TableView<OrdenDia> tabla;

	@FXML
	private JFXButton btn_cancelar;

	@FXML
	private AnchorPane panelAddPunto;

	@FXML
	private JFXTextArea txt_descripcion;

	@FXML
	private JFXButton btn_examinar;

	@FXML
	private JFXButton btn_elimOrden;

	@FXML
	private JFXComboBox<Usuario> cbx_proponente;

	@FXML
	private JFXTextField PuntoOrden;

	@FXML
	private JFXButton btn_eli_lista_pdf;

	@FXML
	private JFXButton btn_ver;

	@FXML
	private JFXButton btn_addOrden;

	@FXML
	private TableView<Pdf> list_pdf;

	@FXML
	private JFXButton btn_nuevo;

	@FXML
	private JFXButton btn_modOrden;

	@FXML
	private JFXButton btn_resoluciones;

	@FXML
	private JFXTextField txt_convocatoria;

	@FXML
	private JFXComboBox<String> cbx_tipoSes;

	@FXML
	private JFXDatePicker date;

	@FXML
	private JFXTimePicker time;

	@FXML
	private JFXListView<String> pdf_acta;

	@FXML
	private JFXButton btn_examinarActa;

	@FXML
	private JFXButton btn_ActSesion;

	@FXML
	private JFXButton btn_modSesion;

	@FXML
	private JFXButton btn_elimSesion;

	@FXML
	private JFXButton btn_addSesion;

	@FXML
	private AnchorPane panelAddReso;

	@FXML
	private JFXButton btn_volver;

	@FXML
	private Label lbl_puntoReso;

	@FXML
	private JFXTextArea txt_desReso;

	@FXML
	private JFXButton btn_elimReso;

	@FXML
	private JFXButton btn_addReso;

	@FXML
	private JFXButton btn_nuevoReso;

	@FXML
	private JFXButton btn_modReso;

	@FXML
	private AnchorPane panelReso;

	@FXML
	private TableView<?> tablaResoluciones;

	@FXML
	void activar_controles_pdf(MouseEvent event) {
		desbloquear_control_pdf();
	}

	@FXML
	void eli_lista_pdf(ActionEvent event) {
		if (list_pdf.getSelectionModel().selectedItemProperty().getValue() == null) {
			mostrarMesaje("No hay pdf para eliminar");
			btn_ver.setDisable(true);
			btn_eli_lista_pdf.setDisable(true);
		} else {
			lista_pdf.remove(list_pdf.getSelectionModel().getSelectedIndex());
			list_pdf.getItems().remove(list_pdf.getSelectionModel().getSelectedIndex());
			bloquear_control_pdf();
		}

	}

	@FXML
	void mostrarResoluciones(MouseEvent event) {

	}

	@FXML
	void mostrar_acta(MouseEvent event) {
		if (ruta_acta == "") {
			mostrarMesaje("No ha agregado un pdf para poder vizualizarlo");
		} else {
			try {
				System.out.println(ruta_acta);
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
	}

	@FXML
	void mostrar_pdf(ActionEvent event) {
		try {
			String ruta = "";
			if (list_pdf.getSelectionModel().selectedItemProperty().getValue() == null) {
				mostrarMesaje("no hay ningun pdf para ver");
				btn_ver.setDisable(true);
				btn_eli_lista_pdf.setDisable(true);
			} else {
				ruta = list_pdf.getSelectionModel().selectedItemProperty().getValue().getRuta_pdf();
				System.out.println(ruta);
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
			}

		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	static int numeroPunto;

	@FXML
	void mostrar_punto(MouseEvent event) throws IOException {
		

		if (tabla.getSelectionModel().selectedItemProperty().getValue() != null) {
			btn_resoluciones.setVisible(true);
			btn_modOrden.setDisable(false);
			btn_nuevo.setDisable(false);
			btn_elimOrden.setDisable(false);
			btn_addOrden.setDisable(true);
			PuntoOrden.setDisable(true);
			txt_descripcion.setDisable(false);
			cbx_proponente.setDisable(false);
			btn_examinar.setDisable(false);
			list_pdf.setDisable(false);
			id_punto_od = tabla.getSelectionModel().selectedItemProperty().getValue().getId();
			txt_descripcion.setText(tabla.getSelectionModel().selectedItemProperty().getValue().getTema());
			numeroPunto = tabla.getSelectionModel().selectedItemProperty().getValue().getNumeroPunto();
			PuntoOrden.setText(
					String.valueOf(tabla.getSelectionModel().selectedItemProperty().getValue().getNumeroPunto()));
			int id_pro = tabla.getSelectionModel().selectedItemProperty().getValue().getProponente();

			System.out.println("el ide proponete es " + id_pro);
			if (id_pro == 0) {
				cbx_proponente.setValue(null);
			} else {

				int log = proponentes.size();
				int bandera = 0;
				while (log >= 1) {
					if (id_pro == proponentes.get(bandera).getId()) {
						cbx_proponente.setValue(proponentes.get(bandera));
					}
					log--;
					bandera++;

				}
			}

			List<Pdf> pdf;
			System.out.println(id_punto + "  holaa");

			pdf = Pdf.consultarPDFS_Modificacion(id_punto_od);
			System.out.println(pdf.size() + "  longitud");
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
			clickTable = true;

		} else {
			mostrarMesaje("Por favor seleccione un punto");
		}

	}

	public String convertirPdf(byte[] bytes) throws IOException {
		String tmpDir = System.getProperty("java.io.tmpdir") + "\\tmp\\";
		String tmpFileName = UUID.randomUUID().toString();
		if (!new File(tmpDir).exists()) {
			if (!new File(tmpDir).mkdirs()) {
				System.out.print("Imposibe crear directorio temporal");
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

	@FXML
	void onActSesion(ActionEvent event) {
		activar();
		btn_elimSesion.setVisible(false);
		btn_ActSesion.setVisible(false);
		btn_modSesion.setVisible(true);
		lockPaneSesion();
	}

	@FXML
	void onAddOrden(ActionEvent event) {
		System.out.println("La convocatoria es: " + convocatoria);
		if (convocatoria == "") {
			mostrarMesaje("Primero tiene que agregar la sesión");
		} else {

			int longitud_lista = 0;
			longitud_lista = list_pdf.getItems().size();

			if (txt_descripcion.getLength() == 0) {
				mostrarMesaje("Falta ingresar la descripción del punto");
			} else {
				if (cbx_proponente.getValue() == null) {
					mostrarMesaje("No se a agredado Proponente " + "para el punto " + PuntoOrden.getText() + "");
				}
				if (longitud_lista == 0) {
					mostrarMesaje("No se a agredado documentación " + "para el punto " + PuntoOrden.getText() + "");
				}
				if (data.documentacion == 0) {
					int id = 0;
					OrdenDia orden;
					if (cbx_proponente.getValue() != null) {
						id = cbx_proponente.getValue().getId();
						orden = new OrdenDia(convocatoria, Integer.valueOf(PuntoOrden.getText()),
								txt_descripcion.getText(), id);

					} else {
						orden = new OrdenDia(convocatoria, Integer.valueOf(PuntoOrden.getText()),
								txt_descripcion.getText(), 0);

					}

					conexion.establecerConexion();
					idOrden = orden.guardarRegistro(conexion.getConnection());
					conexion.cerrarConexion();
					datos.add(orden);
					tabla.setDisable(false);
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

					

					PuntoOrden.setText(String.valueOf(datos.size()+1));
					lockControls();
					lockButtons();
					btn_cancelar.setVisible(true);
				}

			}
		}
	}

	@FXML
	void onAddReso(ActionEvent event) {

	}

	@FXML
	void onAddSesion(ActionEvent event) throws NotBoundException, IOException, SQLException {

		conexion.establecerConexion();
		// conecciï¿½n a la base de datos
		Connection db = conexion.getConnection();
		Statement st = db.createStatement();

		ResultSet resultado = st
				.executeQuery("SELECT convocatoria_sesion  FROM public.sesion_ve where convocatoria_sesion='"
						+ txt_convocatoria.getText() + "';");
		Statement st2 = db.createStatement();
		ResultSet resultado2 = null;
		if (date.getValue() != null) {
			resultado2 = st2
					.executeQuery("SELECT convocatoria_sesion  FROM public.sesion_ve where intervention_sesion='"
							+ date.getValue() + "';");

		}

		if (txt_convocatoria.getLength() == 0) {
			mostrarMesaje("Falta ingresar la convocatoria");
		} else if (date.getValue() == null) {
			mostrarMesaje("Falta selecionar la fecha de intervención");
		} else if (resultado.next()) {
			mostrarMesaje("La convocatoria " + txt_convocatoria.getText() + ", ya se encuentra agregada en el sistema");
		} else if (resultado2.next()) {
			mostrarMesaje("Ya se encuentra una sesión agregada en el sistema para la fecha " + date.getValue());
		} else if (pdf_acta.getItems().size() == 0) {
			mostrarMesaje("Falta agregar el acta de la sesión");
		} else if (time.getValue() == null) {
			mostrarMesaje("Seleccione la hora de la sesion");
		} else {
			String txtconvocatoria = txt_convocatoria.getText();
			String[] meses = { "ENERO", "ENERO", "FEBRERO", "MARZO", "ABRIL", "MAYO", "JUNIO", "JULIO", "AGOSTO",
					"SEPTIEMBRE", "OCTUBRE", "NOVIEMBRE", "DICIEMBRE" };
			String fechaCompleta = date.getValue().getDayOfMonth() + " DE " + meses[date.getValue().getMonthValue()]
					+ " DEL " + date.getValue().getYear();
			String horaIntervencion = time.getValue().toString();
			String titulo = "SESIÓN " + cbx_tipoSes.getValue()
					+ " DEL CONCEJO DEL GOBIERNO AUTÓNOMO DESCENTRALIZADO MUNICIPAL DEL CANTÓN MANTA, CORRESPONDIENTE AL DÍA "
					+ fechaCompleta + ", A lAS " + horaIntervencion + " EN EL SALON DE ACTOS DEL GADMC-MANTA";
			String tipo_sesion = cbx_tipoSes.getValue();
			Date fechaIntervencion = Date.valueOf(date.getValue());
			Date fechaRegistro = new Date(Calendar.getInstance().getTime().getTime());

			conexion.establecerConexion();
			ActaPdf pdf = new ActaPdf(nombre_acta, ruta_acta);
			idActa = pdf.guardarRegistro_pdf(conexion.getConnection());
			conexion.cerrarConexion();

			Sesion sesion = new Sesion(txtconvocatoria, titulo, tipo_sesion, fechaRegistro, fechaIntervencion,
					horaIntervencion, 0, idActa);
			conexion.establecerConexion();
			convocatoria = sesion.guardarRegistro(conexion.getConnection());
			System.out.println(convocatoria);
			conexion.cerrarConexion();

			if (convocatoria == "") {
				mostrarMesaje("No se pudo registrar la sesión");
			} else {

				mostrarMesaje("Ahora prodece a agregar los puntos del dia para la " + convocatoria);
				lockControlsAddSession();
				unLockPanels();

				// iniciandoEl ingreso de puntos
				startAddPoint();

			}

			conexion.cerrarConexion();
		}
	}

	@FXML
	void onElimOrden(ActionEvent event) {
		
		if(tabla.getSelectionModel().selectedItemProperty().getValue()!=null) {
			int id = tabla.getSelectionModel().selectedItemProperty().getValue().getId();
			OrdenDia modi = datos.stream().filter(p -> p.getId() == id).findFirst().get();
			datos.remove(modi);
			//tabla.refresh();
			
			String sql = "DELETE FROM ordendia_ve WHERE id_ordendia=" + id + ";";
			String sql2 = "DELETE FROM pdf_ve WHERE id_ordendia=" + id + ";";

			try {
				conexion.establecerConexion();
				// conecciï¿½n a la base de datos
				Connection db = conexion.getConnection();
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
			
			lockControls();
			lockButtons();
			PuntoOrden.setText(""+(datos.size()+1));
		}else {
			mostrarMesaje("Por favor seleccione un punto");
		}
		
	}

	@FXML
	void onElimReso(ActionEvent event) {

	}

	@FXML
	void onElimSesion(ActionEvent event) throws SQLException {
		conexion.establecerConexion();
		// conecciï¿½n a la base de datos
		Connection db = conexion.getConnection();
		Statement st = db.createStatement();
		ResultSet resultado = st.executeQuery(
				"SELECT id_pdf  FROM public.sesion_ve where convocatoria_sesion='" + txt_convocatoria.getText() + "';");

		if (resultado.next()) {

			String sql = "DELETE FROM public.acta_ve WHERE id_pdf=" + resultado.getInt(1) + ";";
			PreparedStatement instruccion = db.prepareStatement(sql);
			if (!instruccion.execute()) {
				mostrarMesaje("La sesión" + txt_convocatoria.getText() + " a sido eliminada correctamemte");
				activar();
				limpiar_sesion();
				lockPaneSesion();
				unLocksButtons();

			} else {
				mostrarMesaje("Hubo un error al eliminar sesión" + txt_convocatoria.getText() + "");
			}
		}
		conexion.cerrarConexion();
	}

	@FXML
	void onExaActa(ActionEvent event) {
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
	void onExaAction(ActionEvent event) {
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
				System.out.println(fichero.getName());
				Pdf pdf = new Pdf(fichero.getName(), dbpath);
				System.out.println("ruta " + pdf.getNombre());
				lista_pdf.add(pdf);
				contador++;

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
	void onFinAction(ActionEvent event) {

	}

	@FXML
	void onModOrden(ActionEvent event) throws IOException {

		int longitud_lista = 0;
		longitud_lista = list_pdf.getItems().size();

		if (txt_descripcion.getLength() == 0) {
			mostrarMesaje("Falta ingresar la descripciÃ³n del punto");
		} else {

			if (longitud_lista == 0) {
				mostrarMesaje("No se a agregado documentación " + "para el punto " + PuntoOrden.getText() + "");
			}
			if (data.documentacion == 0) {

				int proponente = 0;
				if (cbx_proponente.getValue() != null) {
					proponente = cbx_proponente.getValue().getId();
				}
				// modificamos la tabla
				int id = tabla.getSelectionModel().selectedItemProperty().getValue().getId();
				OrdenDia modi = datos.stream().filter(p -> p.getId() == id).findFirst().get();
				modi.setTema(txt_descripcion.getText());
				modi.setNumeroPunto(Integer.valueOf(PuntoOrden.getText()));
				modi.setProponente(proponente);

				// modificamos el orden del dia en la base de datos
				OrdenDia orden = new OrdenDia(modi.getNumeroPunto(), modi.getTema(), modi.getProponente());
				conexion.establecerConexion();
				orden.actualizarRegistro(conexion.getConnection(), id);
				conexion.cerrarConexion();
				tabla.refresh();

				Connection db;

				String sql2 = "DELETE FROM pdf_ve WHERE id_ordendia=" + id_punto_od + ";";
				String sql3 = "INSERT INTO pdf_ve(id_ordendia,nombre_pdf,archivo_pdf) VALUES (?, ?, ?);";

				try {
					db = DriverManager.getConnection("jdbc:postgresql:" + data_configuracion.nombre_bd + "",
							"" + data_configuracion.usu_db + "", "" + data_configuracion.conta_usu + "");

					PreparedStatement instruccion2 = db.prepareStatement(sql2);
					if (!(instruccion2.execute())) {
						mostrarMesaje("Este punto a sido atualizado");
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

					db.close();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

				lockControls();
				lockButtons();

			}

		}
	}

	@FXML
	void onModReso(ActionEvent event) {

	}

	@FXML
	void onModSesion(ActionEvent event) throws IOException {
		if (txt_convocatoria.getLength() == 0) {
			mostrarMesaje("Falta ingresar la convocatoria");
		} else if (date.getValue() == null) {
			mostrarMesaje("Falta selecionar la fecha de intervención");
		} else if (pdf_acta.getItems().size() == 0) {
			mostrarMesaje("Falta agregar el acta de la sesión");
		} else {

			String txtconvocatoria = txt_convocatoria.getText();
			String[] meses = { "ENERO", "ENERO", "FEBRERO", "MARZO", "ABRIL", "MAYO", "JUNIO", "JULIO", "AGOSTO",
					"SEPTIEMBRE", "OCTUBRE", "NOVIEMBRE", "DICIEMBRE" };
			String fechaCompleta = date.getValue().getDayOfMonth() + " DE " + meses[date.getValue().getMonthValue()]
					+ " DEL " + date.getValue().getYear();
			String horaIntervencion = time.getValue().toString();
			String titulo = "SESIÓN " + cbx_tipoSes.getValue()
					+ " DEL CONCEJO DEL GOBIERNO AUTÓNOMO DESCENTRALIZADO MUNICIPAL DEL CANTÓN MANTA, CORRESPONDIENTE AL DÍA "
					+ fechaCompleta + ", A lAS " + horaIntervencion + " EN EL SALON DE ACTOS DEL GADMC-MANTA";

			String tipo_sesion = cbx_tipoSes.getValue();
			Date fechaIntervencion = Date.valueOf(date.getValue());
			Date fechaRegistro = new Date(Calendar.getInstance().getTime().getTime());

			String sql = " UPDATE sesion_ve SET convocatoria_sesion='" + txtconvocatoria + "', description_sesion='"
					+ titulo + "', tipo_sesion='" + tipo_sesion + "', register_sesion='" + fechaRegistro + "',"
					+ " intervention_sesion='" + fechaIntervencion + "', hour_sesion='" + horaIntervencion
					+ "' WHERE convocatoria_sesion='" + convocatoria + "';";

			String sql2 = "UPDATE acta_ve SET nombre_pdf=?, archivo_pdf=? WHERE id_pdf=" + idActa + ";";

			try {
				conexion.establecerConexion();
				// conecciï¿½n a la base de datos
				Connection db = conexion.getConnection();
				PreparedStatement instruccion = db.prepareStatement(sql);

				File pdf = new File(ruta_acta);
				FileInputStream fis = new FileInputStream(pdf);
				PreparedStatement ps = db.prepareStatement(sql2);

				ps.setString(1, nombre_acta);
				ps.setBinaryStream(2, fis, (int) pdf.length());
				ps.execute();
				fis.close();

				if (!(instruccion.execute() && ps.execute())) {
					mostrarMesaje("La convocatoria a sido actualizada");
					if(datos.size()>=1) {
						tabla.setDisable(false);
					}
					lockControlsAddSession();
					unLockPanels();
				} else {
					mostrarMesaje("No se a podido actualizar la convocatoria");
				}

				conexion.cerrarConexion();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				// e1.printStackTrace();
				mostrarMesaje("Ya existe una convocatoria " + txtconvocatoria + ", en el sistema");
			}

		}
	}

	@FXML
	void onNewOrden(ActionEvent event) {
		if (clickTable == true) {
			btn_resoluciones.setVisible(false);
			PuntoOrden.setText("" + (datos.size() + 1));
			btn_elimOrden.setDisable(true);
			btn_modOrden.setDisable(true);
		}
		unlockControls();
		unlockButtonNewOrden();
	}

	@FXML
	void onNewReso(ActionEvent event) {

	}

	@FXML
	void onResoluciones(ActionEvent event) {
		panelAddPunto.setVisible(false);
		panelOrden.setVisible(false);
		panelReso.setVisible(true);
		panelAddReso.setVisible(true);
		panelReso.setLayoutX(14);
		panelReso.setLayoutY(235);
		panelAddReso.setLayoutX(336);
		panelAddReso.setLayoutY(235);
		lbl_puntoReso.setText(String.valueOf(numeroPunto));
		System.out.println("el id del punto es"+id_punto_od);
	}

	@FXML
	void onVolver(ActionEvent event) {
		if(datos.size()>=1) {
			tabla.setDisable(false);
		}
		panelAddPunto.setVisible(true);
		panelOrden.setVisible(true);
		panelReso.setVisible(false);
		panelAddReso.setVisible(false);
	}

	@FXML
	void validar_punto(KeyEvent event) {

	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		conexion = new Conexion();
		txt_convocatoria.requestFocus();
		lockButtonsSession();
		lockPaneSesion();
		noVisiblePanelReso();
		llenarCbxTipoSesion();
		// inicializamos la listaPdf
		iniciarPdfList();
		// inicializamos la lista de orden del dia
		llenarTableOrden();
	}

	@SuppressWarnings("unchecked")
	private void iniciarPdfList() {
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
	}

	private void lockButtonsSession() {
		btn_ActSesion.setVisible(false);
		btn_modSesion.setVisible(false);
	}

	private void lockPaneSesion() {
		panelAddPunto.setDisable(true);
		panelOrden.setDisable(true);
		panelAddReso.setDisable(true);
		panelReso.setDisable(true);

	}

	private void noVisiblePanelReso() {
		panelReso.setVisible(false);
		panelAddReso.setVisible(false);
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

	private void llenarCbxTipoSesion() {
		cbx_tipoSes.setValue("ORDINARIA");
		cbx_tipoSes.setItems(tipoSesion);
	}

	public void lockControlsAddSession() {
		txt_convocatoria.setDisable(true);
		cbx_tipoSes.setDisable(true);
		date.setDisable(true);
		time.setDisable(true);
		btn_addSesion.setVisible(false);
		btn_examinarActa.setDisable(true);
		btn_ActSesion.setVisible(true);
		btn_elimSesion.setVisible(true);
		btn_modSesion.setVisible(false);
		if(datos.size()>=1) {
			tabla.setDisable(false);
		}else {
			tabla.setDisable(true);
		}
	}

	public void unLockPanels() {
		panelAddPunto.setDisable(false);
		panelOrden.setDisable(false);
		panelReso.setDisable(false);
		panelAddReso.setDisable(false);
	}

	public void activar() {
		txt_convocatoria.setDisable(false);
		cbx_tipoSes.setDisable(false);
		date.setDisable(false);
		time.setDisable(false);
		btn_examinarActa.setDisable(false);
		btn_modSesion.setDisable(false);
	}

	public void limpiar_sesion() {
		ruta_acta = "";
		nombre_acta = "";
		// Para guardar el acta
		idActa = 0;
		// para Agregar los puntos del orden del dia
		convocatoria = "";
		txt_convocatoria.setText(null);
		date.setValue(null);
		pdf_acta.getItems().clear();
		time.setValue(null);

	}

	public void unLocksButtons() {
		// btn_elimSesion.setVisible(false);
		btn_modSesion.setVisible(false);
		btn_ActSesion.setVisible(false);
		btn_addSesion.setVisible(true);
	}

	private void llenarCbxProponente() {
		conexion.establecerConexion();
		proponentes = FXCollections.observableArrayList();
		// System.out.println(System.getProperty("java.io.tmpdir"));
		Sesion.llenarInformacion(conexion.getConnection(), proponentes);
		cbx_proponente.setItems(proponentes);
		conexion.cerrarConexion();
	}

	private void startAddPoint() {
		llenarCbxProponente();
		lockControls();
		lockButtons();
	}

	private void lockControls() {
		// limpiando los intuts
		txt_descripcion.setText("");
		cbx_proponente.setValue(null);
		list_pdf.getItems().clear();
		lista_pdf.clear();
		txt_descripcion.setDisable(true);
		list_pdf.setDisable(true);
		// txt_numpunto
		PuntoOrden.setDisable(true);
		PuntoOrden.setText(String.valueOf(datos.size()+1));
		cbx_proponente.setDisable(true);
		btn_examinar.setDisable(true);
		btn_ver.setDisable(true);
		btn_eli_lista_pdf.setDisable(true);
	}

	private void lockButtons() {
		btn_resoluciones.setVisible(false);
		btn_elimOrden.setDisable(true);
		btn_modOrden.setDisable(true);
		btn_addOrden.setDisable(true);
		btn_nuevo.setDisable(false);
	}

	private void unlockControls() {
		txt_descripcion.setText("");
		cbx_proponente.setValue(null);
		list_pdf.getItems().clear();
		lista_pdf.clear();
		txt_descripcion.setDisable(false);
		txt_descripcion.requestFocus();
		cbx_proponente.setDisable(false);
		btn_examinar.setDisable(false);
		list_pdf.setDisable(false);
	}

	private void unlockButtonNewOrden() {
		btn_nuevo.setDisable(true);
		btn_addOrden.setDisable(false);
	}

	public void desbloquear_control_pdf() {
		btn_ver.setDisable(false);
		btn_eli_lista_pdf.setDisable(false);
	}

	public void bloquear_control_pdf() {
		btn_ver.setDisable(true);
		btn_eli_lista_pdf.setDisable(true);
	}

	@SuppressWarnings("unchecked")
	private void llenarTableOrden() {
		List<OrdenDia> listaOrden = FXCollections.observableArrayList();
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
		datos = FXCollections.observableArrayList(listaOrden);
		tabla.getColumns().addAll(id_punto, num_punto, descrip);
		tabla.setItems(datos);
	}
	
}
