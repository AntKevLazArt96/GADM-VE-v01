package application;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;

import clases.data;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class InicioController implements Initializable {
	
	
	@FXML
    private JFXButton inicioButton;

    @FXML
    private JFXButton sesionButton;
    

    @FXML
    private JFXButton buttonControlPane;
    
	@FXML private JFXButton closeButton;
	
	@FXML
    public AnchorPane panel;
	
	@FXML
    private Label txt_username;


    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    	txt_username.setText(data.name);
    	try {
			AnchorPane pane = (AnchorPane)FXMLLoader.load(getClass().getResource("SubInicio.fxml"));
			panel.getChildren().setAll(pane);
			
    	} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	
    	
    	
    }    
   
    
    @FXML
    void closeButtonAction(ActionEvent event) throws IOException {
    		// logica para cerrar sesion
	    Stage actualStage = (Stage) closeButton.getScene().getWindow();
	    // do what you have to do
	    actualStage.close();
	    
		Stage newStage = new Stage();
		AnchorPane pane = (AnchorPane)FXMLLoader.load(getClass().getResource("login.fxml"));
        Scene scene = new Scene(pane);

        newStage.setScene(scene);
        newStage.initStyle(StageStyle.UNDECORATED);
        newStage.show();
    }
    
    @FXML
    void buttonControlPaneAction(ActionEvent event) {
    	try {
			AnchorPane pane = (AnchorPane)FXMLLoader.load(getClass().getResource("PanelControl.fxml"));
			panel.getChildren().setAll(pane);
    	} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    @FXML
    void inicioButtonAction(ActionEvent event) {
    	try {
			AnchorPane pane = (AnchorPane)FXMLLoader.load(getClass().getResource("SubInicio.fxml"));
			panel.getChildren().setAll(pane);
    	} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    


    @FXML
    void sessionButtonAction(ActionEvent event) {
    	try {
			AnchorPane pane = (AnchorPane)FXMLLoader.load(getClass().getResource("SubSesiones.fxml"));
			panel.getChildren().setAll(pane);
    	} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

	

	


    
}
