package application;

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

import gad.manta.common.Documentacion;
import gad.manta.common.IServidor;
import gad.manta.common.OrdenDia;
import gad.manta.common.Pdf;
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

public class IniSesionControll implements Initializable {
	int posicionPersonaEnTabla;
	volatile boolean ejecutar = true;

	
	private static IServidor servidor;
	@FXML
    private Label label_convocatoria;
	@FXML
    private JFXTextArea label_titulo;
	@FXML
    private Label label_proponente;
	
	@FXML
    private Label lbl_punto;
	@FXML
    private TableView<OrdenDia> tabla_ordenDia;
	
	ObservableList<OrdenDia> datos;
	
	@FXML
    private TableView<Pdf> table_pdf;

    @FXML
    private Circle cirlogin;

    @FXML
    private Label lbl_nombre;
    
    @FXML
    private JFXButton btnIniVoto,btn_fin,btn_voz;
    
    public static Thread th;
    Socket sock;
    DataOutputStream dos;
    DataInputStream dis;
  //conexion para los mensajes en tiempo real de Socket
	
	//inicializamos socket
    public IniSesionControll() {
		
		
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

                    while(ejecutar) {
                        String newMsgJson = dis.readUTF();

                        System.out.println("RE : " + newMsgJson);
                        Message newMsg = new Message();
                      

                        Object obj = parser.parse(newMsgJson);
                        JSONObject msg = (JSONObject) obj;

                        newMsg.setName((String) msg.get("name"));
                        newMsg.setMessage((String) msg.get("status"));
                        
                                                
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
    	// get a handle to the stage
	    Stage actualStage = (Stage) btnIniVoto.getScene().getWindow();
	    // do what you have to do
	    actualStage.close();
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
        ejecutar=false;
        
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

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		lbl_nombre.setText(data.name);
		label_convocatoria.setText(data.convocatoria_sesion);
		
		File f = new File("C:\\librerias\\concejal1.png");
        Image im = new Image(f.toURI().toString());
        cirlogin.setFill(new ImagePattern(im));
        cirlogin.setStroke(Color.SEAGREEN);
        cirlogin.setEffect(new DropShadow(+25d, 0d, +2d, Color.DARKGREEN));
        
        try {
			servidor = (IServidor)Naming.lookup("rmi://192.168.1.6/VotoE");
			//Sesion sesion = servidor.consultarSesion();
			
			
			List<OrdenDia>lista_orden=servidor.consultarOrden();
			
			//cargo el orden del dia en los labels
			lbl_punto.setText("Seleccione el punto..");
			label_titulo.setText("Esperando...");
			label_proponente.setText("Esperando...");
			
			TableColumn id = new TableColumn("id");
	        id.setMinWidth(500);
	        id.setVisible(false);
	        id.setCellValueFactory(
	                new PropertyValueFactory<>("id"));
			
			
			TableColumn num_punto = new TableColumn("#");
			num_punto.setMinWidth(50);
			num_punto.setCellValueFactory(new PropertyValueFactory<>("numeroPunto"));
	 
	        TableColumn descripcion = new TableColumn("Descripción");
	        descripcion.setMinWidth(900);
	        descripcion.setCellValueFactory(
	                new PropertyValueFactory<>("tema"));
	 
	        TableColumn proponente = new TableColumn("Proponente");
	        proponente.setMinWidth(300);
	        proponente.setCellValueFactory(
	                new PropertyValueFactory<>("proponente"));
			datos = FXCollections.observableArrayList(lista_orden);
			
			tabla_ordenDia.getColumns().addAll(id,num_punto,descripcion,proponente);
			tabla_ordenDia.setItems(datos);
			
	
			
		} catch (MalformedURLException | RemoteException | NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
		
	}

	
	public OrdenDia getTablaPersonasSeleccionada() {
        if (tabla_ordenDia != null) {
            List<OrdenDia> tabla = tabla_ordenDia.getSelectionModel().getSelectedItems();
            if (tabla.size() == 1) {
                final OrdenDia competicionSeleccionada = tabla.get(0);
                return competicionSeleccionada;
            }
        }
        return null;
    }

    /**
     * Método para poner en los textFields la tupla que selccionemos
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
	private void ponerPersonaSeleccionada() {
        final OrdenDia orden = getTablaPersonasSeleccionada();
        posicionPersonaEnTabla = datos.indexOf(orden);

        if (orden != null) {
  
        	puntoATratar.num_punto= String.valueOf(orden.getNumeroPunto());
        	puntoATratar.tema=orden.getTema();
        	puntoATratar.proponente=orden.getProponente_nombre();
        	
            // Pongo los textFields con los datos correspondientes
        	lbl_punto.setText(""+orden.getNumeroPunto());
        	label_titulo.setText(orden.getTema());
            label_proponente.setText(orden.getProponente_nombre());
            //cargar la documentacion
            System.out.println("El id de la orden del dia es"+orden.getId());
            try {
				
				
				List<Pdf> lista_pdfs=servidor.consultarPdfsPunto(orden.getId());
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
            
            //socket para envio de los principales componentes al momento de dar click en el tableview
            try {
                String titulo = label_titulo.getText();
                String punto = lbl_punto.getText();
                String proponente = label_proponente.getText();
                String id_pdf = String.valueOf(orden.getId());
                //String json = "{" + " 'name' : '" + data.name + "', 'message' : '" + msg + "'" + "}";

                JSONObject js = new JSONObject();
                js.put("titulo", titulo);
                js.put("punto", punto);
                js.put("proponente", proponente);
                js.put("id_pdf",id_pdf);

                String json = js.toJSONString();


                System.out.println("Se envio:"+json);

                dos.writeUTF(json);

            } catch(IOException E) {
                E.printStackTrace();
            }
        }
    }
    
    @FXML
    void prueba(MouseEvent event) {
    	ponerPersonaSeleccionada();
    	

    }
   
    

}
