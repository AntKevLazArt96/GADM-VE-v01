package gad.manta.common;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Pdf implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 6970918765794755720L;
	private int id;
	private int id_orden_dia;
	private String nombre;
	private String ruta_pdf;
	private byte[] pdf;
	
	
	
	
	public byte[] getPdf() {
		return pdf;
	}
	public void setPdf(byte[] pdf) {
		this.pdf = pdf;
	}
	public Pdf(int id) {
		super();
		this.id = id;
	}
	
	public Pdf(int id, int id_orden_dia, String nombre, byte[] pdf) {
		super();
		this.id = id;
		this.id_orden_dia = id_orden_dia;
		this.nombre = nombre;
		this.pdf = pdf;
	}

	public Pdf(int id, int id_orden_dia, String ruta_pdf) {
		this.id = id;
		this.id_orden_dia = id_orden_dia;
		this.ruta_pdf = ruta_pdf;
	}


	public Pdf(int id, String nombre) {
		this.id = id;
		this.nombre = nombre;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getId_orden_dia() {
		return id_orden_dia;
	}

	public void setId_orden_dia(int id_orden_dia) {
		this.id_orden_dia = id_orden_dia;
	}

	public String getRuta_pdf() {
		return ruta_pdf;
	}

	public void setRuta_pdf(String ruta_pdf) {
		this.ruta_pdf = ruta_pdf;
	}
	
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public int guardarRegistro_pdfs(Connection connection) throws IOException {
		String sql = "INSERT INTO pdf_ve(id_ordendia,nombre_pdf,archivo_pdf) VALUES (?, ?, ?);";
		try {
			File pdf = new File(nombre);
			FileInputStream fis = new FileInputStream(pdf);
    		PreparedStatement ps = connection.prepareStatement(sql);
    		ps.setInt(1,id);
    		ps.setString(2, pdf.getName());
    		ps.setBinaryStream(3, fis, (int)pdf.length());
    		ps.executeUpdate();
    		ps.close();
    		fis.close();
    		
    		return 1;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}
		
	}
	
	
	}