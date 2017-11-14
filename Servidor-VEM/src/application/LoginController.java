package application;

import java.io.IOException;
import java.rmi.Naming;
import java.rmi.NotBoundException;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;

import gad.manta.common.IServidor;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


public class LoginController extends Application {
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
	    
	    servidor = (IServidor)Naming.lookup("rmi://192.168.128.1/VotoE");
	    
	    Stage newStage = new Stage();
	    FXMLLoader loader= new FXMLLoader(getClass().getResource("Inicio.fxml"));
	    String username = servidor.login(txt_username.getText(),txt_password.getText());
	    
	    data.ip = "192.168.128.1";
        data.port = 6666;
	    data.name = username;
	    
	    
	    if(username!=null) {
	    	
		    AnchorPane pane = loader.load();
	        Scene scene = new Scene(pane);
	        InicioController inicio =(InicioController)loader.getController();
	        inicio.setUsername(username);
	        
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
	    }else {
	    	System.out.println("Usuario incorrecto");
	    }
	}
	


	@Override
	public void start(Stage arg0) throws Exception {
		// TODO Auto-generated method stub
		
	}    
    
}
