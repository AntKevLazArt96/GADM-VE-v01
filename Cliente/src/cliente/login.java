package cliente;


import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class login implements Initializable{
    @FXML public TextField server_ip;
    @FXML public TextField port;
    @FXML public TextField name;
    @FXML public String sPort;




    public void onClick() throws IOException{
        System.out.println("Clicked");
        data.ip = server_ip.getText();
        this.sPort = port.getText();
        data.name = name.getText();
        data.port = Integer.parseInt(sPort);


        Stage stage;
        stage = (Stage) server_ip.getScene().getWindow();
        //Parent parent = FXMLLoader.load(getClass().getResource("room.fxml"));
        Parent root = FXMLLoader.load(login.class.getResource("room.fxml"));
        stage.setScene(new Scene(root, 600, 400));
        stage.setTitle(data.name);
        stage.setOnCloseRequest(e-> {
        	
            //e.consume();
            room.th.stop();
            System.exit(0);
        });
        stage.setResizable(false);

        stage.show();
    }




	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		server_ip.setText("192.168.1.6");
		port.setText("6666");
		name.setText("secretaria");
		
	}






}
