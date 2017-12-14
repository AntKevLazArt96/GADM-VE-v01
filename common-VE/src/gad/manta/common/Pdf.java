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
import java.util.ArrayList;
import java.util.List;

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

	
	public Pdf(int id, int id_orden_dia, String nombre, String ruta_pdf) {
		super();
		this.id = id;
		this.id_orden_dia = id_orden_dia;
		this.nombre = nombre;
		this.ruta_pdf = ruta_pdf;
	}
	
	public Pdf(String nombre, String ruta_pdf) {
		super();
		this.nombre = nombre;
		this.ruta_pdf = ruta_pdf;
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
	public static List<Pdf> consultarPDFS_Modificacion(int id_punto) {
		List<Pdf> lista_PDF = new ArrayList<Pdf>();
		try {
			// para verificar si esta instalado el drive de postgressql

			try {
				Class.forName("org.postgresql.Driver");

			} catch (ClassNotFoundException cnfe) {
				System.out.println("Drive no encontrado");
				cnfe.printStackTrace();

			}
			//conecci�n a la base de datos  
			Connection db = DriverManager.getConnection("jdbc:postgresql://"+data_configuracion.ipBaseDatos+"/"+data_configuracion.nombre_bd+"",""+data_configuracion.usu_db+"",""+data_configuracion.conta_usu+"");
			Statement st = db.createStatement();
			// ejecucion y resultado de la consulta
			ResultSet resultado = st.executeQuery("select id_pdf,nombre_pdf from pdf_ve where id_ordendia=" + id_punto + ";");

			while (resultado.next()) {
				lista_PDF.add(new Pdf(resultado.getInt(1), resultado.getString(2)));
			}
			db.close();

		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
			e.printStackTrace();
		}

		return lista_PDF;
	}
	
	public static Pdf pdf_punto(int id) {
		Connection db;
		Pdf pdf = null;
		try {
			db = DriverManager.getConnection("jdbc:postgresql://"+data_configuracion.ipBaseDatos+"/"+data_configuracion.nombre_bd+"",""+data_configuracion.usu_db+"",""+data_configuracion.conta_usu+"");
			Statement st = db.createStatement();
			// ejecucion y resultado de la consulta
			ResultSet resultado = st.executeQuery("select * from pdf_ve where id_pdf=" + id + ";");
			if(resultado.next()) {
			 pdf= new Pdf(resultado.getInt(1), resultado.getInt(2), resultado.getString(3), resultado.getBytes(4));
			}	
			db.close();
			return pdf;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}



	}
	
	
	
	}