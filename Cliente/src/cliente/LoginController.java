package cliente;

import java.io.IOException;
import java.net.URL;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;

import gad.manta.common.IServidor;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


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
	    
	    servidor = (IServidor)Naming.lookup("rmi://192.168.1.7/VotoE");
	    
	    Stage newStage = new Stage();
		
	    String login = servidor.login(txt_username.getText(),txt_password.getText());
		
	    System.out.println(login);
		AnchorPane pane = (AnchorPane)FXMLLoader.load(getClass().getResource("ClientePrincipal.fxml"));
        Scene scene = new Scene(pane);

        newStage.setScene(scene);
        newStage.initStyle(StageStyle.UNDECORATED);
        newStage.show();
	}
	
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
