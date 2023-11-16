package ar.edu.unju.escmi.poo.tests;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;

import ar.edu.unju.escmi.poo.dominio.Detalle;
import ar.edu.unju.escmi.poo.dominio.Factura;
import ar.edu.unju.escmi.poo.dominio.TarjetaCredito;

import org.junit.jupiter.api.Test;

class CreditoTest {
	
	public static final int MONTO1 = 500000;
	public static final int MONTO2 = 500000;
	public static final int MONTO3 = 1000;
	
	
	public Factura facturaAProbar() {
		Factura factura = new Factura();
		factura.setDetalles(crearListaDeDetalles(MONTO1,MONTO2,MONTO3));
		
		return factura;
	}
	
	@Test
	void montoDeCreditoValido() {
		
		int costoTotal = (int) facturaAProbar().calcularTotal();
		int montoCrediticioPermitido = 1000000;
		
		Assertions.assertTrue(costoTotal <= montoCrediticioPermitido, "El credito no deberia superar un monto de 1000000");
	}
	
	@Test
	void validarMontoTotal() {
		
		int totalFactura = (int) facturaAProbar().calcularTotal();
		int expected = MONTO1 + MONTO2 + MONTO3;
		
		Assertions.assertEquals(expected, totalFactura, "La suma de todos los importes de detalles deberia ser igual al total de la factura");
		
	}
	
	@Test
	void montoValidoParaCreditoYTarjeta() {
		TarjetaCredito tarjeta = new TarjetaCredito();
		tarjeta.setLimiteCompra(850000);
		int montoCrediticioPermitido = 1000000;
		int facturaTotal = (int) facturaAProbar().calcularTotal();
		
		Assertions.assertTrue(facturaTotal <= tarjeta.getLimiteCompra() && facturaTotal <= montoCrediticioPermitido , "El monto Total de la Factura no deberia sobrepasar el limite de la tarjeta ni el valor permitido de 1000000");
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
