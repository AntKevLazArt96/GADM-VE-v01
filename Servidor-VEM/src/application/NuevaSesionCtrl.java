package application;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
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
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTimePicker;

import gad.manta.common.IServidor;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import modelo.Conexion;
import modelo.OrdenDia;
import modelo.Sesion;
import modelo.Usuario;

public class NuevaSesionCtrl implements Initializable{
	private static IServidor servidor;
	
	public static int idSesion = 0;
	
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
    private JFXButton btn_addOrden;
    
    
    
    @Override
	public void initialize(URL location, ResourceBundle resources) {
		//date.setConverter(new LocalDateStringConverter(FormatStyle.FULL));
    	conexion = new Conexion();
    	time.setValue(LocalTime.of(16, 00));
		cbx_tipoSes.setValue("ORDINARIA");
		cbx_tipoSes.setItems(tipoSesion);		
		
		conexion.establecerConexion();
		proponentes =FXCollections.observableArrayList();
		
		Usuario.llenarInformacion(conexion.getConnection(), proponentes);

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
    	PuntoOrden.setText(null);
    	txt_descripcion.setText(null);
    	lbl_file.setText(null);
    	cbx_proponente.setValue(null);
    	rutapdf="";
    }
    
    
    
    @FXML
    void onAddSesion(ActionEvent event) throws MalformedURLException, RemoteException, NotBoundException {
    	String convocatoria = txt_convocatoria.getText();
    	String [] meses = {"ENERO","ENERO","FEBRERO","MARZO","ABRIL","MAYO","JUNIO","JULIO","AGOSTO","SEPTIEMBRE","OCTUBRE","NOVIEMBRE","DICIEMBRE"};
    	String fechaCompleta = date.getValue().getDayOfMonth()+" DE "+meses[date.getValue().getMonthValue()]+" DEL "+date.getValue().getYear();
    	String horaIntervencion = time.getValue().toString();
    	String titulo = lbl1.getText()+" "+cbx_tipoSes.getValue()+lbl2.getText()+" "+lbl3.getText()+fechaCompleta+", A lAS "+horaIntervencion+" "+lbl4.getText();
    	Date fechaIntervencion = Date.valueOf(date.getValue());
    	Date fechaRegistro = new Date(Calendar.getInstance().getTime().getTime());
    	
    	//int idsesion = servidor.agregarSesion(fechaRegistro, fechaIntervencion, horaIntervencion, convocatoria, titulo);
    	
    	Sesion sesion = new Sesion(fechaRegistro, fechaIntervencion , horaIntervencion, convocatoria, titulo);
    	conexion.establecerConexion();
    	idSesion = sesion.guardarRegistro(conexion.getConnection());
    	conexion.cerrarConexion();
    	
    	if(idSesion ==0 ) {
    		Alert mensaje = new Alert(AlertType.ERROR);
    		mensaje.setTitle("Sesion Guardada");
    		mensaje.setContentText("Hubo algun erro");
    		mensaje.setHeaderText("Sesion Guardada");
    		mensaje.show();	
    	}else {
    		Alert mensaje = new Alert(AlertType.INFORMATION);
    		mensaje.setTitle("Sesion Guardada");
    		mensaje.setContentText("Ahora prodece a agregar la orden del dia "+idSesion);
    		mensaje.setHeaderText("Sesion Guardada");
    		mensaje.show();
    		bloquear();
    		
    		listaOrden =FXCollections.observableArrayList();
    		conexion.establecerConexion();
        	
    		OrdenDia.llenarInformacion(conexion.getConnection(), listaOrden,idSesion);
    		conexion.cerrarConexion();
    		
    		tabla.setItems(listaOrden);
    		
    		punto.setCellValueFactory(new PropertyValueFactory<OrdenDia, String>("numeroPunto"));
    		descripcion.setCellValueFactory(new PropertyValueFactory<OrdenDia, String>("tema"));
    		
    		
    	
    	}
    	
    	
    	
    
    	
    	
    }
    @FXML
    void onCancelAction(ActionEvent event) {
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
    void onExaAction(ActionEvent event) {
    	System.out.println("Examinar");
    	FileChooser fc = new FileChooser();
    	//fc.setInitialDirectory(new File(""));
    	fc.getExtensionFilters().addAll(new ExtensionFilter("PDF Files","*.pdf"));
    	File selectedf = fc.showOpenDialog(null);
    	if(selectedf!=null) {
    		lbl_file.setText(selectedf.getName());
    		rutapdf = selectedf.getAbsolutePath();
    	}
    	
    }
    
    @FXML
    void onAddOrden(ActionEvent event) throws RemoteException {
    	
    	System.out.println("El id de la sesion es: "+idSesion);
    	if(idSesion==0) {
    		JOptionPane.showMessageDialog(null, "Primero Tienes que agregar la sesion");
    	}else {
    		System.out.println(PuntoOrden.getText());
    		System.out.println(txt_descripcion.getText());
    		System.out.println(rutapdf);
    		System.out.println(cbx_proponente.getValue().getId());
    		System.out.println(idSesion);
    		
    		OrdenDia sesion = new OrdenDia(idSesion,Integer.valueOf(PuntoOrden.getText()), txt_descripcion.getText() , rutapdf, cbx_proponente.getValue().getId());
        	conexion.establecerConexion();
        	sesion.guardarRegistro(conexion.getConnection());
        	conexion.cerrarConexion();
        	limpiar();
        	
        	listaOrden =FXCollections.observableArrayList();
    		conexion.establecerConexion();
        	
    		OrdenDia.llenarInformacion(conexion.getConnection(), listaOrden,idSesion);
    		conexion.cerrarConexion();
    		
    		tabla.setItems(listaOrden);
    		
    		punto.setCellValueFactory(new PropertyValueFactory<OrdenDia, String>("numeroPunto"));
    		
    	}
    	
    }
    
    
}
