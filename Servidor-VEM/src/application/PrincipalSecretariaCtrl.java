package application;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
//import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.Socket;
import java.net.URL;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
//import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
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

public class PrincipalSecretariaCtrl implements Initializable{
	
	@FXML
    private Circle cirlogin;
	@FXML
    private Label lbl_nombre;
	@FXML
    private JFXButton btn_voz;
	@FXML
    private JFXButton btn_modificar;
	
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
<<<<<<< HEAD
    public Sesion sesion;
	//inicializamos socket
	 public PrincipalSecretariaCtrl() {
	        try {
=======
    
    @FXML
    private JFXButton btn_voz;
	@FXML
    private JFXButton btn_modificar;
	
	@FXML
    private Label lblOrden;

	
   
    public Sesion sesion;
>>>>>>> anthony

	            
	    	    data.ip = "192.168.1.6";
	            data.port = 6666;
	    	    data.name = "secretaria";
	    	    
	    	    
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

<<<<<<< HEAD
	                    while(true) {
	                        String newMsgJson = dis.readUTF();

	                        System.out.println("RE : " + newMsgJson);
	                        Message newMsg = new Message();

	                        Object obj = parser.parse(newMsgJson);
	                        JSONObject msg = (JSONObject) obj;

	                        newMsg.setName((String) msg.get("name"));
	                        newMsg.setMessage((String) msg.get("status"));
	                        Platform.runLater(new Runnable() {
	                            @SuppressWarnings("deprecation")
								@Override
	                            public void run() {
	                            	if(newMsg.getName().equals("cambio de pantalla")) {
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
	                            		
	                            		
	                            		
	                            	}
	                            }
	                        });
	                                                
	                    }
	                } catch(Exception E) {
	                    E.printStackTrace();
	                }

	            });
=======
    @FXML
    void modificar_sesion(ActionEvent event) {
		
		try {
			Stage newStage = new Stage();
			
			AnchorPane pane;
			
			pane = (AnchorPane)FXMLLoader.load(getClass().getResource("ModificacionSesion.fxml"));
			Scene scene = new Scene(pane);
	        
	        //Pantalla completa
	      /*  Screen screen = Screen.getPrimary();
			Rectangle2D bounds = screen.getVisualBounds();
			
			newStage.setX(bounds.getMinX());
			newStage.setY(bounds.getMinY());
			newStage.setWidth(bounds.getWidth());
			newStage.setHeight(bounds.getHeight());*/
	        
	        newStage.setScene(scene);
	        newStage.initStyle(StageStyle.UNDECORATED);
	        newStage.show();
	        
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        

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
>>>>>>> anthony

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
				/*data.img=user.getImg();
				Image im = convertirImg(data.img);
				data.Imagen = im;
		        cirlogin.setFill(new ImagePattern(im));
		        cirlogin.setStroke(Color.SEAGREEN);
		        cirlogin.setEffect(new DropShadow(+25d, 0d, +2d, Color.DARKGREEN));*/
			
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
				data.id_acta=sesion.getId_pdf();
				
				List<OrdenDia>lista_orden=servidor.consultarOrden();
				@SuppressWarnings("rawtypes")
				TableColumn num_punto = new TableColumn("No. Punto");
				num_punto.setMinWidth(50);
				num_punto.setCellValueFactory(
		                new PropertyValueFactory<>("numeroPunto"));
		 
		        @SuppressWarnings("rawtypes")
				TableColumn descripcion = new TableColumn("DescripciÃ³n");
		        descripcion.setMinWidth(900);
		        descripcion.setCellValueFactory(
		                new PropertyValueFactory<>("tema"));
		        @SuppressWarnings("rawtypes")
		        TableColumn proponente = new TableColumn("Proponente");
		        proponente.setMinWidth(300);
		        proponente.setCellValueFactory(
		                new PropertyValueFactory<>("proponente_nombre"));
				ObservableList<OrdenDia> datos = FXCollections.observableArrayList(
						lista_orden
						);
				tabla_ordenDia.getColumns().addAll(num_punto,descripcion,proponente);
				tabla_ordenDia.setItems(datos);
				@SuppressWarnings("rawtypes")
				List<Documentacion>lista_documentacion=servidor.mostrarDocumentacion();
				 @SuppressWarnings("rawtypes")
			        TableColumn pdf = new TableColumn("id");
			        pdf.setMinWidth(500);
			        pdf.setVisible(false);
			        pdf.setCellValueFactory(
			                new PropertyValueFactory<>("id_pdf"));
			        
				@SuppressWarnings("rawtypes")
				TableColumn punto = new TableColumn("DocumentaciÃ³n perteneciente al punto");
				punto.setMinWidth(250);
				punto.setCellValueFactory(
		                new PropertyValueFactory<>("punto"));
				@SuppressWarnings("rawtypes")
		        TableColumn nombre = new TableColumn("Nombre");
		        nombre.setMinWidth(700);
		        nombre.setResizable(true);
		        
		        nombre.setCellValueFactory(
		                new PropertyValueFactory<>("nombre"));
		       
				ObservableList<Documentacion> datos_pdf = FXCollections.observableArrayList(
						lista_documentacion
						);
				table_documentacion.getColumns().addAll(pdf,punto,nombre);
				
				table_documentacion.setItems(datos_pdf);
		
<<<<<<< HEAD
				
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
=======
		File f = new File("C:\\librerias\\concejal1.png");
        Image im = new Image(f.toURI().toString());
        cirlogin.setFill(new ImagePattern(im));
        cirlogin.setStroke(Color.SEAGREEN);
        cirlogin.setEffect(new DropShadow(+25d, 0d, +2d, Color.DARKGREEN));
        
		try {
			servidor = (IServidor)Naming.lookup("rmi://192.168.1.6/VotoE");
>>>>>>> anthony
			
			sesion = servidor.consultarSesion();
			label_titulo.setText(sesion.getDescription());
			label_convocatoria.setText(sesion.getConvocatoria());
			
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
			
<<<<<<< HEAD
			newStage.setX(bounds.getMinX());
			newStage.setY(bounds.getMinY());
			newStage.setWidth(bounds.getWidth());
			newStage.setHeight(bounds.getHeight());
	        
	        newStage.setScene(scene);
	        newStage.initStyle(StageStyle.UNDECORATED);
	        newStage.show();
=======
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
			    TableColumn pdf = new TableColumn("id");
		        pdf.setMinWidth(500);
		        pdf.setVisible(false);
		        pdf.setCellValueFactory(
		                new PropertyValueFactory<>("id_pdf"));
		        
			TableColumn punto = new TableColumn("Documentación perteneciente al punto");
			punto.setMinWidth(250);
			punto.setCellValueFactory(
	                new PropertyValueFactory<>("punto"));
			TableColumn nombre = new TableColumn("Nombre");
	        nombre.setMinWidth(700);
	        nombre.setResizable(true);
	        
	        nombre.setCellValueFactory(
	                new PropertyValueFactory<>("nombre"));
	       
			ObservableList<Documentacion> datos_pdf = FXCollections.observableArrayList(
					lista_documentacion
					);
			table_documentacion.getColumns().addAll(pdf,punto,nombre);
			
			table_documentacion.setItems(datos_pdf);
	
>>>>>>> anthony
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        

    }
	
	
<<<<<<< HEAD
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
    void modificar_sesion(ActionEvent event) {
		
		try {
			Stage newStage = new Stage();
			
			AnchorPane pane;
			
			pane = (AnchorPane)FXMLLoader.load(getClass().getResource("ModificacionSesion.fxml"));
			Scene scene = new Scene(pane);
	        
	        //Pantalla completa
	      /*  Screen screen = Screen.getPrimary();
			Rectangle2D bounds = screen.getVisualBounds();
			
			newStage.setX(bounds.getMinX());
			newStage.setY(bounds.getMinY());
			newStage.setWidth(bounds.getWidth());
			newStage.setHeight(bounds.getHeight());*/
	        
	        newStage.setScene(scene);
	        newStage.initStyle(StageStyle.UNDECORATED);
	        newStage.show();
	        
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
=======
	
>>>>>>> anthony

    }
	



	

}
