package application;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;

import gad.manta.common.IServidor;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
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
    private JFXTextField sesionA;

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

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		try {
			servidor = (IServidor)Naming.lookup("rmi://192.168.1.6/VotoE");
		} catch (MalformedURLException | RemoteException | NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    try {
			sesionDe = servidor.iniciarSesion("Anthony");
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    System.out.println(sesionDe);
		
	}

}
