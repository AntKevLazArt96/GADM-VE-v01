package application;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.time.LocalTime;
import java.sql.Date;
import java.util.Calendar;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;


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
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.FileChooser.ExtensionFilter;




public class NuevaSesionCtrl implements Initializable{
	static IServidor servidor;
	
	public static String convocatoria = "";
	public static Integer idPdf = 0;
	public static Integer idActa = 0;
	public static Integer idOrden = 0;
	public static String ruta_acta = "";
	public static String nombre_acta = "";
	ObservableList<String> tipoSesion = FXCollections.
			observableArrayList("ORDINARIA","EXTRAORDINARIA");
	
	private Conexion conexion;
	
	@FXML
    private ObservableList<Usuario> proponentes;
	
	@FXML
    private ObservableList<OrdenDia> listaOrden;
	
    @FXML
    private AnchorPane panel;
    @FXML
    private JFXDatePicker date;
    
    @FXML
    private JFXTimePicker time;
    
    @FXML
    private JFXComboBox<String> cbx_tipoSes;
    
    @FXML
    private JFXComboBox<Usuario> cbx_proponente;
    
    @FXML
    private JFXTextField PuntoOrden;
    
    @FXML
    private Label lbl_file;
    
    @FXML
    private Label lbl1;
    @FXML
    private Label lbl2;


    @FXML
    private Label lbl3;

    @FXML
    private Label lbl4;
    @FXML
    private JFXTextField txt_convocatoria;
    
    @FXML
    private JFXTextArea txt_descripcion;
    
    @FXML
    private TableView<OrdenDia> tabla;
    
    @FXML
    private TableColumn<OrdenDia,String> punto;
    @FXML
    private TableColumn<OrdenDia,String> descripcion;
    
    @FXML
    private JFXButton btn_addSesion;
    

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
    private JFXListView<String> list_pdf;
    
    @FXML
    private JFXListView<String> pdf_acta;
    
    
    @Override
	public void initialize(URL location, ResourceBundle resources) {
		//date.setConverter(new LocalDateStringConverter(FormatStyle.FULL));
    	conexion = new Conexion();
    	time.setValue(LocalTime.of(16, 00));
		cbx_tipoSes.setValue("ORDINARIA");
		cbx_tipoSes.setItems(tipoSesion);		
		PuntoOrden.setDisable(true);
		conexion.establecerConexion();
		proponentes =FXCollections.observableArrayList();
		
		Sesion.llenarInformacion(conexion.getConnection(), proponentes);

		cbx_proponente.setItems(proponentes);
		
		conexion.cerrarConexion();
		
		
		
	}
    
    public void bloquear() {
    	txt_convocatoria.setDisable(true);
    	cbx_tipoSes.setDisable(true);
    	date.setDisable(true);
    	time.setDisable(true);
    	btn_addSesion.setVisible(false);
    }
    public void limpiar() {
    	int punto=0;
    	punto=(Integer.parseInt(PuntoOrden.getText())+1);
    	PuntoOrden.setText(Integer.toString(punto));
    	txt_descripcion.setText(null);
    	cbx_proponente.setValue(null);
    	rutapdf="";
    	list_pdf.getItems().clear();
    }
    
    
    
    @FXML
    void onAddSesion(ActionEvent event) throws MalformedURLException, RemoteException, NotBoundException {
    	String txtconvocatoria = txt_convocatoria.getText();
    	String [] meses = {"ENERO","ENERO","FEBRERO","MARZO","ABRIL","MAYO","JUNIO","JULIO","AGOSTO","SEPTIEMBRE","OCTUBRE","NOVIEMBRE","DICIEMBRE"};
    	String fechaCompleta = date.getValue().getDayOfMonth()+" DE "+meses[date.getValue().getMonthValue()]+" DEL "+date.getValue().getYear();
    	String horaIntervencion = time.getValue().toString();
    	String titulo = lbl1.getText()+" "+cbx_tipoSes.getValue()+lbl2.getText()+" "+lbl3.getText()+fechaCompleta+", A lAS "+horaIntervencion+" "+lbl4.getText();
    	String tipo_sesion = cbx_tipoSes.getValue();
    	Date fechaIntervencion = Date.valueOf(date.getValue());
    	Date fechaRegistro = new Date(Calendar.getInstance().getTime().getTime());
    	
    	
		try {
			conexion.establecerConexion();
			ActaPdf pdf = new ActaPdf(nombre_acta,ruta_acta);
			idActa= pdf.guardarRegistro_pdf(conexion.getConnection());
			conexion.cerrarConexion();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
    	
		Sesion sesion = new Sesion(txtconvocatoria,titulo,tipo_sesion,fechaRegistro, fechaIntervencion , horaIntervencion,idActa );
    	conexion.establecerConexion();
    	convocatoria = sesion.guardarRegistro(conexion.getConnection());
    	System.out.println(convocatoria);
    	conexion.cerrarConexion();
    	
    	if(convocatoria =="" ) {
    		Alert mensaje = new Alert(AlertType.ERROR);
    		mensaje.setTitle("Sesion Guardada");
    		mensaje.setContentText("Hubo algun error");
    		mensaje.setHeaderText("Sesion Guardada");
    		mensaje.show();	
    	}else {
    		Alert mensaje = new Alert(AlertType.INFORMATION);
    		mensaje.setTitle("Sesion Guardada");
    		mensaje.setContentText("Ahora prodece a agregar la orden del dia "+convocatoria);
    		mensaje.setHeaderText("Sesion Guardada");
    		mensaje.show();
    		bloquear();
    		
    		listaOrden =FXCollections.observableArrayList();
    		conexion.establecerConexion();
        	
    		OrdenDia.llenarInformacion(conexion.getConnection(), listaOrden,convocatoria);
    		conexion.cerrarConexion();
    		
    		tabla.setItems(listaOrden);
    		
    		punto.setCellValueFactory(new PropertyValueFactory<OrdenDia, String>("numeroPunto"));
    		descripcion.setCellValueFactory(new PropertyValueFactory<OrdenDia, String>("tema"));
    		
    		
    	
    	}
    	
    	
    	
    
    	
    	
    }
    @FXML
    void onModSesion(ActionEvent event) throws MalformedURLException, RemoteException, NotBoundException {
    	String txtconvocatoria = txt_convocatoria.getText();
    	String [] meses = {"ENERO","ENERO","FEBRERO","MARZO","ABRIL","MAYO","JUNIO","JULIO","AGOSTO","SEPTIEMBRE","OCTUBRE","NOVIEMBRE","DICIEMBRE"};
    	String fechaCompleta = date.getValue().getDayOfMonth()+" DE "+meses[date.getValue().getMonthValue()]+" DEL "+date.getValue().getYear();
    	String horaIntervencion = time.getValue().toString();
    	String titulo = lbl1.getText()+" "+cbx_tipoSes.getValue()+lbl2.getText()+" "+lbl3.getText()+fechaCompleta+", A lAS "+horaIntervencion+" "+lbl4.getText();
    	String tipo_sesion = cbx_tipoSes.getValue();
    	Date fechaIntervencion = Date.valueOf(date.getValue());
    	Date fechaRegistro = new Date(Calendar.getInstance().getTime().getTime());
    	
    	
		try {
			conexion.establecerConexion();
			ActaPdf pdf = new ActaPdf(nombre_acta,ruta_acta);
			idActa= pdf.guardarRegistro_pdf(conexion.getConnection());
			conexion.cerrarConexion();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
    	
		Sesion sesion = new Sesion(txtconvocatoria,titulo,tipo_sesion,fechaRegistro, fechaIntervencion , horaIntervencion,idActa );
    	conexion.establecerConexion();
    	convocatoria = sesion.guardarRegistro(conexion.getConnection());
    	System.out.println(convocatoria);
    	conexion.cerrarConexion();
    	
    	if(convocatoria =="" ) {
    		Alert mensaje = new Alert(AlertType.ERROR);
    		mensaje.setTitle("Sesion Guardada");
    		mensaje.setContentText("Hubo algun error");
    		mensaje.setHeaderText("Sesion Guardada");
    		mensaje.show();	
    	}else {
    		Alert mensaje = new Alert(AlertType.INFORMATION);
    		mensaje.setTitle("Sesion Guardada");
    		mensaje.setContentText("Ahora prodece a agregar la orden del dia "+convocatoria);
    		mensaje.setHeaderText("Sesion Guardada");
    		mensaje.show();
    		bloquear();
    		
    		listaOrden =FXCollections.observableArrayList();
    		conexion.establecerConexion();
        	
    		OrdenDia.llenarInformacion(conexion.getConnection(), listaOrden,convocatoria);
    		conexion.cerrarConexion();
    		
    		tabla.setItems(listaOrden);
    		
    		punto.setCellValueFactory(new PropertyValueFactory<OrdenDia, String>("numeroPunto"));
    		descripcion.setCellValueFactory(new PropertyValueFactory<OrdenDia, String>("tema"));
    		
    		
    	
    	}
    	
    	
    	
    
    	
    	
    }
    
    
    @FXML
    void onFinAction(ActionEvent event) {
    	try {
    		AnchorPane pane = (AnchorPane)FXMLLoader.load(getClass().getResource("subSesiones.fxml"));
			
			panel.getChildren().setAll(pane);
		    
    	} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
//variable global para guardar el path del pdf
    private static String rutapdf="";
    @FXML
    void onExaAction(ActionEvent event) throws IOException {
    	
    	try {
        	FileChooser fc = new FileChooser();
        	//fc.setInitialDirectory(new File(""));
        	fc.getExtensionFilters().addAll(new ExtensionFilter("PDF Files","*.pdf"));
        	File selectedf = fc.showOpenDialog(null);
        	if(selectedf!=null) {
        		String filePath = "C:\\Documentos-GAD_VOTO";
    			String dbpath = "C:\\Documentos-GAD_VOTO\\" + selectedf.getName();
    			//origen
    			Path FROM = Paths.get(selectedf.getAbsolutePath());
    			//destino
    			Path TO = Paths.get(dbpath);
    			//crea en dirrectorio si no existe
    			File folder = new File(filePath);
    			//pdf.guardarRegistro_pdf(conexion.getConnection());
    			if (!folder.exists()) {
    				folder.mkdir();	
    			}
    			File fichero= new File(dbpath);
    				
    				//agrega al lisview
    				list_pdf.getItems().add(dbpath);
    				//copia el archivo al directorio
    				CopyOption[] opciones= new CopyOption[] {
        					StandardCopyOption.REPLACE_EXISTING,
        					StandardCopyOption.COPY_ATTRIBUTES
        			};
        			Files.copy(FROM, TO,opciones);
    			
    			
        	}
		} catch (NullPointerException nl) {
			nl.printStackTrace();
		}
		catch (Exception ex) {
			ex.printStackTrace();
		}
    }
    
    
    @FXML
    void onExaActa(ActionEvent event) throws IOException {
    	String dbpath="";
    	try {
        	FileChooser fc = new FileChooser();
        	//fc.setInitialDirectory(new File(""));
        	fc.getExtensionFilters().addAll(new ExtensionFilter("PDF Files","*.pdf"));
        	File selectedf = fc.showOpenDialog(null);
        	if(selectedf!=null) {
        		String filePath = "C:\\Documentos-GAD_VOTO";
    			dbpath = "C:\\Documentos-GAD_VOTO\\" + selectedf.getName();
    			//origen
    			Path FROM = Paths.get(selectedf.getAbsolutePath());
    			//destino
    			Path TO = Paths.get(dbpath);
    			//crea en dirrectorio si no existe
    			File folder = new File(filePath);
    			
    			if (!folder.exists()) {
    				folder.mkdir();	
    			}
    			File fichero= new File(dbpath);
    				
    				//agrega al lisview
					pdf_acta.getItems().clear();
    				pdf_acta.getItems().add(selectedf.getName());
    				//copia el archivo al directorio
    				CopyOption[] opciones= new CopyOption[] {
        					StandardCopyOption.REPLACE_EXISTING,
        					StandardCopyOption.COPY_ATTRIBUTES
        			};
        			Files.copy(FROM, TO,opciones);
        			ruta_acta = dbpath;
        			nombre_acta= fichero.getPath();
    			
        	}
		} catch (NullPointerException nl) {
			nl.printStackTrace();
		}
		catch (Exception ex) {
			ex.printStackTrace();
		}
    }
    @FXML
    void mostrar_pdf(MouseEvent  event) throws RemoteException{
    	
    	try {
    		String ruta="";
        	ruta=list_pdf.getSelectionModel().selectedItemProperty().getValue();
    	    System.out.println(ruta);
        	File archivo = new File (ruta);
        	data.archivo_pff=archivo;
    		data.tipo_lectura=3;
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

    	}catch (IOException ex) {
    	     ex.printStackTrace();
    	}
    }
    
    @FXML
    void mostrar_acta(MouseEvent  event) throws RemoteException{
    	
    	try {
    		
    		System.out.println(ruta_acta);
    		File archivo = new File (ruta_acta);
    		data.archivo_pff=archivo;
    		data.tipo_lectura=3;
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

    		
    		
    	}catch (IOException ex) {
    	     ex.printStackTrace();
    	}
    	
    	

    	
    }
    
    @FXML
    void onAddOrden(ActionEvent event) throws IOException {
    	
    	System.out.println("La convocatoria es: "+convocatoria);
    	if(convocatoria=="") {
    		JOptionPane.showMessageDialog(null, "Primero Tienes que agregar la sesion");
    	}else {
    		System.out.println(PuntoOrden.getText());
    		System.out.println(txt_descripcion.getText());
    		System.out.println(rutapdf);
    		System.out.println(cbx_proponente.getValue().getId());
    		System.out.println(convocatoria);
    		
    		OrdenDia orden = new OrdenDia(convocatoria,Integer.valueOf(PuntoOrden.getText()), txt_descripcion.getText(), cbx_proponente.getValue().getId());
        	conexion.establecerConexion();
        	idOrden=orden.guardarRegistro(conexion.getConnection());
        	
        	
        	conexion.establecerConexion();
        	int longitud_lista=0;
        	longitud_lista=list_pdf.getItems().size();
        
        	while(longitud_lista>0)
				try {
					{
						System.out.println(list_pdf.getItems().get(longitud_lista-1).toString());
						Pdf pdf = new Pdf(idOrden,list_pdf.getItems().get(longitud_lista-1).toString());
						pdf.guardarRegistro_pdfs(conexion.getConnection());
						longitud_lista--;
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
        	conexion.cerrarConexion();
        	
        	limpiar();
        	
        	listaOrden =FXCollections.observableArrayList();
    		conexion.establecerConexion();
        	
    		OrdenDia.llenarInformacion(conexion.getConnection(), listaOrden,convocatoria);
    		conexion.cerrarConexion();
    		
    		tabla.setItems(listaOrden);
    		
    		punto.setCellValueFactory(new PropertyValueFactory<OrdenDia, String>("numeroPunto"));	
    	}
    }
    
    @FXML
    public void onModOrden(ActionEvent event) {
    	
    }
}

