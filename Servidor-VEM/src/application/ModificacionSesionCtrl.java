package application;

import java.awt.Desktop;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
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
import java.sql.Date;
import java.util.Calendar;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Logger;

import javax.swing.JOptionPane;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTimePicker;

import gad.manta.common.ActaPdf;
import gad.manta.common.Conexion;
import gad.manta.common.IServidor;
import gad.manta.common.OrdenDia;
import gad.manta.common.Pdf;
import gad.manta.common.Sesion;
import gad.manta.common.Usuario;
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
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;


public class ModificacionSesionCtrl implements Initializable{
	/**
	 * 
	 */

	private static IServidor servidor;
	
	public static String convocatoria_sesion = null;
	public static Integer idOrden = 0;
	public static String ruta_pdf = "";
	ObservableList<String> tipoSesion = FXCollections.
			observableArrayList("ORDINARIA","EXTRAORDINARIA");
	
	private Conexion conexion;
	
	@FXML
    private ObservableList<Usuario> proponentes;

    private ObservableList<Sesion> convocatoria;
	
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
    private JFXComboBox<Sesion> cbx_combocatoria;
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
    
    @FXML
    private JFXListView<String> list_pdf;
    
    
    @Override
	public void initialize(URL location, ResourceBundle resources) {
		//date.setConverter(new LocalDateStringConverter(FormatStyle.FULL));
    	conexion = new Conexion();
    	time.setValue(LocalTime.of(16, 00));
		cbx_tipoSes.setValue("ORDINARIA");
		cbx_tipoSes.setItems(tipoSesion);		
		
		conexion.establecerConexion();
		proponentes =FXCollections.observableArrayList();
		Sesion.llenarInformacion(conexion.getConnection(), proponentes);
		System.out.println(proponentes);
		cbx_proponente.setItems(proponentes);
		cbx_proponente.setValue(proponentes.get(0));
		conexion.cerrarConexion();
		
		conexion.establecerConexion();
		convocatoria=FXCollections.observableArrayList();
		Sesion.llenarInformacion_sesion(conexion.getConnection(), convocatoria);
		System.out.println(convocatoria);
		cbx_combocatoria.setItems(convocatoria);
		
		conexion.cerrarConexion();

		try {
			servidor = (IServidor)Naming.lookup("rmi://192.168.1.6/VotoE");
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
		
		
	}
    @SuppressWarnings("unchecked")
	@FXML
    void llenar_sesion(ActionEvent  event) throws RemoteException{
    	
			String a = cbx_combocatoria.getValue().getConvocatoria(); 
	    	List<gad.manta.common.Sesion>lista_sesion=servidor.consultarSesion_Modificacion(a);
	    	cbx_tipoSes.setValue(lista_sesion.get(0).getTipo_sesion());
	    	date.setValue(lista_sesion.get(0).getFechaIntervencion().toLocalDate());
	    	time.setValue(LocalTime.parse(lista_sesion.get(0).getHoraIntervencion()));
	    	
	    	List<OrdenDia>lista_orden=servidor.consultarOrden_Modificacion(a);
	    	
	    	@SuppressWarnings("rawtypes")
			TableColumn id_punto = new TableColumn("No. Punto");
	    	id_punto.setMinWidth(50);
	    	id_punto.setVisible(false);
	    	id_punto.setCellValueFactory(
	                new PropertyValueFactory<>("id"));
			
			@SuppressWarnings("rawtypes")
			TableColumn num_punto = new TableColumn("No. Punto");
			num_punto.setMinWidth(100);
			num_punto.setCellValueFactory(
	                new PropertyValueFactory<>("numeroPunto"));
	        ObservableList<OrdenDia> datos = FXCollections.observableArrayList(
					lista_orden
					);
			
			tabla.getColumns().addAll(id_punto,num_punto);
			tabla.setItems(datos);
			System.out.println(datos);
			
			
    }
    @SuppressWarnings("unlikely-arg-type")
	@FXML
    public void mostrar_punto(MouseEvent  event){
   
			List<OrdenDia> punto;
			List<Pdf> pdf;
			try {
				punto = servidor.consultarPunto_Modificacion(tabla.getSelectionModel().selectedItemProperty().getValue().getId());
				txt_descripcion.setText(punto.get(0).getTema());
				PuntoOrden.setText(String.valueOf(punto.get(0).getNumeroPunto()));
				int log= proponentes.size();
				int bandera=0;
				while(log>=1) {
					if(punto.get(0).getProponente()==proponentes.get(bandera).getId()) {
						cbx_proponente.setValue(proponentes.get(bandera));
					}
					log--;	
					bandera++;
				
				}
				pdf=servidor.consultarPDFS_Modificacion(tabla.getSelectionModel().selectedItemProperty().getValue().getId());
				
				int log_pdf=pdf.size();
				System.out.println(log_pdf);
				int bandera_2=0;
				list_pdf.getItems().clear();
				while(log_pdf>0) {	
					list_pdf.getItems().add(pdf.get(bandera_2).getNombre());;
					log_pdf--;
					bandera++;
				}
				
				
				
				
				
				
				
				
			
				
				
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
        	
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
    	cbx_proponente.setValue(null);
    	rutapdf="";
    	list_pdf.getItems().clear();
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
    	
    	//Sesion sesion = new Sesion(fechaRegistro, fechaIntervencion , horaIntervencion, convocatoria, titulo);
    	conexion.establecerConexion();
    	//idSesion = sesion.guardarRegistro(conexion.getConnection());
    	conexion.cerrarConexion();
    	
    	if(convocatoria_sesion ==null ) {
    		Alert mensaje = new Alert(AlertType.ERROR);
    		mensaje.setTitle("Sesion Guardada");
    		mensaje.setContentText("Hubo algun error");
    		mensaje.setHeaderText("Sesion Guardada");
    		mensaje.show();	
    	}else {
    		Alert mensaje = new Alert(AlertType.INFORMATION);
    		mensaje.setTitle("Sesion Guardada");
    		mensaje.setContentText("Ahora prodece a agregar la orden del dia "+convocatoria_sesion);
    		mensaje.setHeaderText("Sesion Guardada");
    		mensaje.show();
    		bloquear();
    		
    		listaOrden =FXCollections.observableArrayList();
    		conexion.establecerConexion();
        	
    		OrdenDia.llenarInformacion(conexion.getConnection(), listaOrden,convocatoria_sesion);
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
    			if (!folder.exists()) {
    				folder.mkdir();	
    			}
    			File fichero= new File(dbpath);
    			if(!fichero.exists()) {			
    				//agrega al lisview
    				//list_pdf.getItems().add(dbpath);
    				//copia el archivo al directorio
    				CopyOption[] opciones= new CopyOption[] {
        					StandardCopyOption.REPLACE_EXISTING,
        					StandardCopyOption.COPY_ATTRIBUTES
        			};
        			Files.copy(FROM, TO,opciones);
    			}else {
    				System.out.println("Ya existe el fichero  "+ fichero.getName());
    			}
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
    	
    	System.out.println(	);
    	System.out.println("aa");
    }
    
    @FXML
    void onAddOrden(ActionEvent event) throws IOException {
    	
    	System.out.println("El id de la sesion es: "+convocatoria_sesion);
    	if(convocatoria_sesion==null) {
    		JOptionPane.showMessageDialog(null, "Primero Tienes que agregar la sesion");
    	}else {
    		System.out.println(PuntoOrden.getText());
    		System.out.println(txt_descripcion.getText());
    		System.out.println(rutapdf);
    		System.out.println(cbx_proponente.getValue().getId());
    		System.out.println(convocatoria_sesion);
    		
    		OrdenDia sesion = new OrdenDia(convocatoria_sesion,Integer.valueOf(PuntoOrden.getText()), txt_descripcion.getText(), cbx_proponente.getValue().getId());
        	conexion.establecerConexion();
        	idOrden=sesion.guardarRegistro(conexion.getConnection());
        	
        	
        	conexion.establecerConexion();
        	int longitud_lista=0;
        	longitud_lista=list_pdf.getItems().size();
        
        	while(longitud_lista>0) {
        		System.out.println(list_pdf.getItems().get(longitud_lista-1).toString());
        		ActaPdf pdf = new ActaPdf(idOrden,list_pdf.getItems().get(longitud_lista-1).toString());
            	pdf.guardarRegistro_pdf(conexion.getConnection());
        		longitud_lista--;
        	}
        	conexion.cerrarConexion();
        	
        	limpiar();
        	
        	listaOrden =FXCollections.observableArrayList();
    		conexion.establecerConexion();
        	
    		OrdenDia.llenarInformacion(conexion.getConnection(), listaOrden,convocatoria_sesion);
    		conexion.cerrarConexion();
    		
    		tabla.setItems(listaOrden);
    		
    		punto.setCellValueFactory(new PropertyValueFactory<OrdenDia, String>("numeroPunto"));	
    	}
    }
    
    
}
