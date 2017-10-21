package application;





import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import com.jfoenix.controls.*;

import gad.manta.common.IServidor;
import javafx.event.*;
import javafx.fxml.*;
import javafx.scene.control.*;

public class SampleController {
	private static IServidor servidor;
	
	@FXML
	private JFXTextField tf_firtsName;
    
    @FXML
    private JFXTextField tf_lastName;
    
    @FXML
    private Label lbl_resultado;
    
    @FXML
    void onSubmit(ActionEvent event) throws MalformedURLException, RemoteException, NotBoundException {
    		
    	servidor = (IServidor)Naming.lookup("rmi://192.168.1.7/VotoE");
		System.out.println(servidor.toString());
		String saludo = servidor.saludar(tf_firtsName.getText()+ " "+tf_lastName.getText());
    	//System.out.println(tf_firtsName.getText() + " "+tf_lastName.getText() );
    	lbl_resultado.setText(saludo);
    		
    	
    }

}
