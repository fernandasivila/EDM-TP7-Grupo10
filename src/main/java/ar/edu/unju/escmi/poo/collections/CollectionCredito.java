package ar.edu.unju.escmi.poo.collections;

import java.util.ArrayList;
import java.util.List;

import ar.edu.unju.escmi.poo.dominio.Credito;
import ar.edu.unju.escmi.poo.dominio.TarjetaCredito;

public abstract class CollectionCredito {

	 public static List<Credito> creditos = new ArrayList<Credito>();

	 public static void agregarCredito(Credito credito) {
	        
	    	try {
	    		creditos.add(credito);
			} catch (Exception e) {
				System.out.println("\nNO SE PUEDE GUARDAR EL CREDITO");
			}
	    	
	    }
	 public static Credito buscarCredito(TarjetaCredito tarjetaCredito) {
			Credito creditoEncontrado=null;
			
			try {
				if (creditos != null) {
					for (Credito credito : creditos) {
						if (credito.getTarjetaCredito() == tarjetaCredito) {
							creditoEncontrado=credito;
						}
					}
				}
			} catch (Exception e) {
				return null;
			}
			
			return creditoEncontrado;
		}
}
