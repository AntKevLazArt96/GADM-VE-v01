package application;

import java.awt.Desktop;
import java.io.File;
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
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.time.LocalTime;
import java.sql.Date;
import java.util.Calendar;
import java.util.List;
import java.util.ResourceBundle;
import java.util.UUID;

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
    private TableColumn<OrdenDia,String> id_punto;
    @FXML
    private TableColumn<OrdenDia,String> punto;
    @FXML
    private TableColumn<OrdenDia,String> descripcion;
    
    
    @FXML
    private TableColumn<OrdenDia,String> nombre_pdf;
    @FXML
    private TableColumn<OrdenDia,String> ruta_pdf;
    
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
    private JFXButton btn_modSesion;
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
    
    private int contador=0;
    @SuppressWarnings("unchecked")
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
		bloquear_control_pdf();
		btn_modOrden.setDisable(true);
		btn_modSesion.setDisable(true);
    	btn_addOrden.setDisable(true);
    	btn_ActSesion.setVisible(false);
    	btn_modSesion.setVisible(false);
		@SuppressWarnings("rawtypes")
		TableColumn nombre = new TableColumn("Nombre");
		nombre.setMinWidth(360);
		nombre.setVisible(true);
		nombre.setCellValueFactory(
                new PropertyValueFactory<>("nombre"));
		
		@SuppressWarnings("rawtypes")
		TableColumn ruta_pdf = new TableColumn("Ruta");
		ruta_pdf.setMinWidth(80);
		ruta_pdf.setVisible(false);
		ruta_pdf.setCellValueFactory(
                new PropertyValueFactory<>("ruta_pdf"));
		

        list_pdf.getColumns().addAll(nombre,ruta_pdf);
        lista_pdf =FXCollections.observableArrayList();		
	}
    
    public void bloquear() {
    	txt_convocatoria.setDisable(true);
    	cbx_tipoSes.setDisable(true);
    	date.setDisable(true);
    	time.setDisable(true);
    	btn_addSesion.setVisible(false);
    	btn_examinarActa.setDisable(true);
    }
    public void activar() {
    	txt_convocatoria.setDisable(false);
    	cbx_tipoSes.setDisable(false);
    	date.setDisable(false);
    	time.setDisable(false);
    	btn_examinarActa.setDisable(false);
    	btn_modSesion.setDisable(false);
    }
    public void limpiar() {
    	PuntoOrden.setText(String.valueOf(data.num_punto));
    	txt_descripcion.setText(null);
    	cbx_proponente.setValue(null);
    	rutapdf="";
    	list_pdf.getItems().clear();
    	btn_modOrden.setDisable(true);
    	btn_addOrden.setDisable(true);
    
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
    void onAddSesion(ActionEvent event) throws NotBoundException, IOException {
 
		if(txt_convocatoria.getLength()==0) {
			mostrarMesaje("Falta ingresar la convocatoria");
		}else if(date.getValue()==null) {
			mostrarMesaje("Falta selecionar la fecha de intervención");
		}else if(pdf_acta.getItems().size()==0){
			mostrarMesaje("Falta agregar el acta de la sesión");
		}else {
			
			String txtconvocatoria = txt_convocatoria.getText();
	    	String [] meses = {"ENERO","ENERO","FEBRERO","MARZO","ABRIL","MAYO","JUNIO","JULIO","AGOSTO","SEPTIEMBRE","OCTUBRE","NOVIEMBRE","DICIEMBRE"};
	    	String fechaCompleta = date.getValue().getDayOfMonth()+" DE "+meses[date.getValue().getMonthValue()]+" DEL "+date.getValue().getYear();
	    	String horaIntervencion = time.getValue().toString();
	    	String titulo = lbl1.getText()+" "+cbx_tipoSes.getValue()+lbl2.getText()+" "+lbl3.getText()+fechaCompleta+", A lAS "+horaIntervencion+" "+lbl4.getText();
	    	String tipo_sesion = cbx_tipoSes.getValue();
	    	Date fechaIntervencion = Date.valueOf(date.getValue());
	    	Date fechaRegistro = new Date(Calendar.getInstance().getTime().getTime());
	    	
	    	
	    	conexion.establecerConexion();
			ActaPdf pdf = new ActaPdf(nombre_acta,ruta_acta);
			idActa= pdf.guardarRegistro_pdf(conexion.getConnection());
			conexion.cerrarConexion(); 
			
			Sesion sesion = new Sesion(txtconvocatoria,titulo,tipo_sesion,fechaRegistro, fechaIntervencion , horaIntervencion,idActa );
	    	conexion.establecerConexion();
	    	convocatoria = sesion.guardarRegistro(conexion.getConnection());
	    	System.out.println(convocatoria);
	    	conexion.cerrarConexion();
	    	
			
	    	if(convocatoria =="" ) {
	    		
	    		mostrarMesaje("No se pudo registrar la sesión");
	    	}else {
	    		btn_modSesion.setDisable(false);
	        	btn_ActSesion.setVisible(true);
	        	btn_modSesion.setVisible(true);
	    		mostrarMesaje("Ahora prodece a agregar la orden del dia "+convocatoria);
	    		bloquear();
	    	
	    	}	

			
				
			
		}
		
    	
    	
    	
    	
    
    	
    	
    }
    @FXML
    void onModSesion(ActionEvent event) throws NotBoundException, IOException {

		if(txt_convocatoria.getLength()==0) {
			mostrarMesaje("Falta ingresar la convocatoria");
		}else if(date.getValue()==null) {
			mostrarMesaje("Falta selecionar la fecha de intervención");
		}else if(pdf_acta.getItems().size()==0){
			mostrarMesaje("Falta agregar el acta de la sesión");
		}else {
			
			String txtconvocatoria = txt_convocatoria.getText();
	    	String [] meses = {"ENERO","ENERO","FEBRERO","MARZO","ABRIL","MAYO","JUNIO","JULIO","AGOSTO","SEPTIEMBRE","OCTUBRE","NOVIEMBRE","DICIEMBRE"};
	    	String fechaCompleta = date.getValue().getDayOfMonth()+" DE "+meses[date.getValue().getMonthValue()]+" DEL "+date.getValue().getYear();
	    	String horaIntervencion = time.getValue().toString();
	    	String titulo = lbl1.getText()+" "+cbx_tipoSes.getValue()+lbl2.getText()+" "+lbl3.getText()+fechaCompleta+", A lAS "+horaIntervencion+" "+lbl4.getText();
	    	String tipo_sesion = cbx_tipoSes.getValue();
	    	Date fechaIntervencion = Date.valueOf(date.getValue());
	    	Date fechaRegistro = new Date(Calendar.getInstance().getTime().getTime());
	    	
	    	
	    	conexion.establecerConexion();
			ActaPdf pdf = new ActaPdf(nombre_acta,ruta_acta);
			idActa= pdf.guardarRegistro_pdf(conexion.getConnection());
			conexion.cerrarConexion(); 
			
			Sesion sesion = new Sesion(txtconvocatoria,titulo,tipo_sesion,fechaRegistro, fechaIntervencion , horaIntervencion,idActa );
	    	conexion.establecerConexion();
	    	convocatoria = sesion.guardarRegistro(conexion.getConnection());
	    	System.out.println(convocatoria);
	    	conexion.cerrarConexion();
	    	
			
	    	if(convocatoria =="" ) {
	    		
	    		mostrarMesaje("No se pudo registrar la sesión");
	    	}else {
	    		btn_modSesion.setDisable(false);
	    		mostrarMesaje("Ahora prodece a agregar la orden del dia "+convocatoria);
	    		bloquear();
	    	
	    	}	

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
    @SuppressWarnings("unchecked")
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
    			
    			
    			//lista_pdf.set(0,new Pdf(fichero.getName(),dbpath));
    			lista_pdf.add(contador,new Pdf(fichero.getName(),dbpath));
    			contador++;
    			
    		
        		
    	        ObservableList<Pdf> datos = FXCollections.observableArrayList(
    	        		lista_pdf
    					);
    			
    	        list_pdf.setItems(datos);
        
    			//agrega al lisview
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
    void activar_controles_pdf(MouseEvent  event) throws RemoteException{
    	
    	desbloquear_control_pdf();
    	
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
    
    @SuppressWarnings("unchecked")
	@FXML
    void onAddOrden(ActionEvent event) throws IOException {
    	
    	System.out.println("La convocatoria es: "+convocatoria);
    	if(convocatoria=="") {
    		mostrarMesaje("Primero tiene que agregar la sesión");
    	}else {
    		System.out.println(PuntoOrden.getText());
    		System.out.println(txt_descripcion.getText());
    		System.out.println(rutapdf);
    		System.out.println(cbx_proponente.getValue().getId());
    		System.out.println(convocatoria);
    		
    		int longitud_lista=0;
        	longitud_lista=list_pdf.getItems().size();
        	
    		
    		if(txt_descripcion.getLength()==0) {
        		mostrarMesaje("Falta ingresar la descripción del punto");
        	}else if( cbx_proponente.getValue().getId()==0) {
        		mostrarMesaje("Falta selecionar el Proponente del punto");
        	}else {
        		
        		if(longitud_lista==0) {
            		mostrarMesaje("No se a agredado documentación "
            				+ "para el punto "+PuntoOrden.getText()+"");
            	}
        		if(data.documentacion==0) {
        			
        			OrdenDia orden = new OrdenDia(convocatoria,Integer.valueOf(PuntoOrden.getText()), txt_descripcion.getText(), cbx_proponente.getValue().getId());
                	conexion.establecerConexion();
                	idOrden=orden.guardarRegistro(conexion.getConnection());
                	conexion.cerrarConexion();
                	
                	
                	conexion.establecerConexion();
                	
                	while(longitud_lista>0)
        				try {
        					{
        						System.out.println(list_pdf.getItems().get(longitud_lista-1).getRuta_pdf());
        						Pdf pdf = new Pdf(idOrden,list_pdf.getItems().get(longitud_lista-1).getRuta_pdf());
        						pdf.guardarRegistro_pdfs(conexion.getConnection());
        						longitud_lista--;
        					}
        				} catch (Exception e) {
        					// TODO Auto-generated catch block
        					e.printStackTrace();
        				}
                	conexion.cerrarConexion();

                	data.num_punto=data.num_punto+1;
                	limpiar();
                	
        		}
        		
        	}

    		
        	
        	
        	contador=0;
        	List<OrdenDia> listaOrden =FXCollections.observableArrayList();
    		conexion.establecerConexion();
        	
    		OrdenDia.llenarInformacion(conexion.getConnection(), listaOrden,convocatoria);
    		conexion.cerrarConexion();
    		
    		
    		
    		@SuppressWarnings("rawtypes")
			TableColumn id_punto = new TableColumn("No. Punto");
	    	id_punto.setMinWidth(50);
	    	id_punto.setVisible(false);
	    	id_punto.setCellValueFactory(
	                new PropertyValueFactory<>("id"));
			
			@SuppressWarnings("rawtypes")
			TableColumn num_punto = new TableColumn("No. Punto");
			num_punto.setMinWidth(80);
			num_punto.setCellValueFactory(
	                new PropertyValueFactory<>("numeroPunto"));
			@SuppressWarnings("rawtypes")
			TableColumn descrip = new TableColumn("Descripción");
			descrip.setMinWidth(300);
			descrip.setCellValueFactory(
	                new PropertyValueFactory<>("tema"));
	        ObservableList<OrdenDia> datos = FXCollections.observableArrayList(
	        		listaOrden
					);
			
			tabla.getColumns().addAll(id_punto,num_punto,descrip);
			tabla.setItems(datos);
    	}
    }
    
    @FXML
    public void onModOrden(ActionEvent event) {
    	
    }@FXML
    public void mostrar_pdf(ActionEvent event) {
    	try {
    		String ruta="";
        	ruta=list_pdf.getSelectionModel().selectedItemProperty().getValue().getRuta_pdf();
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
    		//bloquear_control_pdf();
    	}catch (IOException ex) {
    	     ex.printStackTrace();
    	}
    }

    @FXML
    public void eli_lista_pdf(ActionEvent event) {
    	list_pdf.getItems().remove(list_pdf.getSelectionModel().getSelectedIndex());
    	bloquear_control_pdf();
    	
    }
    
    @FXML
    public void onNewOrden(ActionEvent event) {
    	limpiar();

    	btn_addOrden.setDisable(false);
    
    	}
    @FXML
    public void onActSesion(ActionEvent event) {
    	
    	activar();
    	btn_ActSesion.setVisible(false);
    	
    	}
    @SuppressWarnings("unchecked")
	@FXML
    public void mostrar_punto(MouseEvent event) throws IOException {
    		btn_modOrden.setDisable(false);
    		int id_punto=tabla.getSelectionModel().selectedItemProperty().getValue().getId();
    		txt_descripcion.setText(tabla.getSelectionModel().selectedItemProperty().getValue().getTema());
    		PuntoOrden.setText(String.valueOf(tabla.getSelectionModel().selectedItemProperty().getValue().getNumeroPunto()));
    		int id_pro= tabla.getSelectionModel().selectedItemProperty().getValue().getProponente();
    		int log= proponentes.size();
			int bandera=0;
			while(log>=1) {
				if(id_pro==proponentes.get(bandera).getId()) {
					cbx_proponente.setValue(proponentes.get(bandera));
				}
				log--;	
				bandera++;
			
			}
			
			
			List<Pdf> pdf;
			System.out.println(id_punto+"  holaa");
			
			pdf=Pdf.consultarPDFS_Modificacion(id_punto);
			System.out.println(pdf.size()+"  longitud");
			list_pdf.getItems().clear();
			lista_pdf.clear();
			int log_pdf=pdf.size();
			int bandera_2=0;
			list_pdf.getItems().clear();
			while(log_pdf>0) {	
				Pdf pdf_file = Pdf.pdf_punto(pdf.get(bandera_2).getId());
				File n = new File(convertirPdf(pdf_file.getPdf()));
				lista_pdf.addAll(new Pdf(pdf_file.getNombre(),n.getPath()));		        
				log_pdf--;
				bandera_2++;
			}
			
			ObservableList<Pdf> datos = FXCollections.observableArrayList(
	        		lista_pdf
					);
			
	        list_pdf.setItems(datos);
	        
			
    
	
    }
    
    public String convertirPdf(byte[] bytes) throws IOException {
		String tmpDir=System.getProperty("user.dir")+"\\tmp\\";
		String tmpFileName= UUID.randomUUID().toString();
		if(!new File(tmpDir).exists()) {
			if(!new File(tmpDir).mkdirs()) {
				System.out.print("Imposibe crear directorio temporal");
				return null;
			}
			
		}
		OutputStream out = new FileOutputStream(tmpDir+tmpFileName+".pdf");
		out.write(bytes);
		out.close();
		File file= new File(tmpDir+tmpFileName+".pdf");
		file.deleteOnExit();
		
		return file.getPath();
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

