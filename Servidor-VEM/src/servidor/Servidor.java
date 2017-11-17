package servidor;

import java.rmi.RemoteException;

import gad.manta.common.IServidor;
import gad.manta.common.Mensaje;
import gad.manta.common.OrdenDia;
import gad.manta.common.Usuario;
import gad.manta.common.Voto;
import gad.manta.common.Sesion;
//import modelo.Sesion;

import java.sql.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class Servidor implements IServidor {
	public Servidor() throws RemoteException{
		super();
	}
	
	private Map<Integer, String> sesion_nombre =  new HashMap<Integer, String>();
    private Map<String, Integer> nombre_sesion =  new HashMap<String, Integer>();
    
    private Map<Integer, List<Mensaje>> buffer =  new HashMap<Integer, List<Mensaje>>();

    List<Usuario> listaUsuario = new ArrayList<>();
    List<Voto> listaVotantes = new ArrayList<>();
    List<Voto> listaVotoAprueba = new ArrayList<>();
    List<Voto> listaVotoRechaza = new ArrayList<>();
    List<Sesion> lista_sesion = new ArrayList<Sesion>();
    List<OrdenDia> lista_ordendia = new ArrayList<OrdenDia>();
    Calendar fecha = new GregorianCalendar();
    int annio = fecha.get(Calendar.YEAR);
    int mes = fecha.get(Calendar.MONTH)+1;
    int dia = fecha.get(Calendar.DAY_OF_MONTH);
    
	@Override
	public String saludar(String nombre) throws RemoteException {
		// TODO Auto-generated method stub
		System.out.println("Tu nombre es: "+ nombre);
		return "Hola "+ nombre +", bienvenido al Sistema";
	}

	@Override
	public String login(String username, String password) throws RemoteException {
		String resultado_validacion="";
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
		ResultSet resultado = st.executeQuery("select *from verificar_usuario('"+username+"','"+password+"');");
		resultado.next();
		resultado_validacion= resultado.getString(3);
		db.close();
		//Para el Quorum
		if(!username.equals("secretaria")) {
			listaUsuario.add(new Usuario(resultado_validacion,"PRESENTE",""));
		}
			
		
		}catch(Exception e) {
			System.out.println("Error: " + e.getMessage());
			e.printStackTrace();
		}
		
		System.out.println("el usuario se ha logueado correctamente");
		
		return resultado_validacion;
	}
	
	@Override
	public List<Usuario> consultaQuorum() throws RemoteException {
		return listaUsuario;
	}
	
	@Override
	public String addVoto(String usuario, String voto) throws RemoteException {
		// TODO Auto-generated method stub
		if(voto.contains("APROBAR")) {
			listaVotoAprueba.add(new Voto(usuario,voto));
		}
		if(voto.contains("RECHAZAR")) {
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
			ResultSet resultado = st.executeQuery("select nombre from usuario;");
			
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

	
	private static int sesion = new Random().nextInt();
    
    public static int getSesion(){
        return ++sesion;
    }

	@Override
	public int iniciarSesion(String nombre) throws RemoteException {
		System.out.println(nombre+" Esta intentando autenticarse");
        int sesionUsuario = getSesion();
        
        sesion_nombre.put(sesionUsuario, nombre);
        nombre_sesion.put(nombre, sesionUsuario);
        
        return sesionUsuario;
	}

	@Override
	public void enviar(String cuerpo, int sesionDe, int SesionA) throws RemoteException {
		if(!sesion_nombre.containsKey(sesionDe)){
            throw new RuntimeException("Sesion invalida");
        }
        if(!sesion_nombre.containsKey(SesionA)){
            throw new RuntimeException("Contacto no esta conectado");
        }
        
        List<Mensaje> mensajes = buffer.get(SesionA);
        if(mensajes == null){
            mensajes = new LinkedList<Mensaje>();
            buffer.put(SesionA, mensajes);
        }
        
        mensajes.add(new Mensaje(cuerpo,sesion_nombre.get(sesionDe)));
        System.out.println(sesion_nombre.get(sesionDe)+ " Envio un mensaje a "+ sesion_nombre.get(SesionA));
		
	}

	@Override
	public List<Sesion> consultarSesion() throws RemoteException {
		
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
			ResultSet resultado = st.executeQuery("select convocatoria,titulo from sesion where fecha_intervencion='"+annio+"-"+mes+"-"+dia+"';");
			
			resultado.next();
			lista_sesion.add(new Sesion(resultado.getString(1),resultado.getString(2)));
			db.close();
				
			
			}catch(Exception e) {
				System.out.println("Error: " + e.getMessage());
				e.printStackTrace();
			}
		
		return lista_sesion;	
		}

	@Override
	public List<OrdenDia> consultarOrden() throws RemoteException {

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
			ResultSet resultado = st.executeQuery("select numero_punto,tema_punto,us.nombre from sesion as s inner join orden_dia as od on s.id=od.id_sesion inner join usuario as us on us.id=od.proponente where s.fecha_intervencion='"+annio+"-"+mes+"-"+dia+"';");
			
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
	

	

	
	
	

	
	
}
