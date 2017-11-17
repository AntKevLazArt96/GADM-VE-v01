package application;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;

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
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class PrincipalSecretariaCtrl implements Initializable{
	private static IServidor servidor;
	int sesionDe = 0;
	
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
    private TableView table_documentacion;
    @FXML
    private JFXTextField sesionA;
    
    @FXML
    private JFXButton btn_pdf;

    @FXML
    void iniSesion(ActionEvent event) throws IOException, NotBoundException {   	    	    	    	    	
    	Stage stage = (Stage) btnIniSesion.getScene().getWindow();
	    // do what you have to do
	    stage.close();
	    
    	
    	Stage newStage = new Stage();
		
	    AnchorPane pane = (AnchorPane)FXMLLoader.load(getClass().getResource("inicioSesion.fxml"));
	    servidor.enviar("hola como estas", sesionDe, Integer.valueOf(sesionA.getText()));	    
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
       void mostrar_pdf(ActionEvent event) {

       }

	@SuppressWarnings({ "unchecked", "unchecked" })
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		try {
			servidor = (IServidor)Naming.lookup("rmi://192.168.1.6/VotoE");
			List<Sesion> lista = servidor.consultarSesion();
			label_titulo.setText(lista.get(0).getTitulo());
			label_convocatoria.setText(lista.get(0).getConvocatoria());
			
			List<OrdenDia>lista_orden=servidor.consultarOrden();
			TableColumn num_punto = new TableColumn("N° Punto");
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
	                new PropertyValueFactory<>("proponente"));
			ObservableList<OrdenDia> datos = FXCollections.observableArrayList(
					lista_orden
					);
			
			
			tabla_ordenDia.getColumns().addAll(num_punto,descripcion,proponente);
			tabla_ordenDia.setItems(datos);
			
		} catch (MalformedURLException | RemoteException | NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	    try {
			sesionDe = servidor.iniciarSesion("christian.pinargote");
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    System.out.println(sesionDe);
		
	}
	
	@FXML
    void mostrar_documentacion(MouseEvent  event) throws RemoteException{
    	
    	try {
    		String ruta="";
        	ruta=list_pdf.getSelectionModel().selectedItemProperty().getValue();
    	    File path = new File (ruta);
    	    Desktop.getDesktop().open(path);
    	}catch (IOException ex) {
    	     ex.printStackTrace();
    	}
    }

}
