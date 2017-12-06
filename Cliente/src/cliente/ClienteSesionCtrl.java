package cliente;

import java.net.URL;
import java.util.ResourceBundle;
import com.jfoenix.controls.JFXTextArea;
import gad.manta.common.Pdf;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;

public class ClienteSesionCtrl implements Initializable {
	
	@FXML
    public Label lbl_punto;
   
    @FXML
    public JFXTextArea label_titulo;

    @FXML
    public Label label_proponente;

    @FXML
    public TableView<Pdf> table_pdf;
    

	@Override
	public void initialize(URL location, ResourceBundle resources) {
	
		lbl_punto.setText("Esperando...");
    	label_titulo.setText("Esperando...");
        label_proponente.setText("Esperando...");
		
		
		
	}

}
