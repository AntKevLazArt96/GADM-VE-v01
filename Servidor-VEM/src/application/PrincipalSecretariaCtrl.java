package application;

//import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
//import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;

import gad.manta.common.Documentacion;
import gad.manta.common.IServidor;
import gad.manta.common.OrdenDia;
import gad.manta.common.Sesion;
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
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class PrincipalSecretariaCtrl implements Initializable{
	private static IServidor servidor;
	int sesionDe = 0;
	
	@FXML
    private Circle cirlogin;
    @FXML
    private Label lbl_nombre;
    
    @FXML
    private JFXButton btnIniSesion;
    @FXML
    private Label label_convocatoria;
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
    private JFXButton btn_voz;
	@FXML
    private JFXButton btn_modificar;
	
	@FXML
    private Label lblOrden;

	
   
    public Sesion sesion;

    @FXML
    void iniSesion(ActionEvent event) throws IOException, NotBoundException {   	    	    	    	    	
    	Stage stage = (Stage) btnIniSesion.getScene().getWindow();
	    // do what you have to do
	    stage.close();
	    
    	
    	Stage newStage = new Stage();
		
	    AnchorPane pane = (AnchorPane)FXMLLoader.load(getClass().getResource("InicioVotoOrden.fxml"));
	    Scene scene = new Scene(pane);
        
        //Pantalla completa
        Screen screen = Screen.getPrimary();
		Rectangle2D bounds = screen.getVisualBounds();

		newStage.setX(bounds.getMinX());
		newStage.setY(bounds.getMinY());
		newStage.setWidth(bounds.getWidth());
		newStage.setHeight(bounds.getHeight());
        
        
        newStage.setScene(scene);
        newStage.initStyle(StageStyle.UNDECORATED);
        newStage.show();

    }


    @FXML
    void modificar_sesion(ActionEvent event) {
		
		try {
			Stage newStage = new Stage();
			
			AnchorPane pane;
			
			pane = (AnchorPane)FXMLLoader.load(getClass().getResource("ModificacionSesion.fxml"));
			Scene scene = new Scene(pane);
	        
	        //Pantalla completa
	      /*  Screen screen = Screen.getPrimary();
			Rectangle2D bounds = screen.getVisualBounds();
			
			newStage.setX(bounds.getMinX());
			newStage.setY(bounds.getMinY());
			newStage.setWidth(bounds.getWidth());
			newStage.setHeight(bounds.getHeight());*/
	        
	        newStage.setScene(scene);
	        newStage.initStyle(StageStyle.UNDECORATED);
	        newStage.show();
	        
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
			data.id_acta=sesion.getId_pdf();
			pane = (AnchorPane)FXMLLoader.load(getClass().getResource("LecturaPDF.fxml"));
			Scene scene = new Scene(pane);
	        
	        //Pantalla completa
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
			
			data.id_acta=0;
			data.id_pdf=table_documentacion.getSelectionModel().selectedItemProperty().get().getId_pdf();
			System.out.println(data.id_pdf);
			
			pane = (AnchorPane)FXMLLoader.load(getClass().getResource("LecturaPDF.fxml"));
			Scene scene = new Scene(pane);
	        
	        //Pantalla completa
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
		lbl_nombre.setText(data.name);
		
		File f = new File("C:\\librerias\\concejal1.png");
        Image im = new Image(f.toURI().toString());
        cirlogin.setFill(new ImagePattern(im));
        cirlogin.setStroke(Color.SEAGREEN);
        cirlogin.setEffect(new DropShadow(+25d, 0d, +2d, Color.DARKGREEN));
        
		try {
			servidor = (IServidor)Naming.lookup("rmi://192.168.1.6/VotoE");
			
			sesion = servidor.consultarSesion();
			label_titulo.setText(sesion.getDescription());
			label_convocatoria.setText(sesion.getConvocatoria());
			
			List<OrdenDia>lista_orden=servidor.consultarOrden();
			
			TableColumn num_punto = new TableColumn("No. Punto");
			num_punto.setMinWidth(50);
			num_punto.setCellValueFactory(
	                new PropertyValueFactory<>("numeroPunto"));
	 
	       TableColumn descripcion = new TableColumn("Descripción");
	        descripcion.setMinWidth(900);
	        descripcion.setCellValueFactory(
	                new PropertyValueFactory<>("tema"));
	 
	        TableColumn proponente = new TableColumn("Proponente");
	        proponente.setMinWidth(300);
	        proponente.setCellValueFactory(
	                new PropertyValueFactory<>("proponente_nombre"));
			ObservableList<OrdenDia> datos = FXCollections.observableArrayList(
					lista_orden
					);
			
			tabla_ordenDia.getColumns().addAll(num_punto,descripcion,proponente);
			tabla_ordenDia.setItems(datos);
	
			List<Documentacion>lista_documentacion=servidor.mostrarDocumentacion();
			    TableColumn pdf = new TableColumn("id");
		        pdf.setMinWidth(500);
		        pdf.setVisible(false);
		        pdf.setCellValueFactory(
		                new PropertyValueFactory<>("id_pdf"));
		        
			TableColumn punto = new TableColumn("Documentación perteneciente al punto");
			punto.setMinWidth(250);
			punto.setCellValueFactory(
	                new PropertyValueFactory<>("punto"));
			TableColumn nombre = new TableColumn("Nombre");
	        nombre.setMinWidth(700);
	        nombre.setResizable(true);
	        
	        nombre.setCellValueFactory(
	                new PropertyValueFactory<>("nombre"));
	       
			ObservableList<Documentacion> datos_pdf = FXCollections.observableArrayList(
					lista_documentacion
					);
			table_documentacion.getColumns().addAll(pdf,punto,nombre);
			
			table_documentacion.setItems(datos_pdf);
	
			
		} catch (MalformedURLException | RemoteException | NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	}
	
	


	@FXML 
	public void handleButtonAction(ActionEvent event) {
		
	}


	

}
