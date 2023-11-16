package ar.edu.unju.escmi.poo.tests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.util.List;
import java.util.ArrayList;

import ar.edu.unju.escmi.poo.dominio.Detalle;
import ar.edu.unju.escmi.poo.dominio.Factura;
import ar.edu.unju.escmi.poo.dominio.Credito;

class CuotaTest {
	
	public static final int MONTO1 = 500000;
	public static final int MONTO2 = 500000;
	public static final int MONTO3 = 1000;
	
	public Factura facturaAProbar() {
		Factura factura = new Factura();
		factura.setDetalles(crearListaDeDetalles(MONTO1,MONTO2,MONTO3));
		
		return factura;
	}
	
	@Test
	void listaDeCuotasNotNull() {
		Credito credito = new Credito();
		credito.setFactura(facturaAProbar());
		credito.generarCuotas();
		
		Assertions.assertNotNull(credito.getCuotas(), "La lista de cuotas no deberia ser null");
	}
	
	@Test
	void listaDeCuotasTiene30(){
		
		Credito credito = new Credito();
		credito.setFactura(facturaAProbar());
		credito.generarCuotas();
		int expected = 30;
		
		Assertions.assertEquals(expected, credito.getCuotas().size(), "Se espera que la lista contenga 30 cuotas");
	}
	
	@Test
	void listaDeCuotasConLimite() {
		
		Credito credito = new Credito();
		credito.setFactura(facturaAProbar());
		credito.generarCuotas();
		int expected = 30;
		
		Assertions.assertTrue(credito.getCuotas().size() <= expected, "La lista de cuotas no deberia superar la cantidad de 30");
	}
	
	public static List<Detalle> crearListaDeDetalles(int monto1, int monto2, int monto3){
		List<Detalle> aux = new ArrayList<Detalle>();
		int montos[] = {monto1, monto2, monto3};
		
		for(int i=0; i<3; i++) {
			Detalle detalle = new Detalle();
			detalle.setImporte(montos[i]);
			aux.add(detalle);
		}
		
		return aux;
	}

}
