package cliente;

import java.io.DataInputStream;
import java.io.DataOutputStream;
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
import gad.manta.common.Pdf;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class ClienteSesionCtrl implements Initializable {
	private static IServidor servidor;
	volatile boolean ejecutar =true;
    @FXML
    private JFXButton btn_voz;

    @FXML
    private Label label_convocatoria;
    @FXML
    private Label lbl_punto;
    @FXML
    private Circle cirlogin;

    @FXML
    private Label lbl_nombre;
    
    @FXML
    private JFXTextArea label_titulo;

    @FXML
    private Label label_proponente;

    @FXML
    private TableView<Pdf> table_pdf;
    
    public static Thread th;
    Socket sock;
    DataOutputStream dos;
    DataInputStream dis;
    
  //inicializamos socket
    public ClienteSesionCtrl() {
    	try {

            sock = new Socket(data.ip, data.port);
            dos = new DataOutputStream(sock.getOutputStream());
            dis = new DataInputStream(sock.getInputStream());

            dos.writeUTF(data.name);
            /*
            * This Thread let the client recieve the message from the server for any time;
            */
            th = new Thread(() -> {
                try {

                    JSONParser parser = new JSONParser();

                    while(ejecutar) {
                        String newMsgJson = dis.readUTF();

                        System.out.println("RE : " + newMsgJson);
                       
                        Object obj = parser.parse(newMsgJson);
                        JSONObject msg = (JSONObject) obj;
                        
                        
                        String punto =(String) msg.get("punto");
                        String titulo = (String) msg.get("titulo");
                        String proponente = (String) msg.get("proponente");
                        String id_pdf = (String) msg.get("id_pdf");
                        String name =(String) msg.get("name");
                        
                        Platform.runLater(new Runnable() {
                            @SuppressWarnings({ "rawtypes", "unchecked" })
							@Override
                            public void run() {
                            	if(punto!=null) {
                            		lbl_punto.setText(""+punto);
                                	label_titulo.setText(titulo);
                                    label_proponente.setText(proponente);
                                    
                                    puntoATratar.num_punto= punto;
                            		puntoATratar.tema = titulo;
                            		puntoATratar.proponente= proponente;
                            		puntoATratar.id_pdf=Integer.valueOf(id_pdf);
                            		
                            		try {
                        				
                        				
                        				List<Pdf> lista_pdfs=servidor.consultarPdfsPunto(Integer.valueOf(id_pdf));
                        				    TableColumn pdf = new TableColumn("id");
                        			        pdf.setMinWidth(500);
                        			        pdf.setVisible(false);
                        			        pdf.setCellValueFactory(
                        			                new PropertyValueFactory<>("id_pdf"));
                        			        
                        				
                        				TableColumn nombre = new TableColumn("Nombre");
                        		        nombre.setMinWidth(700);
                        		        nombre.setResizable(true);
                        		        nombre.setCellValueFactory(
                        		                new PropertyValueFactory<>("nombre"));
                        		        
                        		       
                        		       
                        				ObservableList<Pdf> datos_pdf = FXCollections.observableArrayList(
                        						lista_pdfs
                        						);
                        				table_pdf.getColumns().addAll(pdf,nombre);
                        				
                        				table_pdf.setItems(datos_pdf);
                                    
                                    
                                    } catch (RemoteException e) {
                        				// TODO Auto-generated catch block
                        				e.printStackTrace();
                        			}
                            		
                            	}else {
                            		lbl_punto.setText("Esperando...");
                                	label_titulo.setText("Esperando...");
                                    label_proponente.setText("Esperando...");
                            	}
                            	
                            	if(name!= null &&name.equals("cambio de pantalla2")) {
                            		System.out.println("estoy en el cliente y se cambio de pantalla en el servidor");
                            		//paso variables al voto
                            		Stage stage = (Stage) lbl_punto.getScene().getWindow();
								    // do what you have to do
								    stage.close();
								    Stage newStage = new Stage();
                            		
                            	    AnchorPane pane = null;
									try {
										pane = (AnchorPane)FXMLLoader.load(getClass().getResource("ClienteVoto.fxml"));
									} catch (IOException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
									
									
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
                                    ejecutar = false;
                        		
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
    
    
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		lbl_nombre.setText(data.name);
		cirlogin.setFill(new ImagePattern(data.Imagen));
        cirlogin.setStroke(Color.SEAGREEN);
        cirlogin.setEffect(new DropShadow(+25d, 0d, +2d, Color.DARKGREEN));
		label_convocatoria.setText(data.convocatoria);
		try {
			servidor = (IServidor)Naming.lookup("rmi://192.168.1.6/VotoE");
		} catch (MalformedURLException | RemoteException | NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
