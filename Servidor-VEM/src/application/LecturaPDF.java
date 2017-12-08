package application;
import java.awt.Toolkit;
import java.awt.geom.Rectangle2D;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ResourceBundle;
import java.util.UUID;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import com.sun.pdfview.PDFFile;
import com.sun.pdfview.PDFPage;
import com.sun.pdfview.PagePanel;

import clases.data;
import gad.manta.common.ActaPdf;
import gad.manta.common.Conexion;
import gad.manta.common.IServidor;
import gad.manta.common.Pdf;
import gad.manta.common.Usuario;
import gad.manta.common.data_configuracion;
import javafx.embed.swing.SwingNode;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

public class LecturaPDF extends JFrame implements  Initializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 
	 */
	private PDFFile pdffile;
	private int pagina;
	private int paginas;
	static java.awt.Image image;
	
	private Conexion conexion;
	private static IServidor servidor;
	@FXML
    private Circle cirlogin;
	@FXML
    private Label lbl_nombre;
	@FXML
    private JFXButton btn_voz;
	
	@FXML
    private JFXButton btn_anterior;
    @FXML
    private JFXButton btn_siguiente;
    @FXML 
    private StackPane  leftAnchorPane;
    @FXML
    private JFXButton regresar;
    
    @FXML
    private JFXButton guardarNotas;
    
    @FXML
    private JFXTextArea nota_pdf;
    

    
    SwingNode swingNode = new SwingNode();
	PagePanel panel = new PagePanel();
	Usuario user;
	
	 @FXML
	 private void siguiente(){
	    	
	    		pagina += 1;
				if (pagina > paginas || pagina < 1) {
					pagina = 1;
				}
				viewPage();
	    }
	 
	 @FXML
	 private void regresar(){
		 Stage stage = (Stage) regresar.getScene().getWindow();
		    // do what you have to do
		    stage.close();
	    }
	 
	 @FXML
	 private  void atras(){
		 	pagina -= 1;
			if (pagina > paginas || pagina < 1) {
				pagina = 1;
			}
			viewPage();
	 }
	 
	 public Image convertirImg(byte[] bytes) throws IOException {
			ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
			
			Image img = new Image(bis);
			return img;
		}
	 
	 public String convertirPdf(byte[] bytes) throws IOException {
			String tmpDir=System.getProperty("java.io.tmpdir")+"\\tmp\\";
			String tmpFileName= UUID.randomUUID().toString();
			if(!new File(tmpDir).exists()) {
				if(!new File(tmpDir).mkdirs()) {
					System.out.print("Imposibe crear directorio temporal");
					return null;
				}
				
			}
			OutputStream out = new FileOutputStream(tmpDir+tmpFileName+".pdf");
			out.write(bytes);
			out.close();
			File file= new File(tmpDir+tmpFileName+".pdf");
			file.deleteOnExit();
			
			return file.getPath();
		}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		try {
			servidor = (IServidor)Naming.lookup("rmi://"+data_configuracion.ip_rmi+"/VotoE");
		} catch (MalformedURLException | RemoteException | NotBoundException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
	
	lbl_nombre.setText(data.name);
	
	File f = new File("C:\\librerias\\concejal1.png");
	Image im = new Image(f.toURI().toString());
	cirlogin.setFill(new ImagePattern(im));
	cirlogin.setStroke(Color.SEAGREEN);
	cirlogin.setEffect(new DropShadow(+25d, 0d, +2d, Color.DARKGREEN));
	
		try
		{
		
			File  n = null;
			// Ubicación del archivo pdf
			if(data.tipo_lectura==2) {
				
				Pdf pdf = servidor.pdf_punto(data.id_pdf);
				n = new File(convertirPdf(pdf.getPdf()));
				
			}else if(data.tipo_lectura==1) {
				ActaPdf acta_pdf= servidor.acta_sesion(data.id_acta);
				n = new File(convertirPdf(acta_pdf.getPdf()));
			}else if(data.tipo_lectura==3) {
				n = data.archivo_pff;
			}
			
			RandomAccessFile raf = new RandomAccessFile(n, "r");
			FileChannel channel = raf.getChannel();
			ByteBuffer buf = channel.map(FileChannel.MapMode.READ_ONLY,  0, channel.size());
			pdffile = new PDFFile(buf);
			paginas = pdffile.getNumPages();
			pagina = 1;		
			viewPage();
			leftAnchorPane.getChildren().add(swingNode);
			
			raf.close();
			
		
		}catch (Exception e) {
			e.printStackTrace();
		}	
			
	}
	
	
	private void viewPage(){
        PDFPage page = pdffile.getPage(pagina);
        Rectangle2D r2d = page.getBBox ();
        double width = r2d.getWidth ();
        double height = r2d.getHeight ();
        width /= 75.0;
        height /= 75.0;
        int res = Toolkit.getDefaultToolkit ().getScreenResolution ();
        width *= res;
        height *= res;
        image = page.getImage ((int) width, (int) height, r2d, null, true, true);
        JLabel label = new JLabel (new ImageIcon (image));
        label.setVerticalAlignment (JLabel.TOP);
        setContentPane (new JScrollPane (label));

		swingNode.setContent(new JScrollPane (label));	
        pack ();
        
		

	}
	

}
