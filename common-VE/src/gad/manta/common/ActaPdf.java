package gad.manta.common;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ActaPdf {
	private int id;
	private String nombre;
	private String ruta_pdf;
	
	public ActaPdf() {
		
	}
	
	public ActaPdf(int id, String ruta_pdf) {
		this.id = id;
		this.ruta_pdf = ruta_pdf;
	}
	public ActaPdf( String nombre,String ruta_pdf) {
		this.nombre= nombre;
		this.ruta_pdf = ruta_pdf;
		
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}


	public String getRuta_pdf() {
		return ruta_pdf;
	}

	public void setRuta_pdf(String ruta_pdf) {
		this.ruta_pdf = ruta_pdf;
	}

	public int guardarRegistro_pdf(Connection connection) throws IOException {
		String sql = "select * from agregar_acta(?, ?);";
		try {
			File pdf = new File(ruta_pdf);
			FileInputStream fis = new FileInputStream(pdf);
    		PreparedStatement ps = connection.prepareStatement(sql);
    		//ps.setString(1,getNombre());
    		ps.setString(1, pdf.getName());
    		ps.setBinaryStream(2, fis, (int)pdf.length());
    		ps.execute();
    		fis.close();
    		
    		Statement statement = connection.createStatement();
			ResultSet resultado = statement.executeQuery("select id_pdf from acta_ve where nombre_pdf='"+pdf.getName()+"'");
			int id = 0;
			while(resultado.next()) {
				id=resultado.getInt(1);
			}
			
			return id;
    		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}
		
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	}
