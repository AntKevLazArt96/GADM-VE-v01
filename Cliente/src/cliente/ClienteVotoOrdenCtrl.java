package cliente;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;

import gad.manta.common.IServidor;
import gad.manta.common.Sesion;
import gad.manta.common.Usuario;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class ClienteVotoOrdenCtrl implements Initializable {
	private static IServidor servidor;
	@FXML
    private Label label_convocatoria;
	@FXML
    private Label label_convocatoria2;

    @FXML
    private Circle cirlogin;

    @FXML
    private Label lbl_nombre;
    
    @FXML
    private JFXTextArea label_titulo;
	
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
		
	    String login = servidor.addVoto(data.name, "APROBADO",data.img);
	    /*System.out.println("Clicked");
        data.ip = "192.168.1.6";
        data.port = 6666;
	    data.name = login;*/
	    data.voto="APROBADO";
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
		
	    servidor.addVoto(data.name, "RECHAZADO",data.img);
	    data.voto="RECHAZADO";
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

    public Image convertirImg(byte[] bytes) throws IOException {
		ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
		
		Image img = new Image(bis);
		return img;
	}
    
    
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		try {
			servidor = (IServidor)Naming.lookup("rmi://192.168.1.6/VotoE");
		} catch (MalformedURLException | RemoteException | NotBoundException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		
		lbl_nombre.setText(data.name);
		
		try {
			Image im = convertirImg(data.img);
			
	        cirlogin.setFill(new ImagePattern(im));
	        cirlogin.setStroke(Color.SEAGREEN);
	        cirlogin.setEffect(new DropShadow(+25d, 0d, +2d, Color.DARKGREEN));

				
			Sesion sesion = servidor.consultarSesion();
			data.convocatoria=sesion.getConvocatoria();
			data.titulo=sesion.getTitulo();
			label_titulo.setText(sesion.getTitulo());
			label_convocatoria.setText(sesion.getConvocatoria());
			label_convocatoria2.setText(sesion.getConvocatoria());
		
		} catch (RemoteException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
