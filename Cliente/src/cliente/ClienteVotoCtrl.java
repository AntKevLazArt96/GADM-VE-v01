package src.cliente;

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

public class ClienteVotoCtrl implements Initializable{
	private static IServidor servidor;

    @FXML
    private JFXButton btn_voz;

    @FXML
    private Label label_convocatoria;

    @FXML
    private Circle cirlogin;

    @FXML
    private Label lbl_nombre;

    @FXML
    private JFXButton btn_favor;

    @FXML
    private JFXButton btn_contra;

    @FXML
    private JFXButton btn_blanco;

    @FXML
    private JFXButton btn_salvo;

    @FXML
    private JFXTextArea label_titulo;

    @FXML
    private Label label_punto;

    @FXML
    private Label lbl_proponente;

    @FXML
    void onBlanco(ActionEvent event) {

    }

    @FXML
    void onContra(ActionEvent event) {

    }

    @FXML
    void onFavor(ActionEvent event) throws NotBoundException, IOException {
    	// get a handle to the stage
	    Stage actualStage = (Stage) btn_favor.getScene().getWindow();
	    // do what you have to do
	    actualStage.close();
	    
	    servidor = (IServidor)Naming.lookup("rmi://192.168.1.6/VotoE");
	    
	    Stage newStage = new Stage();
		
	    servidor.addVoto(data.name, "FAVOR",data.img);
	    data.voto="FAVOR";
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
    void onSalvo(ActionEvent event) {

    }
    
    public Image convertirImg(byte[] bytes) throws IOException {
		ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
		
		Image img = new Image(bis);
		return img;
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		
		lbl_nombre.setText(data.name);
		
			try {
				Image im = convertirImg(data.img);
				cirlogin.setFill(new ImagePattern(im));
		        cirlogin.setStroke(Color.SEAGREEN);
		        cirlogin.setEffect(new DropShadow(+25d, 0d, +2d, Color.DARKGREEN));
		        label_convocatoria.setText(data.convocatoria);
				label_punto.setText(puntoATratar.num_punto);
				label_titulo.setText(puntoATratar.tema);
				lbl_proponente.setText(puntoATratar.proponente);
				
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
	        
		
		
		
	}

}
