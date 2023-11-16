package ar.edu.unju.escmi.poo.tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Assertions;

import ar.edu.unju.escmi.poo.collections.CollectionStock;
import ar.edu.unju.escmi.poo.dominio.Producto;

import org.junit.jupiter.api.Test;

import ar.edu.unju.escmi.poo.dominio.Stock;

class StockTest {

	@Test
	void decrementoDeStock() {
		Producto producto = new Producto();
		int cantidadProducto = 5;
		Stock stockAux = new Stock(cantidadProducto, producto);
		int reduccion = 2;
		CollectionStock.agregarStock(stockAux);
		CollectionStock.reducirStock(stockAux, reduccion);
		
		int cantidadReducida = CollectionStock.buscarStock(producto).getCantidad() - 2;
		int expected = cantidadProducto - reduccion;
		
		Assertions.assertEquals(expected, cantidadReducida, "El decremento del stock del producto deberia coincidir con la cantidad indicada");
	}

}
