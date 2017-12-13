package cliente;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import com.jfoenix.controls.JFXTextArea;
import gad.manta.common.Pdf;
import gad.manta.common.Sesion;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class ClienteSesionCtrl implements Initializable {
	Sesion sesion;
	@FXML
    public Label lbl_punto;
   
    @FXML
    public JFXTextArea label_titulo;

    @FXML
    public Label label_proponente;

    @FXML
    public TableView<Pdf> table_pdf;
    
    
    
    @FXML
	void mostrar_pdf(MouseEvent event) {

		Stage newStage = new Stage();

		AnchorPane pane;
		try {

			data.id_pdf = table_pdf.getSelectionModel().selectedItemProperty().get().getId();
			// System.out.println(data.id_pdf);

			pane = (AnchorPane) FXMLLoader.load(getClass().getResource("LecturaPDF.fxml"));
			Scene scene = new Scene(pane);

			// Pantalla completa
			Screen screen = Screen.getPrimary();
			Rectangle2D bounds = screen.getVisualBounds();

			newStage.setX(bounds.getMinX());
			newStage.setY(bounds.getMinY());
			newStage.setWidth(bounds.getWidth());
			newStage.setHeight(bounds.getHeight());

			newStage.setScene(scene);
			newStage.initStyle(StageStyle.UNDECORATED);
			newStage.show();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
    

	@Override
	public void initialize(URL location, ResourceBundle resources) {
	
		lbl_punto.setText("Esperando...");
    	label_titulo.setText("Esperando...");
        label_proponente.setText("Esperando...");
		
		
		
	}

}
