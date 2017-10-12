package gad.manta.servidor;

import java.rmi.RemoteException;

import gad.manta.common.IServidor;

public class Servidor implements IServidor {

	@Override
	public String saludar(String nombre) throws RemoteException {
		// TODO Auto-generated method stub
		System.out.println("Tu nombre es: "+ nombre);
		return "Hola "+ nombre +", bienvenido al Sistema";
	}

}
