package application;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.Socket;
import java.net.URL;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.sql.Date;
import java.util.Calendar;
import java.util.List;
import java.util.ResourceBundle;

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
import modelo.Asistencia;
import modelo.Conexion;
import modelo.Quorum;
import modelo.Sesion;

public class RegistrarAsistenciaCtrl implements Initializable {
	private static IServidor servidor;
	private Conexion conexion;
	
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
    private JFXButton btnasistencia;


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

 
    
    //meotodo para convertir la la imagen
    public Image convertirImg(byte[] bytes) throws IOException {
		ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
		
		Image img = new Image(bis);
		return img;
	}
    
	public RegistrarAsistenciaCtrl() {
		
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
									List<Usuario> lista = servidor.asistenciaUsuarios(data.idquorum);
									
									lblpresentes.setText(String.valueOf(lista.size()));
									
									lblausentes.setText(String.valueOf(12-lista.size()));
									
									
									for (int i = 0; i < lista.size(); i++) {
										img1.setVisible(true);
	                            		img1.setImage(convertirImg(lista.get(i).getImg()));
										user1.setVisible(true);
										user1.setText(lista.get(i).getNombre());
										status1.setText(lista.get(i).getStatus());
										status1.setVisible(true);
										
										if(i==1) {
											img2.setImage(convertirImg(lista.get(i).getImg()));
											img2.setVisible(true);
											user2.setText(lista.get(i).getNombre());
											user2.setVisible(true);
											status2.setText(lista.get(i).getStatus());
											status2.setVisible(true);
											
										}
										
										if(i==2) {
											img3.setImage(convertirImg(lista.get(i).getImg()));
											img3.setVisible(true);
											user3.setText(lista.get(i).getNombre());
											user3.setVisible(true);
											status3.setText(lista.get(i).getStatus());
											status3.setVisible(true);
											
										}
										if(i==3) {
											img4.setImage(convertirImg(lista.get(i).getImg()));
											img4.setVisible(true);
											user4.setText(lista.get(i).getNombre());
											user4.setVisible(true);
											status4.setText(lista.get(i).getStatus());
											status4.setVisible(true);
											
										}
										if(i==4) {
											img5.setImage(convertirImg(lista.get(i).getImg()));
											img5.setVisible(true);
											user5.setText(lista.get(i).getNombre());
											user5.setVisible(true);
											status5.setText(lista.get(i).getStatus());
											status5.setVisible(true);
										}
										if(i==5) {
											img6.setImage(convertirImg(lista.get(i).getImg()));
											img6.setVisible(true);
											user6.setText(lista.get(i).getNombre());
											user6.setVisible(true);
											status6.setText(lista.get(i).getStatus());
											status6.setVisible(true);
										}
										if(i==6) {
											img7.setImage(convertirImg(lista.get(i).getImg()));
											img7.setVisible(true);
											user7.setText(lista.get(i).getNombre());
											user7.setVisible(true);
											status7.setText(lista.get(i).getStatus());
											status7.setVisible(true);
										}
										if(i==7) {
											img8.setImage(convertirImg(lista.get(i).getImg()));
											img8.setVisible(true);
											user8.setText(lista.get(i).getNombre());
											user8.setVisible(true);
											status8.setText(lista.get(i).getStatus());
											status8.setVisible(true);
										}
										if(i==8) {
											img9.setImage(convertirImg(lista.get(i).getImg()));
											img9.setVisible(true);
											user9.setText(lista.get(i).getNombre());
											user9.setVisible(true);
											status9.setText(lista.get(i).getStatus());
											status9.setVisible(true);
										}
										if(i==9) {
											img10.setImage(convertirImg(lista.get(i).getImg()));
											img10.setVisible(true);
											user10.setText(lista.get(i).getNombre());
											user10.setVisible(true);
											status10.setText(lista.get(i).getStatus());
											status10.setVisible(true);
										}
										if(i==10) {
											img11.setImage(convertirImg(lista.get(i).getImg()));
											img11.setVisible(true);
											user11.setText(lista.get(i).getNombre());
											user11.setVisible(true);
											status11.setText(lista.get(i).getStatus());
											status11.setVisible(true);
										}
										if(i==11) {
											img12.setImage(convertirImg(lista.get(i).getImg()));
											img12.setVisible(true);
											user12.setText(lista.get(i).getNombre());
											user12.setVisible(true);
											estatus12.setText(lista.get(i).getStatus());
											estatus12.setVisible(true);
										}
										
										if(lista.size()>=0) {
											txtCumple.setText("Cumple con el mínimo de miembros para inicar la sesión");
											
										}
									}
									
								} catch (RemoteException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								} catch (IOException e) {
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
	int idquorum=0;
	@FXML
	void IniAsistencia(ActionEvent event) {
		btnasistencia.setVisible(false);
		Date fechaActual = new Date(Calendar.getInstance().getTime().getTime());
		System.out.println(fechaActual);
		
		conexion.establecerConexion();
		Quorum q = new Quorum(fechaActual);
		idquorum= q.guardarRegistro(conexion.getConnection());
		
		Sesion s = new Sesion(idquorum,data.convocatoria_sesion);
		s.addQuorum(conexion.getConnection());
		conexion.cerrarConexion();
		System.out.println("Se ha iniciado la asistencia");
		//PENDIENTE AQUI SE TIENE Q INICAR EL LOGIN DE LOS CONCEJALES
	}
    
    @FXML
    void IniVotoAction(ActionEvent event) throws IOException {
    	Stage stage = (Stage) btnasistencia.getScene().getWindow();
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





	@Override
	public void initialize(URL location, ResourceBundle resources) {
		conexion = new Conexion();
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
