package application;

import java.awt.EventQueue;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;

import gad.manta.common.IServidor;
import gad.manta.common.Usuario;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class QuorumCtrl implements Initializable {
	private static IServidor servidor;
	
	public static Thread th;
    Socket sock;
    DataOutputStream dos;
    DataInputStream dis;
    
    @FXML
    private Circle cirlogin;
    @FXML
    private Label lbl_nombre;
    
    
    @FXML
    private Label lblpresentes;

    @FXML
    private Label lblausentes;
    
    @FXML
    private JFXButton btn_voz;

    @FXML
    private JFXButton btnIniVoto;

    @FXML
    private JFXButton btnCerrar;

    @FXML
    private ImageView img2;

    @FXML
    private ImageView img1;

    @FXML
    private ImageView img3;

    @FXML
    private ImageView img4;

    @FXML
    private ImageView img5;

    @FXML
    private ImageView img6;

    @FXML
    private Label user1;

    @FXML
    private Label status1;

    @FXML
    private Label user2;

    @FXML
    private Label user3;

    @FXML
    private Label user4;

    @FXML
    private Label user5;

    @FXML
    private Label user6;

    @FXML
    private Label status3;

    @FXML
    private Label status5;

    @FXML
    private Label status6;

    @FXML
    private Label status2;

    @FXML
    private Label status4;

    @FXML
    private ImageView img8;

    @FXML
    private ImageView img7;

    @FXML
    private ImageView img9;

    @FXML
    private ImageView img10;

    @FXML
    private ImageView img11;

    @FXML
    private ImageView img12;

    @FXML
    private Label user7;

    @FXML
    private Label status7;

    @FXML
    private Label user8;

    @FXML
    private Label user9;

    @FXML
    private Label user10;

    @FXML
    private Label user11;

    @FXML
    private Label user12;

    @FXML
    private Label status8;

    @FXML
    private Label status9;

    @FXML
    private Label status11;

    @FXML
    private Label status10;

    @FXML
    private Label estatus12;
    @FXML
    private JFXTextArea txtCumple;

    @FXML
    private JFXButton btn_fin;
    
   
    
	public QuorumCtrl() {
		
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

            dos.writeUTF(data.name);
            System.out.println(data.name);
            /*
            * This Thread let the client recieve the message from the server for any time;
            */
            th = new Thread(() -> {
                try {

                    JSONParser parser = new JSONParser();

                    while(true) {
                        String newMsgJson = dis.readUTF();

                        System.out.println("RE : " + newMsgJson);
                        Usuario newMsg = new Usuario();
                      

                        Object obj = parser.parse(newMsgJson);
                        JSONObject msg = (JSONObject) obj;

                        newMsg.setNombre((String) msg.get("name"));
                        newMsg.setStatus((String) msg.get("message"));
                        
                                                
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                            	try {
									List<Usuario> lista = servidor.consultaQuorum();
									
									lblpresentes.setText(String.valueOf(lista.size()));
									
									lblausentes.setText(String.valueOf(12-lista.size()));
									
									
									for (int i = 0; i < lista.size(); i++) {
	                            		img1.setVisible(true);
										user1.setVisible(true);
										user1.setText(lista.get(0).getNombre());
										status1.setText(lista.get(0).getStatus());
										status1.setVisible(true);
										if(lista.size()==2) {
											img2.setVisible(true);
											user2.setText(lista.get(1).getNombre());
											user2.setVisible(true);
											status2.setText(lista.get(1).getStatus());
											status2.setVisible(true);
										}
										if(lista.size()==3) {
											img3.setVisible(true);
											user3.setText(lista.get(2).getNombre());
											user3.setVisible(true);
											status3.setText(lista.get(2).getStatus());
											status3.setVisible(true);
										}
										if(lista.size()==4) {
											img4.setVisible(true);
											user4.setText(lista.get(3).getNombre());
											user4.setVisible(true);
											status4.setText(lista.get(3).getStatus());
											status4.setVisible(true);
										}
										if(lista.size()==5) {
											img5.setVisible(true);
											user5.setText(lista.get(4).getNombre());
											user5.setVisible(true);
											status5.setText(lista.get(4).getStatus());
											status5.setVisible(true);
										}
										if(lista.size()==6) {
											img6.setVisible(true);
											user6.setText(lista.get(5).getNombre());
											user6.setVisible(true);
											status6.setText(lista.get(5).getStatus());
											status6.setVisible(true);
										}
										if(lista.size()==7) {
											img7.setVisible(true);
											user7.setText(lista.get(6).getNombre());
											user7.setVisible(true);
											status7.setText(lista.get(6).getStatus());
											status7.setVisible(true);
										}
										if(lista.size()==8) {
											img8.setVisible(true);
											user8.setText(lista.get(7).getNombre());
											user8.setVisible(true);
											status8.setText(lista.get(7).getStatus());
											status8.setVisible(true);
										}
										if(lista.size()==9) {
											img9.setVisible(true);
											user9.setText(lista.get(8).getNombre());
											user9.setVisible(true);
											status9.setText(lista.get(8).getStatus());
											status9.setVisible(true);
										}
										if(lista.size()==10) {
											img10.setVisible(true);
											user10.setText(lista.get(9).getNombre());
											user10.setVisible(true);
											status10.setText(lista.get(9).getStatus());
											status10.setVisible(true);
										}
										if(lista.size()==11) {
											img11.setVisible(true);
											user11.setText(lista.get(10).getNombre());
											user11.setVisible(true);
											status11.setText(lista.get(10).getStatus());
											status11.setVisible(true);
										}
										if(lista.size()==12) {
											img12.setVisible(true);
											user12.setText(lista.get(11).getNombre());
											user12.setVisible(true);
											estatus12.setText(lista.get(11).getStatus());
											estatus12.setVisible(true);
										}
										
										if(lista.size()>=0) {
											btnIniVoto.setDisable(false);
											txtCumple.setText("Cumple con el mínimo de miembros para inicar la sesión");
											
										}
									}
									
								} catch (RemoteException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}                       
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
    	Stage stage = (Stage) btnIniVoto.getScene().getWindow();
	    // do what you have to do
	    stage.close();
    	
    	Stage newStage = new Stage();
		
	    AnchorPane pane = (AnchorPane)FXMLLoader.load(getClass().getResource("PrincipalSecretaria.fxml"));
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
    void onCerrar(ActionEvent event) {
    	// get a handle to the stage
	    Stage stage = (Stage) btnCerrar.getScene().getWindow();
	    // do what you have to do
	    stage.close();
    }

    @FXML
    void finAction(ActionEvent event) {

    }



	@Override
	public void initialize(URL location, ResourceBundle resources) {
		lbl_nombre.setText(data.name);
		
		cirlogin.setStroke(Color.SEAGREEN);
		File f = new File("C:\\GIT\\GADM-VE-v01\\Servidor-VEM\\res\\concejal1.png");
        Image im = new Image(f.toURI().toString());
        cirlogin.setFill(new ImagePattern(im));
        cirlogin.setEffect(new DropShadow(+25d, 0d, +2d, Color.DARKSEAGREEN));
		
	}
	
	@FXML
    void handleButtonAction(ActionEvent event) {

    }


	

}
