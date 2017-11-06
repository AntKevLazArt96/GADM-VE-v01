package application;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.format.FormatStyle;
import java.util.Date;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTimePicker;
import com.sun.glass.ui.Timer;

import gad.manta.common.IServidor;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.util.converter.LocalDateStringConverter;

public class NuevaSesionCtrl implements Initializable{
	private static IServidor servidor;
	
	ObservableList<String> tipoSesion = FXCollections.
			observableArrayList("ORDINARIA","EXTRAORDINARIA");
	
    @FXML
    private AnchorPane panel;
    @FXML
    private JFXDatePicker date;
    
    @FXML
    private JFXTimePicker time;
    
    @FXML
    private JFXComboBox<String> cbx_tipoSes;
    
    
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
    private JFXButton btn_addSesion;
    

    @FXML
    private JFXButton btn_cancelar;
    
    @FXML
    private JFXButton btn_addOrden;
    
    @Override
	public void initialize(URL location, ResourceBundle resources) {
		//date.setConverter(new LocalDateStringConverter(FormatStyle.FULL));
    	time.setValue(LocalTime.of(16, 00));
		cbx_tipoSes.setValue("ORDINARIA");
		cbx_tipoSes.setItems(tipoSesion);
		System.out.println("La hora es: "+time.getAccessibleText());
		
	}
    
    public void bloquear() {
    	txt_convocatoria.setDisable(true);
    	cbx_tipoSes.setDisable(true);
    	date.setDisable(true);
    	time.setDisable(true);
    	btn_addSesion.setVisible(false);
    	
    }
    
    public static int idSesion = 0;
    
    @FXML
    void onAddSesion(ActionEvent event) throws MalformedURLException, RemoteException, NotBoundException {
    	String convocatoria = txt_convocatoria.getText();
    	String [] meses = {"ENERO","ENERO","FEBRERO","MARZO","ABRIL","MAYO","JUNIO","JULIO","AGOSTO","SEPTIEMBRE","OCTUBRE","NOVIEMBRE","DICIEMBRE"};
    	String fechaCompleta = date.getValue().getDayOfMonth()+" DE "+meses[date.getValue().getMonthValue()]+" DEL "+date.getValue().getYear();
    	String horaIntervencion = time.getValue().toString();
    	String titulo = lbl1.getText()+" "+cbx_tipoSes.getValue()+lbl2.getText()+" "+lbl3.getText()+fechaCompleta+", A lAS "+horaIntervencion+" "+lbl4.getText();
    	String fechaIntervencion = date.getValue().toString();
    	Date myDate = new Date();
    	String fechaRegistro = new SimpleDateFormat("dd-MM-yyyy").format(myDate);
    	//Aquí obtienes el formato que deseas
    	
    	servidor = (IServidor)Naming.lookup("rmi://192.168.1.6/VotoE");
    	idSesion=1;
    	//int idsesion = servidor.agregarSesion(fechaRegistro, fechaIntervencion, horaIntervencion, convocatoria, titulo);
    	bloquear();
    	try {
    		
    		System.out.println(titulo);
		} catch (Exception e) {
			System.out.println(e.getMessage());
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
    
    @FXML
    void onAddOrden(ActionEvent event) {
    	if(idSesion==0) {
    		JOptionPane.showMessageDialog(null, "Primero Tienes que agregar la sesion");
    	}else {
    		
    	}
    	
    }

	

}
