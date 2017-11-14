package cliente;

import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import com.jfoenix.controls.JFXButton;

import gad.manta.common.IServidor;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class ClienteVotoOrdenCtrl {
	private static IServidor servidor;
	
    @FXML
    private JFXButton bnt_aprobar;

    @FXML
    private JFXButton bntRechazar;

    @FXML
    void onAprobar(ActionEvent event) throws NotBoundException, IOException {
    	// get a handle to the stage
	    Stage actualStage = (Stage) bnt_aprobar.getScene().getWindow();
	    // do what you have to do
	    actualStage.close();
	    
	    servidor = (IServidor)Naming.lookup("rmi://192.168.1.6/VotoE");
	    
	    Stage newStage = new Stage();
		
	    String login = servidor.addVoto(data.name, "APROBAR");
	    /*System.out.println("Clicked");
        data.ip = "192.168.1.6";
        data.port = 6666;
	    data.name = login;*/
	    data.voto="APROBAR";
	    System.out.println(login);
		AnchorPane pane = (AnchorPane)FXMLLoader.load(getClass().getResource("ClienteMostrarVoto.fxml"));
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
    void onRechazar(ActionEvent event) throws NotBoundException, IOException {
    	// get a handle to the stage
	    Stage actualStage = (Stage) bntRechazar.getScene().getWindow();
	    // do what you have to do
	    actualStage.close();
	    
	    servidor = (IServidor)Naming.lookup("rmi://192.168.1.6/VotoE");
	    
	    Stage newStage = new Stage();
		
	    String login = servidor.addVoto(data.name, "RECHAZAR");
	    /*System.out.println("Clicked");
        data.ip = "192.168.1.6";
        data.port = 6666;
	    data.name = login;*/
	    data.voto="RECHAZAR";
	    //System.out.println(login);
		AnchorPane pane = (AnchorPane)FXMLLoader.load(getClass().getResource("ClienteMostrarVoto.fxml"));
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

}
