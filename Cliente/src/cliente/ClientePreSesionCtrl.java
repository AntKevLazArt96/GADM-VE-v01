package cliente;

import java.io.ByteArrayInputStream;
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
import com.jfoenix.controls.JFXTextField;

import cliente.data;
import gad.manta.common.Documentacion;
import gad.manta.common.IServidor;
import gad.manta.common.OrdenDia;
import gad.manta.common.Sesion;
import gad.manta.common.Usuario;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
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
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class ClientePreSesionCtrl implements Initializable  {
	volatile boolean ejecutar = true;
	@FXML
    private Circle cirlogin;
	@FXML
    private Label lbl_nombre;
	@FXML
    private JFXButton btn_voz;
	Sesion sesion;
	
	@FXML
    private Label lblOrden;
	//variable statica para rmi
	private static IServidor servidor;
	
	//variables estaticas para socket
	public static Thread th;
    Socket sock;
    DataOutputStream dos;
    DataInputStream dis;
	int sesionDe = 0;
	
    @FXML
    private JFXButton btnIniSesion;
    @FXML
    private Label label_convocatoria;
    @FXML
    private JFXTextArea label_titulo;
    @FXML
    private TableView<OrdenDia> tabla_ordenDia;
    @FXML
    private Label label_punto;
    
    @FXML
    private TableView<Documentacion> table_documentacion;
    @FXML
    private JFXTextField sesionA;
    
    @FXML
    private JFXButton btn_pdf;
	
	//inicializamos socket
	 public ClientePreSesionCtrl() {
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
	                        Mensage newMsg = new Mensage();

	                        Object obj = parser.parse(newMsgJson);
	                        JSONObject msg = (JSONObject) obj;

	                        newMsg.setName((String) msg.get("name"));
	                        newMsg.setMessage((String) msg.get("status"));
	                        Platform.runLater(new Runnable() {
	                           @Override
	                            public void run() {
	                            	
	                            	if(newMsg.getName()!= null && newMsg.getName()!= null && newMsg.getName().equals("cambio de pantalla")) {
	                            		System.out.println("estoy en el cliente y se cambio de pantalla en el servidor");
	                            		
	                            		Stage stage = (Stage) label_punto.getScene().getWindow();
									    // do what you have to do
									    stage.close();
									    Stage newStage = new Stage();
	                            		
	                            	    AnchorPane pane = null;
										try {
											pane = (AnchorPane)FXMLLoader.load(getClass().getResource("ClienteVotoOrden.fxml"));
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
	 public Image convertirImg(byte[] bytes) throws IOException {
			ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
			
			Image img = new Image(bis);
			return img;
		}
	 
		@SuppressWarnings({ "rawtypes", "unchecked" })
		@Override
		public void initialize(URL arg0, ResourceBundle arg1) {
				try {
					servidor = (IServidor)Naming.lookup("rmi://192.168.1.6/VotoE");
				} catch (MalformedURLException | RemoteException | NotBoundException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
			
			lbl_nombre.setText(data.name);
			
			try {
				Usuario user = servidor.usuario(data.name);
				data.img=user.getImg();
				Image im = convertirImg(data.img);
				data.Imagen = im;
		        cirlogin.setFill(new ImagePattern(im));
		        cirlogin.setStroke(Color.SEAGREEN);
		        cirlogin.setEffect(new DropShadow(+25d, 0d, +2d, Color.DARKGREEN));
			
			} catch (RemoteException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			
			
			try {
				
				sesion = servidor.consultarSesion();
				label_titulo.setText(sesion.getDescription());
				label_convocatoria.setText(sesion.getConvocatoria());
				
				List<OrdenDia>lista_orden=servidor.consultarOrden();
				TableColumn num_punto = new TableColumn("No. Punto");
				num_punto.setMinWidth(50);
				num_punto.setCellValueFactory(
		                new PropertyValueFactory<>("numeroPunto"));
		 
		        TableColumn descripcion = new TableColumn("Descripción");
		        descripcion.setMinWidth(900);
		        descripcion.setCellValueFactory(
		                new PropertyValueFactory<>("tema"));
		 
		        TableColumn proponente = new TableColumn("Proponente");
		        proponente.setMinWidth(300);
		        proponente.setCellValueFactory(
		                new PropertyValueFactory<>("proponente_nombre"));
				ObservableList<OrdenDia> datos = FXCollections.observableArrayList(
						lista_orden
						);
				tabla_ordenDia.getColumns().addAll(num_punto,descripcion,proponente);
				tabla_ordenDia.setItems(datos);
		
				List<Documentacion>lista_documentacion=servidor.mostrarDocumentacion();
				TableColumn punto = new TableColumn("Documentación perteneciente al punto");
				punto.setMinWidth(250);
				punto.setCellValueFactory(
		                new PropertyValueFactory<>("punto"));
		 
		        TableColumn nombre = new TableColumn("Nombre");
		        nombre.setMinWidth(400);
		        nombre.setCellValueFactory(
		                new PropertyValueFactory<>("nombre"));
		 
		  
				ObservableList<Documentacion> datos_pdf = FXCollections.observableArrayList(
						lista_documentacion
						);
				table_documentacion.getColumns().addAll(punto,nombre);
				table_documentacion.setItems(datos_pdf);
		
				
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
		}
		
		@FXML
	    void mostrar_documentacion(MouseEvent  event) throws RemoteException{
	    	
	    	
	    }
		
		@FXML
	    void mostrar_acta(ActionEvent event) {
			
			Stage newStage = new Stage();
			AnchorPane pane;
			try {
				data.id_acta=sesion.getId_pdf();
				pane = (AnchorPane)FXMLLoader.load(getClass().getResource("LecturaPDF.fxml"));
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
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        

	    }
		@FXML
	    void mostrar_pdf(MouseEvent event) {
			
			Stage newStage = new Stage();
			
			AnchorPane pane;
			try {
				
				data.id_acta=0;
				data.id_pdf=table_documentacion.getSelectionModel().selectedItemProperty().get().getId_pdf();
				System.out.println(data.id_pdf);
				
				pane = (AnchorPane)FXMLLoader.load(getClass().getResource("LecturaPDF.fxml"));
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
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        

	    }
		
		@FXML
	    void pedirPalabra(ActionEvent event) {

	    }

	}

