package cliente;

import java.io.IOException;
import java.net.URL;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;

import cliente.data;
import gad.manta.common.IServidor;
import gad.manta.common.data_configuracion;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


public class LoginController implements Initializable {
	public static IServidor servidor;

    
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
	    
	    servidor = (IServidor)Naming.lookup("rmi://192.168.1.6/VotoE");
	    
	    Stage newStage = new Stage();
		
	    String login = servidor.login(txt_username.getText(),txt_password.getText());
	    
	    System.out.println("Clicked");
        
	    data.name = login;
	    System.out.println(login);
		AnchorPane pane = (AnchorPane)FXMLLoader.load(getClass().getResource("PantallaPrincipal.fxml"));
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
public static void configuraciones() {
		
		try {
			Connection db;
			db = DriverManager.getConnection("jdbc:postgresql:gad_voto","postgres","1234");
			Statement st = db.createStatement();
			ResultSet resultado= st.executeQuery("select * from configuracion_ve where id_confi=1;");
			resultado.next();

			//socket
			data_configuracion.ip=resultado.getString(3);
			data_configuracion.port=resultado.getInt(5);
			System.out.println(data_configuracion.ip);
			db.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
}
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    	configuraciones();
    	txt_username.setText("concejal1");
		txt_password.setText("1234");
    }    
    
}
