package application;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.Socket;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.List;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.jfoenix.controls.JFXButton;

import gad.manta.common.IServidor;
import gad.manta.common.Usuario;
import gad.manta.common.Voto;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class IniSesionControll {
	private static IServidor servidor;
	
    @FXML
    private JFXButton btnIniVoto,btn_fin,btn_voz;
    
    public static Thread th;
    Socket sock;
    DataOutputStream dos;
    DataInputStream dis;
	
	
	//inicializamos socket
    public IniSesionControll() {
		
		try {
			servidor = (IServidor)Naming.lookup("rmi://192.168.1.6/VotoE");
		} catch (MalformedURLException | RemoteException | NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {

            sock = new Socket(data.ip, data.port);
            dos = new DataOutputStream(sock.getOutputStream());
            dis = new DataInputStream(sock.getInputStream());

            dos.writeUTF(data.voto);
            //System.out.println(data.name);
            /*
            * This Thread let the client recieve the message from the server for any time;
            */
            th = new Thread(() -> {
                try {

                    JSONParser parser = new JSONParser();

                    while(true) {
                        String newMsgJson = dis.readUTF();

                        System.out.println("RE : " + newMsgJson);
                        Message newMsg = new Message();
                      

                        Object obj = parser.parse(newMsgJson);
                        JSONObject msg = (JSONObject) obj;

                        newMsg.setName((String) msg.get("name"));
                        newMsg.setMessage((String) msg.get("message"));
                        
                                                
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                            	
                            }
                        });
                                                
                    }
                } catch(Exception E) {
                    E.printStackTrace();
                }
            });
            th.start();
        } catch(IOException E) {
            E.printStackTrace();
        }  

    }

    @FXML
    void IniVotoAction(ActionEvent event) throws IOException {
    	/*// get a handle to the stage
	    Stage actualStage = (Stage) btnIniVoto.getScene().getWindow();
	    // do what you have to do
	    actualStage.close();
	    */
	    
	    Stage newStage = new Stage();
		
	   
	   AnchorPane pane = (AnchorPane)FXMLLoader.load(getClass().getResource("InicioVoto.fxml"));
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
    private AnchorPane panelvoz;
    @FXML
    void finAction(ActionEvent event) {
    	// get a handle to the stage
	    Stage stage = (Stage) btn_fin.getScene().getWindow();
	    // do what you have to do
	    stage.close();
    }
    
    @FXML
    void handleButtonAction(ActionEvent event) {
    	if(event.getSource()==btn_voz) {
    		
    		if(panelvoz.visibleProperty().getValue()==true)
    		{
    			panelvoz.setVisible(false);
    		}
    		else {
    			panelvoz.setVisible(true);
    		}
    		
    	}
    }
   
    

}
