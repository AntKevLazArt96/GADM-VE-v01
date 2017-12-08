package application;

import java.io.IOException;
import java.net.URL;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;

import clases.DataSesion;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class PanelSesionCtrl implements Initializable{
	
	Calendar fecha = new GregorianCalendar();
	int annio = fecha.get(Calendar.YEAR);
	int mes = fecha.get(Calendar.MONTH) + 1;
	int dia = fecha.get(Calendar.DAY_OF_MONTH);
	
	
	
    @FXML
    public AnchorPane panelInicioVoto;

    @FXML
    private AnchorPane paneHaySesion;

    @FXML
    private JFXButton btn_inicio;

    @FXML
    private JFXButton btn_ver;
    
    @FXML
    private Label lbl_fecha;

    @FXML
    private Label lbl_convocatoria;

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

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		String fechaActual=annio+"-"+mes+"-"+dia;
		int dia =DataSesion.fechaIntervencion.getDate();
		int mes =DataSesion.fechaIntervencion.getMonth()+ 1;
		int anio =DataSesion.fechaIntervencion.getYear()+1900;
		
		String fechaIntervencion=anio+"-"+mes+"-"+dia;
		System.out.println(DataSesion.fechaIntervencion.getDate()); 
		if(fechaIntervencion.equals(fechaActual)) {
			lbl_fecha.setText("HOY ");
		}else {
			lbl_fecha.setText("EL "+DataSesion.fechaIntervencion);
		}
		
		System.out.println(fechaIntervencion+"="+fechaActual);
		
		
		lbl_convocatoria.setText(DataSesion.tipo_sesion+" "+DataSesion.convocatoria);
		
	}

}
