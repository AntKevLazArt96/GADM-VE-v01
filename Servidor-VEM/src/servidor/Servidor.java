package servidor;

import java.rmi.RemoteException;

import gad.manta.common.IServidor;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Servidor implements IServidor {
	


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
		//conección a la base de datos  
		Connection db = DriverManager.getConnection("jdbc:postgresql:gad_voto","postgres","1234");
		Statement st = db.createStatement();
		
		//ejecucion y resultado de la consulta
		ResultSet resultado = st.executeQuery("select verificar_usuario('"+username+"','"+password+"');");
		resultado.next();
		resultado_validacion= resultado.getString(1);
		db.close();
		
		}catch(Exception e) {
			System.out.println("Error: " + e.getMessage());
			e.printStackTrace();
		}
		
		System.out.println("el usuario se ha logueado correctamente");
		
		return resultado_validacion;
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

	@Override
	public void iniciarSesion() throws RemoteException {
		System.out.println("La secretaria cambio de pantalla");
		
	}
	
	

	
	
}
