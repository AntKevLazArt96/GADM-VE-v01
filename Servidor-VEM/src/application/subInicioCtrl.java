package application;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.util.Calendar;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;

import clases.data;
import gad.manta.common.Conexion;
import gad.manta.common.Sesion;
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


public class subInicioCtrl implements Initializable{
	
	private Conexion conexion;
	
	//private Servidor servidor;
	@FXML
    private AnchorPane panel;

    @FXML
    private AnchorPane paneHaySesion;

    @FXML
    private JFXButton btn_inicio;

    @FXML
    private JFXButton btn_ver;

    @FXML
    private AnchorPane paneNoHaySesion;

    @FXML
    private JFXButton btn_nuevaSesion;
    
    @Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		conexion = new Conexion();
    	
		Date fechaActual = new Date(Calendar.getInstance().getTime().getTime());
		
		
		conexion.establecerConexion();
			String resultado = Sesion.haySesionParaHoy(conexion.getConnection(), fechaActual);
			System.out.println("El resultado es "+resultado);
			if(resultado != "") {
				data.convocatoria_sesion=resultado;
				paneHaySesion.setVisible(true);
				paneNoHaySesion.setVisible(false);
			}else {
				paneHaySesion.setVisible(false);
				paneNoHaySesion.setVisible(true);
				paneNoHaySesion.setLayoutY(141);
				
			}
		conexion.cerrarConexion();
		
	}
    
    
    @FXML
    void iniciarAction(ActionEvent event) throws IOException {
    	Stage actualStage = (Stage) btn_inicio.getScene().getWindow();
	    // do what you have to do
	    actualStage.close();
	    
	    Stage newStage = new Stage();
		
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
    
    @FXML
    void onNuevaSesion(ActionEvent event) throws IOException {
    	try {
			AnchorPane pane = (AnchorPane)FXMLLoader.load(getClass().getResource("NuevaSesion.fxml"));
			panel.getChildren().setAll(pane);
    	} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

	

}
