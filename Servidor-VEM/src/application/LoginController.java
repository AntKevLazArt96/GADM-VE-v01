package application;

import java.io.IOException;
import java.net.URL;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;

import gad.manta.common.IServidor;
import gad.manta.common.Utils;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import servidor.Servidor;


public class LoginController implements Initializable {
	private static IServidor servidor;

    
	@FXML private JFXTextField txt_username;
	@FXML private JFXPasswordField txt_password;
	@FXML private javafx.scene.control.Button closeButton;
	@FXML private javafx.scene.control.Button loginButton;

	@FXML
	private void closeButtonAction(){
	    // get a handle to the stage
	    Stage stage = (Stage) closeButton.getScene().getWindow();
	    // do what you have to do
	    stage.close();
	}
	
	

	@FXML
	private void loginAction() throws IOException, NotBoundException{
		// get a handle to the stage
	    Stage actualStage = (Stage) closeButton.getScene().getWindow();
	    // do what you have to do
	    actualStage.close();
	    
	    servidor = (IServidor)Naming.lookup("rmi://192.168.1.5/VotoE");
	    
	    Stage newStage = new Stage();
		
	    String login = servidor.login(txt_username.getText(),txt_password.getText());
		
	    System.out.println(login);
		AnchorPane pane = (AnchorPane)FXMLLoader.load(getClass().getResource("Inicio.fxml"));
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
    public void initialize(URL url, ResourceBundle rb) {
    	
    	boolean estado=false;
		
    	System.out.println(estado);
    	
		if(estado==false) {
    		try {
        		Utils.setCodeBase(IServidor.class);
    			
    			Servidor servidor = new Servidor();
    			IServidor remote = (IServidor)UnicastRemoteObject.exportObject(servidor, 8888);
    			
    			Registry registry = LocateRegistry.createRegistry(1099);
    			registry.rebind("VotoE", remote);
    			
    			
    			System.out.println(registry.toString());
    			System.out.println("Servidor Liso, Preione enter para terminar");
    	        /*System.in.read();
    	    	
    	        registry.unbind("VotoE");
    	        UnicastRemoteObject.unexportObject(servidor, true);
    			*/
    			estado = true;
    		} catch (RemoteException e) {
    			System.out.println(e);
    		}
    	}
    	
    	
    	
    	
        
    }    
    
}
