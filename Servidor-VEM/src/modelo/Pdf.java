package modelo;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Pdf {
	private int id;
	private int id_orden_dia;
	private String nombre;
	private String ruta_pdf;
	
	
	public Pdf(int id, int id_orden_dia, String ruta_pdf) {
		this.id = id;
		this.id_orden_dia = id_orden_dia;
		this.ruta_pdf = ruta_pdf;
	}
	public Pdf(int id_orden_dia, String ruta_pdf) {
		this.id_orden_dia = id_orden_dia;
		this.ruta_pdf = ruta_pdf;
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

	public int guardarRegistro_pdf(Connection connection) throws IOException {
		String sql = "INSERT INTO pdf(id_orden_dia,nombre,pdf) VALUES (?, ?, ?);";
		try {
			File pdf = new File(ruta_pdf);
			FileInputStream fis = new FileInputStream(pdf);
    		PreparedStatement ps = connection.prepareStatement(sql);
    		ps.setInt(1,id_orden_dia);
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
