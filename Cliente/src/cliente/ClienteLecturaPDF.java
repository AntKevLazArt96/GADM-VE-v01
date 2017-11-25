package cliente;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.geom.Rectangle2D;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ResourceBundle;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import com.jfoenix.controls.JFXButton;
import com.sun.pdfview.PDFFile;
import com.sun.pdfview.PDFPage;
import com.sun.pdfview.PagePanel;
import javafx.embed.swing.SwingNode;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;

public class ClienteLecturaPDF extends JFrame implements  Initializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6506395191970665089L;
	private PDFFile pdffile;
	private int pagina;
	private int paginas;
	static java.awt.Image image;
	
	@FXML
    private JFXButton btn_anterior;
    @FXML
    private JFXButton btn_siguiente;
    @FXML 
    private StackPane  leftAnchorPane;
    
    SwingNode swingNode = new SwingNode();
	PagePanel panel = new PagePanel();
	
	
	 @FXML
	 private void siguiente(){
	    	
	    		pagina += 1;
				if (pagina > paginas || pagina < 1) {
					pagina = 1;
				}
				viewPage();
	    }
	 
	 @FXML
	 private  void atras(){
		 	pagina -= 1;
			if (pagina > paginas || pagina < 1) {
				pagina = 1;
			}
			viewPage();
	 }
	 
	  
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		
		
		try
		{
			
			
			File file = new File("C:\\Users\\chris\\Desktop\\471App.pdf"); 
			// Ubicación del archivo pdf
			RandomAccessFile raf = new RandomAccessFile(file, "r");
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
