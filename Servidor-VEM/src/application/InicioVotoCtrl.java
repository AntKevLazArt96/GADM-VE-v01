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
import java.util.List;
import java.util.ResourceBundle;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;

import gad.manta.common.IServidor;
import gad.manta.common.Sesion;
import gad.manta.common.Usuario;
import gad.manta.common.Voto;
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



public class InicioVotoCtrl implements Initializable {
	volatile boolean ejecutar = true;
	private static IServidor servidor;
	@FXML
    private Label label_convocatoria;
	

    @FXML
    private Circle cirlogin;

    @FXML
    private Label lbl_nombre;
    
    
    @FXML
    private JFXButton btn_voz;

    @FXML
    private JFXButton btn_finVoto;
    
    @FXML
    private JFXButton btn_reVoto;

    @FXML
    private AnchorPane panelvoz;
    
    //imagenes
    @FXML
    private ImageView img1,img2,img3,img4,img5,img6,img7,img8,img9,img10,img11,img12;

    //nombre de usuarios
    @FXML
    private Label user1,user2,user3,user4,user5,user6,user7,user8,user9,user10,user11,user12;
    
    //Votacion de los usurios
    
    @FXML
    private Label status1,status2,status3,status4,status5,status6,status7,status8,status9,status10,status11,estatus12;
    
    //botones de reiniciar el voto individual
    
    @FXML
    private JFXButton btnRe1,btnRe2,btnRe3,btnRe4,btnRe5,btnRe6,btnRe7,btnRe8,btnRe9,btnRe10,btnRe11,btnRe12;
    
    

    
    
    @FXML
    private JFXTextArea label_titulo;
    
    @FXML
    private Label lbl_punto;
    
    @FXML
    private Label lbl_proponente;
    
    @FXML
    private Label lblAFavor;

    @FXML
    private Label lblEnContra;
    
    @FXML
    private Label lblBlanco;

    @FXML
    private Label lblSalvoVoto;
    
    @FXML
    private Label lblEspera;
  //meotodo para convertir la la imagen
    public Image convertirImg(byte[] bytes) throws IOException {
		ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
		
		Image img = new Image(bis);
		return img;
	}
  //variables estaticas para socket
  	public static Thread th;
      Socket sock;
      DataOutputStream dos;
      DataInputStream dis;
     
  //listas
      List<Voto> lista;
      List<Voto> favor;
      

      public InicioVotoCtrl() {
    		
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

                dos.writeUTF("cambio de pantalla2");
                //System.out.println(data.name);
                /*
                * This Thread let the client recieve the message from the server for any time;
                */
                th = new Thread(() -> {
                    try {

                        JSONParser parser = new JSONParser();

                        while(ejecutar) {
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
                                	try {
                                		//lista que devuelve todos los votantes con sus votos
                                		lista = servidor.votantesPunto();
    									favor = servidor.votosAFavor();
    									List<Voto> contra = servidor.votosEnContra();
    									List<Voto> salvados = servidor.votosSalvados();
    									List<Voto> blanco = servidor.votosBlanco();
    									
    									//lista que verifica el total de asistentes
    									List<Usuario> quorum = servidor.consultaQuorum();
      									
    									
    									lblAFavor.setText(String.valueOf(favor.size()));
    									lblEnContra.setText(String.valueOf(contra.size()));
    									lblSalvoVoto.setText(String.valueOf(salvados.size()));
    									lblBlanco.setText(String.valueOf(blanco.size()));
    								
    									    									
    									lblEspera.setText(String.valueOf(quorum.size()-lista.size()));
    									
    									
    									//reiniciando los usuarios
    									if (lista.size() == 0) {
    										limpiar();
    									}
    									
    									for (int i = 0; i < lista.size(); i++) {
    										if (i == 0) {
    											img1.setVisible(true);
    											img1.setImage(convertirImg(lista.get(i).getImg()));
    											user1.setVisible(true);
    											user1.setText(lista.get(i).getNombre());
    											status1.setText(lista.get(i).getVoto());
    											status1.setVisible(true);
    											btnRe1.setVisible(true);
    											if(lista.get(i).getVoto().equals("RECHAZADO")) {
    												status1.setStyle("-fx-text-fill: red;");
    											}
    										}
    										if (i == 1) {
    											img2.setImage(convertirImg(lista.get(i).getImg()));
    											img2.setVisible(true);
    											user2.setText(lista.get(i).getNombre());
    											user2.setVisible(true);
    											status2.setText(lista.get(i).getVoto());
    											status2.setVisible(true);
    											btnRe2.setVisible(true);
    											if(lista.get(i).getVoto().equals("RECHAZADO")) {
    												status2.setStyle("-fx-text-fill: red;");
    											}
    										}
    										if (i == 2) {
    											img3.setImage(convertirImg(lista.get(i).getImg()));
    											img3.setVisible(true);
    											user3.setText(lista.get(i).getNombre());
    											user3.setVisible(true);
    											status3.setText(lista.get(i).getVoto());
    											status3.setVisible(true);
    											btnRe3.setVisible(true);
    											if(lista.get(i).getVoto().equals("RECHAZADO")) {
    												status3.setStyle("-fx-text-fill: red;");
    											}
    										}
    										if (i == 3) {
    											img4.setImage(convertirImg(lista.get(i).getImg()));
    											img4.setVisible(true);
    											user4.setText(lista.get(i).getNombre());
    											user4.setVisible(true);
    											status4.setText(lista.get(i).getVoto());
    											status4.setVisible(true);
    											btnRe4.setVisible(true);
    											if(lista.get(i).getVoto().equals("RECHAZADO")) {
    												status4.setStyle("-fx-text-fill: red;");
    											}
    										}
    										if (i == 4) {
    											img5.setImage(convertirImg(lista.get(i).getImg()));
    											img5.setVisible(true);
    											user5.setText(lista.get(i).getNombre());
    											user5.setVisible(true);
    											status5.setText(lista.get(i).getVoto());
    											status5.setVisible(true);
    											btnRe5.setVisible(true);
    											if(lista.get(i).getVoto().equals("RECHAZADO")) {
    												status5.setStyle("-fx-text-fill: red;");
    											}
    										}
    										if (i == 5) {
    											img6.setImage(convertirImg(lista.get(i).getImg()));
    											img6.setVisible(true);
    											user6.setText(lista.get(i).getNombre());
    											user6.setVisible(true);
    											status6.setText(lista.get(i).getVoto());
    											status6.setVisible(true);
    											btnRe6.setVisible(true);
    											if(lista.get(i).getVoto().equals("RECHAZADO")) {
    												status6.setStyle("-fx-text-fill: red;");
    											}
    										}
    										if (i == 6) {
    											img7.setImage(convertirImg(lista.get(i).getImg()));
    											img7.setVisible(true);
    											user7.setText(lista.get(i).getNombre());
    											user7.setVisible(true);
    											status7.setText(lista.get(i).getVoto());
    											status7.setVisible(true);
    											btnRe7.setVisible(true);
    											if(lista.get(i).getVoto().equals("RECHAZADO")) {
    												status7.setStyle("-fx-text-fill: red;");
    											}
    										}
    										if (i == 7) {
    											img8.setImage(convertirImg(lista.get(i).getImg()));
    											img8.setVisible(true);
    											user8.setText(lista.get(i).getNombre());
    											user8.setVisible(true);
    											status8.setText(lista.get(i).getVoto());
    											status8.setVisible(true);
    											btnRe8.setVisible(true);
    											if(lista.get(i).getVoto().equals("RECHAZADO")) {
    												status8.setStyle("-fx-text-fill: red;");
    											}
    										}
    										if (i == 8) {
    											img9.setImage(convertirImg(lista.get(i).getImg()));
    											img9.setVisible(true);
    											user9.setText(lista.get(i).getNombre());
    											user9.setVisible(true);
    											status9.setText(lista.get(i).getVoto());
    											status9.setVisible(true);
    											btnRe9.setVisible(true);
    											if(lista.get(i).getVoto().equals("RECHAZADO")) {
    												status9.setStyle("-fx-text-fill: red;");
    											}
    										}
    										if (i == 9) {
    											img10.setImage(convertirImg(lista.get(i).getImg()));
    											img10.setVisible(true);
    											user10.setText(lista.get(i).getNombre());
    											user10.setVisible(true);
    											status10.setText(lista.get(i).getVoto());
    											status10.setVisible(true);
    											btnRe10.setVisible(true);
    											if(lista.get(i).getVoto().equals("RECHAZADO")) {
    												status10.setStyle("-fx-text-fill: red;");
    											}
    										}
    										if (i == 10) {
    											img11.setImage(convertirImg(lista.get(i).getImg()));
    											img11.setVisible(true);
    											user11.setText(lista.get(i).getNombre());
    											user11.setVisible(true);
    											status11.setText(lista.get(i).getVoto());
    											status11.setVisible(true);
    											btnRe11.setVisible(true);
    											if(lista.get(i).getVoto().equals("RECHAZADO")) {
    												status11.setStyle("-fx-text-fill: red;");
    											}
    										}
    										if (i == 11) {
    											img12.setImage(convertirImg(lista.get(i).getImg()));
    											img12.setVisible(true);
    											user12.setText(lista.get(i).getNombre());
    											user12.setVisible(true);
    											estatus12.setText(lista.get(i).getVoto());
    											estatus12.setVisible(true);
    											btnRe12.setVisible(true);
    											if(lista.get(i).getVoto().equals("RECHAZADO")) {
    												estatus12.setStyle("-fx-text-fill: red;");
    											}
    										}
    										
    										
    										 if(lista.size()>=0) { 
    											 btn_finVoto.setDisable(false);
    											 btn_reVoto.setDisable(false);
    											 
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

      void limpiar () {
    	  img1.setVisible(false);
			img2.setVisible(false);
			img3.setVisible(false);
			img4.setVisible(false);
			img5.setVisible(false);
			img6.setVisible(false);
			img7.setVisible(false);
			img8.setVisible(false);
			img9.setVisible(false);
			img10.setVisible(false);
			img11.setVisible(false);
			img12.setVisible(false);
			user1.setVisible(false);
			user2.setVisible(false);
			user3.setVisible(false);
			user4.setVisible(false);
			user5.setVisible(false);
			user6.setVisible(false);
			user7.setVisible(false);
			user8.setVisible(false);
			user9.setVisible(false);
			user10.setVisible(false);
			user11.setVisible(false);
			user12.setVisible(false);
			status1.setVisible(false);
			status2.setVisible(false);
			status3.setVisible(false);
			status4.setVisible(false);
			status5.setVisible(false);
			status6.setVisible(false);
			status7.setVisible(false);
			status8.setVisible(false);
			status9.setVisible(false);
			status10.setVisible(false);
			status11.setVisible(false);
			estatus12.setVisible(false);
			btnRe1.setVisible(false);
			btnRe2.setVisible(false);
			btnRe3.setVisible(false);
			btnRe4.setVisible(false);
			btnRe5.setVisible(false);
			btnRe6.setVisible(false);
			btnRe7.setVisible(false);
			btnRe8.setVisible(false);
			btnRe9.setVisible(false);
			btnRe10.setVisible(false);
			btnRe11.setVisible(false);
			btnRe12.setVisible(false);
			status1.setStyle("-fx-text-fill: #4caf50;");
			status2.setStyle("-fx-text-fill: #4caf50;");
			status3.setStyle("-fx-text-fill: #4caf50;");
			status4.setStyle("-fx-text-fill: #4caf50;");
			status5.setStyle("-fx-text-fill: #4caf50;");
			status6.setStyle("-fx-text-fill: #4caf50;");
			status7.setStyle("-fx-text-fill: #4caf50;");
			status8.setStyle("-fx-text-fill: #4caf50;");
			status9.setStyle("-fx-text-fill: #4caf50;");
			status10.setStyle("-fx-text-fill: #4caf50;");
			status11.setStyle("-fx-text-fill: #4caf50;");
			estatus12.setStyle("-fx-text-fill: #4caf50;");
      }
      
    
    @FXML
    void finVoto(ActionEvent event) throws IOException {
    	
        
        try {
			String message = servidor.limpiarVoto();
			System.out.println(message);
			data.header = "Votacion terminada";
			data.cuerpo = message;
			
			//Limpiar las listas
			lista.clear();
			/*aprueba.clear();
			rechaza.clear();
			*/
			// get a handle to the stage
		    Stage actualStage = (Stage) btn_finVoto.getScene().getWindow();
		    // do what you have to do
		    actualStage.close();
		    Stage newStage = new Stage();
		   AnchorPane pane = (AnchorPane)FXMLLoader.load(getClass().getResource("InicioSesion.fxml"));
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
	        ejecutar=false;
			
			try {
               
                //String json = "{" + " 'name' : '" + data.name + "', 'message' : '" + msg + "'" + "}";

                JSONObject js = new JSONObject();
                js.put("name", "VOTO TERMINADO");

                String json = js.toJSONString();


                System.out.println("Se envio:"+json);

                dos.writeUTF(json);

            } catch(IOException E) {
                E.printStackTrace();
            }
			
			
			
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        
        
        
        
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
    
    @Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		lbl_nombre.setText(data.name);
		
		File f = new File("C:\\librerias\\concejal1.png");
        Image im = new Image(f.toURI().toString());
        cirlogin.setFill(new ImagePattern(im));
        cirlogin.setStroke(Color.SEAGREEN);
        cirlogin.setEffect(new DropShadow(+25d, 0d, +2d, Color.DARKGREEN));
		label_convocatoria.setText(data.convocatoria_sesion);
		label_titulo.setText(puntoATratar.tema);
		lbl_punto.setText(puntoATratar.num_punto);
        lbl_proponente.setText(puntoATratar.proponente);
        btn_finVoto.setDisable(true);
		btn_reVoto.setDisable(true);
		
		try {
			servidor.limpiarVoto();
			//Se inicio la votacion
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
    
    @FXML
	void reiniciarVoto(ActionEvent event) throws IOException {
		try {
			String message = servidor.limpiarVoto();
			System.out.println(message);
			data.header = "Votos Reiniciados";
			data.cuerpo = message;
			
			//Limpiar las listas
			lista.clear();
			/*aprueba.clear();
			rechaza.clear();
			*/
			Stage newStage = new Stage();

			AnchorPane pane = (AnchorPane) FXMLLoader.load(getClass().getResource("VentanaDialogo.fxml"));

			Scene scene = new Scene(pane);
			newStage.setScene(scene);
			newStage.initStyle(StageStyle.UNDECORATED);
			newStage.show();
			
			try {
               
                //String json = "{" + " 'name' : '" + data.name + "', 'message' : '" + msg + "'" + "}";

                JSONObject js = new JSONObject();
                js.put("name", "REINICIAR");

                String json = js.toJSONString();


                System.out.println("Se envio:"+json);

                dos.writeUTF(json);

            } catch(IOException E) {
                E.printStackTrace();
            }
			
			
			
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		btn_finVoto.setDisable(true);
		btn_reVoto.setDisable(true);
	}
    
    @FXML
   	void re1(ActionEvent event) throws IOException {
    	
    	limpiar();
    	System.out.println("riniciando xD");
    	servidor.reiniciarVoto(user1.getText(), status1.getText(),0);
    	
    	try {
            
            //String json = "{" + " 'name' : '" + data.name + "', 'message' : '" + msg + "'" + "}";

            JSONObject js = new JSONObject();
            js.put("name", "REINICIAR2");
            js.put("message", user1.getText());

            String json = js.toJSONString();


            System.out.println("Se envio:"+json);

            dos.writeUTF(json);

        } catch(IOException E) {
            E.printStackTrace();
        }
    
    }
    @FXML
   	void re2(ActionEvent event) throws IOException {
    
    
    }
    @FXML
   	void re3(ActionEvent event) throws IOException {
    
    
    }
    @FXML
   	void re4(ActionEvent event) throws IOException {
    
    
    }
    @FXML
   	void re5(ActionEvent event) throws IOException {
    
    
    }
    @FXML
   	void re6(ActionEvent event) throws IOException {
    
    
    }
    @FXML
   	void re7(ActionEvent event) throws IOException {
    
    
    }
    @FXML
   	void re8(ActionEvent event) throws IOException {
    
    
    }
    @FXML
   	void re9(ActionEvent event) throws IOException {
    
    
    }
    @FXML
   	void re10(ActionEvent event) throws IOException {
    
    
    }
    @FXML
   	void re11(ActionEvent event) throws IOException {
    
    
    }
    
    @FXML
   	void re12(ActionEvent event) throws IOException {
    
    
    }
    
    
}
