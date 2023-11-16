package ar.edu.unju.escmi.poo.tests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.util.List;
import java.util.ArrayList;

import ar.edu.unju.escmi.poo.dominio.Cuota;
import ar.edu.unju.escmi.poo.dominio.Credito;

class CuotaTest {
	
	@Test
	
	void listaDeCuotasNotNull() {
		//Credito credito = new Credito();
		List<Cuota> cuotas = null;
		//cuotas = credito.getCuotas();
		//List<Cuota> cuotas = new ArrayList<Cuota>();
		
		Assertions.assertNotNull(cuotas, "La lista de cuotas no deberia ser null");
	}
	
	@Test
	void listaDeCuotasTiene30(){
		
		List<Cuota> cuotas = List.of(new Cuota(), new Cuota(), new Cuota());
		int cantidadCuotas = cuotas.size();
		int expected = 30;
		
		Assertions.assertEquals(expected, cantidadCuotas, "Se espera que la lista contenga 30 cuotas");
	}
	
	@Test
	void listaDeCuotasConLimite() {
		
		List<Cuota> cuotas = crearListaDeCuotas(30);
		int cantidadCuotas = cuotas.size();
		int expected = 30;
		
		Assertions.assertTrue(cantidadCuotas <= expected, "La lista de cuotas no deberia superar la cantidad de 30");
	}
	
	public static List<Cuota> crearListaDeCuotas(int cantidad){
		List<Cuota> aux = new ArrayList<Cuota>();
		for(int i = 0; i<cantidad; i++) {
			Cuota cuota = new Cuota();
			aux.add(cuota);
		}
		
		return aux;
	}
	

}
