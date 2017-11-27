package servidor;



import java.rmi.RemoteException;

import gad.manta.common.ActaPdf;
import gad.manta.common.Documentacion;
import gad.manta.common.IServidor;
import gad.manta.common.OrdenDia;
import gad.manta.common.Pdf;
import gad.manta.common.Usuario;
import gad.manta.common.Voto;
import gad.manta.common.Sesion;
//import modelo.Sesion;

import java.sql.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;


public class Servidor implements IServidor {
	public Servidor() throws RemoteException{
		super();
	}
	  
  
    List<Usuario> listaUsuario = new ArrayList<>();
    //votacion para aprobar el orden del dia propuesto
    List<Voto> listaVotantes = new ArrayList<>();
    List<Voto> listaVotoAprueba = new ArrayList<>();
    List<Voto> listaVotoRechaza = new ArrayList<>();
    //votacion para aprobar cada punto de ser necesario
    List<Voto> listaVotantesPunto = new ArrayList<>();
    List<Voto> listaVotoAFavor = new ArrayList<>();
    List<Voto> listaVotoEnContra = new ArrayList<>();
    List<Voto> listaVotoSalvado = new ArrayList<>();
    List<Voto> listaVotoBlanco = new ArrayList<>();
    
    
    
    
    
    Calendar fecha = new GregorianCalendar();
    int annio = fecha.get(Calendar.YEAR);
    int mes = fecha.get(Calendar.MONTH)+1;
    int dia = fecha.get(Calendar.DAY_OF_MONTH);
    
    
	

	@Override
	public String login(String username, String password) throws RemoteException {
		
		try {
		//para verificar si esta instalado el drive de postgressql
		
		try {
			Class.forName("org.postgresql.Driver");
			
		}catch(ClassNotFoundException cnfe){
			System.out.println("Drive no encontrado");
			cnfe.printStackTrace();
			
		}
		//conecci�n a la base de datos  
		Connection db = DriverManager.getConnection("jdbc:postgresql:gad_voto","postgres","1234");
		Statement st = db.createStatement();
		int id;
		String usuario="";
		byte[] img=null;
		//ejecucion y resultado de la consulta
		ResultSet resultado = st.executeQuery("select *from verificar_usuario('"+username+"','"+password+"');");
		resultado.next();
		id= resultado.getInt(1);
		usuario= resultado.getString(2);
		img= resultado.getBytes(3);
		db.close();
		
		
		
		//Para el Quorum
		if(!username.equals("secretaria")) {
			listaUsuario.add(new Usuario(id,usuario,"PRESENTE", img));	
		}
		System.out.println("el usuario se ha logueado correctamente");
			
		return usuario;
		}catch(Exception e) {
			System.out.println("Error: " + e.getMessage());
			e.printStackTrace();
			return null;
		}
		
	}
	
	@Override
	public Usuario usuario(String name)  {
		Connection db;
		try {
			db = DriverManager.getConnection("jdbc:postgresql:gad_voto","postgres","1234");
			Statement st = db.createStatement();
			//ejecucion y resultado de la consulta
			ResultSet resultado = st.executeQuery("select *from consulta_usuario_name('"+name+"');");
			resultado.next();
			Usuario user = new Usuario(resultado.getInt(1),resultado.getString(2),resultado.getBytes(3));
			db.close();
			return user;
		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		
		
	}
	@Override
	public ActaPdf acta_sesion(int id)  {
		Connection db;
		try {
			db = DriverManager.getConnection("jdbc:postgresql:gad_voto","postgres","1234");
			Statement st = db.createStatement();
			//ejecucion y resultado de la consulta
			ResultSet resultado = st.executeQuery("select * from acta_ve where id_pdf="+id+";");
			resultado.next();
			ActaPdf user = new ActaPdf(resultado.getInt(1),resultado.getString(2),resultado.getBytes(3));
			db.close();
			return user;
		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		
		
	}
	
	@Override
	public List<Usuario> listaUsuarios() throws RemoteException {
		List<Usuario> listaUsuario = new ArrayList<>();
		Connection db;
		try {
			db = DriverManager.getConnection("jdbc:postgresql:gad_voto","postgres","1234");
			Statement st = db.createStatement();
			//ejecucion y resultado de la consulta
			ResultSet resultado = st.executeQuery("select *from consulta_usuarios();");
			while(resultado.next()) {
				listaUsuario.add(new Usuario(resultado.getInt(1),resultado.getString(2),resultado.getBytes(3)));
			}
			db.close();
			return listaUsuario;
		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		
	}
	
	@Override
	public List<Usuario> asistenciaUsuarios(int id_quorum) throws RemoteException {
		List<Usuario> listaUsuario = new ArrayList<>();
		Connection db;
		try {
			db = DriverManager.getConnection("jdbc:postgresql:gad_voto","postgres","1234");
			Statement st = db.createStatement();
			//ejecucion y resultado de la consulta
			ResultSet resultado = st.executeQuery("select *from asistencia_concejales("+id_quorum+");");
			while(resultado.next()) {
				listaUsuario.add(new Usuario(resultado.getInt(1),resultado.getString(2),resultado.getString(3),resultado.getBytes(4)));
			}
			db.close();
			return listaUsuario;
		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	@Override
	public List<Usuario> consultaQuorum() throws RemoteException {
		return listaUsuario;
	}
	
	@Override
	public String addVoto(String usuario, String voto,byte[] img) throws RemoteException {
		// TODO Auto-generated method stub
		if(voto.contains("APROBADO")) {
			listaVotoAprueba.add(new Voto(usuario,voto,img));
		}
		if(voto.contains("RECHAZADO")) {
			listaVotoRechaza.add(new Voto(usuario,voto,img));
		}
		listaVotantes.add(new Voto(usuario,voto,img));
		
		
		return usuario;
	}
	
	@Override
	public List<Voto> votosAprobados() throws RemoteException {
		// TODO Auto-generated method stub
		return listaVotoAprueba;
	}
	
	@Override
	public List<Voto> votosRechazados() throws RemoteException {
		// TODO Auto-generated method stub
		return listaVotoRechaza;
	}
	
	@Override
	public List<Voto> votantes() throws RemoteException {
		// TODO Auto-generated method stub
		return listaVotantes;
	}
	
	
	
	
	

	@Override
	public int agregarSesion(String fechaRegistro, String fechaIntervencion, String horaIntervencion,
			String convocatoria, String titulo) throws RemoteException {
		int idsesion=0;
		Connection db;
		try {
			db = DriverManager.getConnection("jdbc:postgresql:gad_voto","postgres","1234");
			Statement st = db.createStatement();
			
			//ejecucion y resultado de la consulta
			ResultSet resultado = st.executeQuery("select *from ingresar_sesion('"+fechaRegistro+"','"+fechaIntervencion+"','"+horaIntervencion+"','"+convocatoria+"','"+titulo+"');");
			resultado.next();
			idsesion= resultado.getInt(1);
			db.close();
		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return idsesion;
		
	}

	@Override
	public List<String> consultaUsuario() throws RemoteException {
		List<String> listausuario = new ArrayList<>();
		Connection db;
		try {
			db = DriverManager.getConnection("jdbc:postgresql:gad_voto","postgres","1234");
			Statement st = db.createStatement();
			//ejecucion y resultado de la consulta
			ResultSet resultado = st.executeQuery("select name_user from User_VE;");
			
			while(resultado.next()) {
				listausuario.add(resultado.getString(1));
			}
			db.close();
		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return listausuario;
		// TODO Auto-generated method stub
		
	}

	

	@Override
	public Sesion consultarSesion() throws RemoteException {
		
		try {
			//para verificar si esta instalado el drive de postgressql
			
			try {
				Class.forName("org.postgresql.Driver");
				
			}catch(ClassNotFoundException cnfe){
				System.out.println("Drive no encontrado");
				cnfe.printStackTrace();
				
			}
			//conecci�n a la base de datos  
			Connection db = DriverManager.getConnection("jdbc:postgresql:gad_voto","postgres","1234");
			Statement st = db.createStatement();
			
			//ejecucion y resultado de la consulta
			ResultSet resultado = st.executeQuery("select convocatoria_sesion,description_sesion ,id_pdf from Sesion_VE where intervention_sesion='"+annio+"-"+mes+"-"+dia+"';");
			
			resultado.next();
			Sesion sesion = new Sesion(resultado.getString(1),resultado.getString(2),resultado.getInt(3));
			db.close();
			
			return sesion;	
			
			}catch(Exception e) {
				System.out.println("Error: " + e.getMessage());
				e.printStackTrace();
				return null;
			}
		}

	public List<Sesion>  consultarSesion_Modificacion(String convocatoria) throws RemoteException {
		List<Sesion> listaSesion = new ArrayList<>();
		try {
			//para verificar si esta instalado el drive de postgressql
			
			try {
				Class.forName("org.postgresql.Driver");
				
			}catch(ClassNotFoundException cnfe){
				System.out.println("Drive no encontrado");
				cnfe.printStackTrace();
				
			}
			//conecci�n a la base de datos  
			Connection db = DriverManager.getConnection("jdbc:postgresql:gad_voto","postgres","1234");
			Statement st = db.createStatement();
			
			//ejecucion y resultado de la consulta
			ResultSet resultado = st.executeQuery("select * from Sesion_VE where convocatoria_sesion='"+convocatoria+"';");
			
			while(resultado.next()) {
				listaSesion.add(new Sesion(resultado.getString(1) , resultado.getString(2) , resultado.getString(3) , resultado.getDate(4), resultado.getDate(5), resultado.getString(6), resultado.getInt(7)));			
				System.out.println(resultado.getString(1));
			}
			db.close();
			
			}catch(Exception e) {
				System.out.println("Error: " + e.getMessage());
				e.printStackTrace();
				return null;
			}
		return listaSesion;
		}


	
	@Override
	public List<OrdenDia> consultarOrden() throws RemoteException {
		 List<OrdenDia> lista_ordendia = new ArrayList<OrdenDia>();
		try {
			//para verificar si esta instalado el drive de postgressql
			
			try {
				Class.forName("org.postgresql.Driver");
				
			}catch(ClassNotFoundException cnfe){
				System.out.println("Drive no encontrado");
				cnfe.printStackTrace();
				
			}
			//conecci�n a la base de datos  
			Connection db = DriverManager.getConnection("jdbc:postgresql:gad_voto","postgres","1234");
			Statement st = db.createStatement();
			
			//ejecucion y resultado de la consulta
			ResultSet resultado = st.executeQuery("select numpunto_ordendia,descrip_ordendia,us.name_user from Sesion_VE as s inner join OrdenDia_VE as od on s.convocatoria_sesion=od.convocatoria_sesion inner join User_VE as us on us.id_user=od.id_user where s.intervention_sesion='"+annio+"-"+mes+"-"+dia+"';");
			
			while(resultado.next()) {
				lista_ordendia.add(new OrdenDia(resultado.getInt(1),resultado.getString(2),resultado.getString(3)));	
			}
			db.close();
				
			
			}catch(Exception e) {
				System.out.println("Error: " + e.getMessage());
				e.printStackTrace();
			}
		
		return lista_ordendia;	
		}
	
	@Override
	public List<OrdenDia> consultarOrden_Modificacion(String convocatoria) throws RemoteException {
		 List<OrdenDia> lista_ordendia = new ArrayList<OrdenDia>();
		try {
			//para verificar si esta instalado el drive de postgressql
			
			try {
				Class.forName("org.postgresql.Driver");
				
			}catch(ClassNotFoundException cnfe){
				System.out.println("Drive no encontrado");
				cnfe.printStackTrace();
				
			}
			//conecci�n a la base de datos  
			Connection db = DriverManager.getConnection("jdbc:postgresql:gad_voto","postgres","1234");
			Statement st = db.createStatement();
			
			//ejecucion y resultado de la consulta
			ResultSet resultado = st.executeQuery("select id_ordendia, numpunto_ordendia,descrip_ordendia \r\n" + 
					"	from Sesion_VE as s inner join OrdenDia_VE as od on s.convocatoria_sesion=od.convocatoria_sesion\r\n" + 
					"	where s.convocatoria_sesion='"+convocatoria+"';");
			
			while(resultado.next()) {
				lista_ordendia.add(new OrdenDia(resultado.getInt(1),resultado.getInt(2),resultado.getString(3)));	
			}
			db.close();
				
			
			}catch(Exception e) {
				System.out.println("Error: " + e.getMessage());
				e.printStackTrace();
			}
		
		return lista_ordendia;	
		}
	@Override
	public List<OrdenDia> consultarPunto_Modificacion(int id_punto) throws RemoteException {
		 List<OrdenDia> lista_ordendia = new ArrayList<OrdenDia>();
		try {
			//para verificar si esta instalado el drive de postgressql
			
			try {
				Class.forName("org.postgresql.Driver");
				
			}catch(ClassNotFoundException cnfe){
				System.out.println("Drive no encontrado");
				cnfe.printStackTrace();
				
			}
			//conecci�n a la base de datos  
			Connection db = DriverManager.getConnection("jdbc:postgresql:gad_voto","postgres","1234");
			Statement st = db.createStatement();
			
			//ejecucion y resultado de la consulta
			ResultSet resultado = st.executeQuery("select * from OrdenDia_VE where id_ordendia="+id_punto+";");
			
			while(resultado.next()) {
				lista_ordendia.add(new OrdenDia(resultado.getInt(1),resultado.getString(2),resultado.getInt(3),resultado.getString(4),resultado.getInt(5)));	
			}
			db.close();
				
			
			}catch(Exception e) {
				System.out.println("Error: " + e.getMessage());
				e.printStackTrace();
			}
		
		return lista_ordendia;	
		}
	@Override
	public List<Pdf> consultarPDFS_Modificacion(int id_punto) throws RemoteException {
		 List<Pdf> lista_PDF = new ArrayList<Pdf>();
		try {
			//para verificar si esta instalado el drive de postgressql
			
			try {
				Class.forName("org.postgresql.Driver");
				
			}catch(ClassNotFoundException cnfe){
				System.out.println("Drive no encontrado");
				cnfe.printStackTrace();
				
			}
			//conecci�n a la base de datos  
			Connection db = DriverManager.getConnection("jdbc:postgresql:gad_voto","postgres","1234");
			Statement st = db.createStatement();
			
			//ejecucion y resultado de la consulta
			ResultSet resultado = st.executeQuery("select id_pdf,nombre_pdf from pdf_ve where id_ordendia="+id_punto+";");
			
			while(resultado.next()) {
				lista_PDF.add(new Pdf(resultado.getInt(1),resultado.getString(2)));	
			}
			db.close();
				
			
			}catch(Exception e) {
				System.out.println("Error: " + e.getMessage());
				e.printStackTrace();
			}
		
		return lista_PDF;	
		}

	@Override
	public List<Documentacion> mostrarDocumentacion() throws RemoteException {
		  List<Documentacion> lista_documentacion = new ArrayList<Documentacion>();
		  
		try {
			//para verificar si esta instalado el drive de postgressql
			
			try {
				Class.forName("org.postgresql.Driver");
				
			}catch(ClassNotFoundException cnfe){
				System.out.println("Drive no encontrado");
				cnfe.printStackTrace();
				
			}
			//conecci�n a la base de datos  
			Connection db = DriverManager.getConnection("jdbc:postgresql:gad_voto","postgres","1234");
			Statement st = db.createStatement();
			
			//ejecucion y resultado de la consulta
			ResultSet resultado = st.executeQuery("select numpunto_ordendia, nombre_pdf,nombre_pdf from Sesion_VE as s	inner join OrdenDia_VE as od on s.convocatoria_sesion=od.convocatoria_sesion inner join Pdf_VE as p on p.id_ordendia=od.id_ordendia where s.intervention_sesion='"+annio+"-"+mes+"-"+dia+"';");
			while (resultado.next()) {
				
			    lista_documentacion.add(new Documentacion(resultado.getInt(1),resultado.getString(2),resultado.getBytes(3)));
			}
			db.close();
			}catch(Exception e) {
				System.out.println("Error: " + e.getMessage());
				e.printStackTrace();
			}
		return lista_documentacion;	
		}
	
	
	//Votaciones del orden del dia
	@Override
	public String addVotoPunto(String usuario, String voto, byte[] img) throws RemoteException {
		// TODO Auto-generated method stub
				if(voto.contains("FAVOR")) {
					listaVotoAFavor.add(new Voto(usuario,voto,img));
				}
				if(voto.contains("CONTRA")) {
					listaVotoEnContra.add(new Voto(usuario,voto,img));
				}
				if(voto.contains("SALVO")) {
					listaVotoSalvado.add(new Voto(usuario,voto,img));
				}
				if(voto.contains("BLANCO")) {
					listaVotoBlanco.add(new Voto(usuario,voto,img));
				}
				listaVotantesPunto.add(new Voto(usuario,voto,img));
			
				return usuario;
	}

	@Override
	public List<Voto> votantesPunto() throws RemoteException {
		// TODO Auto-generated method stub
		return listaVotantesPunto;
	}

	@Override
	public List<Voto> votosAFavor() throws RemoteException {
		// TODO Auto-generated method stub
		return listaVotoAFavor;
	}

	@Override
	public List<Voto> votosEnContra() throws RemoteException {
		// TODO Auto-generated method stub
		return listaVotoEnContra;
	}

	@Override
	public List<Voto> votosSalvados() throws RemoteException {
		// TODO Auto-generated method stub
		return listaVotoSalvado;
	}

	@Override
	public List<Voto> votosBlanco() throws RemoteException {
		// TODO Auto-generated method stub
		return listaVotoBlanco;
	}



	

	

	

	
	
	

	
	
}
