package servidor;



import java.rmi.RemoteException;

import gad.manta.common.Documentacion;
import gad.manta.common.IServidor;
import gad.manta.common.OrdenDia;
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
    List<Voto> listaVotantes = new ArrayList<>();
    List<Voto> listaVotoAprueba = new ArrayList<>();
    List<Voto> listaVotoRechaza = new ArrayList<>();
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
	public String addVoto(String usuario, String voto) throws RemoteException {
		// TODO Auto-generated method stub
		if(voto.contains("APROBADO")) {
			listaVotoAprueba.add(new Voto(usuario,voto));
		}
		if(voto.contains("RECHAZADO")) {
			listaVotoRechaza.add(new Voto(usuario,voto));
		}
		listaVotantes.add(new Voto(usuario,voto));
		
		
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
			ResultSet resultado = st.executeQuery("select convocatoria_sesion,description_sesion from Sesion_VE where intervention_sesion='"+annio+"-"+mes+"-"+dia+"';");
			
			resultado.next();
			Sesion sesion = new Sesion(resultado.getString(1),resultado.getString(2));
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
				listaSesion.add(new Sesion(resultado.getString(1) , resultado.getString(2) , resultado.getDate(3), resultado.getDate(4), resultado.getString(5), resultado.getInt(6)));			
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



	

	

	

	
	
	

	
	
}
