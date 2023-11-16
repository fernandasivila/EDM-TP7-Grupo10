package ar.edu.unju.escmi.poo.principal;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import ar.edu.unju.escmi.poo.collections.CollectionCliente;
import ar.edu.unju.escmi.poo.collections.CollectionCredito;
import ar.edu.unju.escmi.poo.collections.CollectionFactura;
import ar.edu.unju.escmi.poo.collections.CollectionProducto;
import ar.edu.unju.escmi.poo.collections.CollectionStock;
import ar.edu.unju.escmi.poo.collections.CollectionTarjetaCredito;
import ar.edu.unju.escmi.poo.dominio.Cliente;
import ar.edu.unju.escmi.poo.dominio.Credito;
import ar.edu.unju.escmi.poo.dominio.Detalle;
import ar.edu.unju.escmi.poo.dominio.Factura;
import ar.edu.unju.escmi.poo.dominio.Producto;
import ar.edu.unju.escmi.poo.dominio.Stock;
import ar.edu.unju.escmi.poo.dominio.TarjetaCredito;

public class Principal {

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		CollectionTarjetaCredito.precargarTarjetas();
        CollectionCliente.precargarClientes();
        CollectionProducto.precargarProductos();
        CollectionStock.precargarStocks();
        int opcion = 0; 
        Cliente clienteAuxiliar;
        Producto productoAuxiliar;
        Stock stockAuxiliar;
        TarjetaCredito tarjetaCreditoAuxiliar;
        
        int nroFactura = 1;
        do {
        	System.out.println("\n====== Menu Principal =====");
            System.out.println("1- Realizar una venta");
            System.out.println("2- Revisar compras realizadas por el cliente (debe ingresar el DNI del cliente)");
            System.out.println("3- Mostrar lista de los electrodomésticos");
            System.out.println("4- Consultar stock");
            System.out.println("5- Revisar creditos de un cliente (debe ingresar el DNI del cliente)");
            System.out.println("6- Salir");

            System.out.println("Ingrese su opcion: ");
            opcion = scanner.nextInt();
            
            
            switch(opcion) {
            case 1:
            	System.out.println("******REALIZAR UNA VENTA******");
            	clienteAuxiliar=ingresarDNICliente(scanner);
            	if(clienteAuxiliar==null)
            		break;
            	System.out.println("ABRIENDO NUEVA FACTURA...");
            	Factura nuevaFactura = new Factura(LocalDate.now(),nroFactura,clienteAuxiliar,null);
            	//nroFactura++; AÑADIR MÁS TARDE CUANDO SE CONFIRME LA FACTURA
            	//PRODUCTOS
            	boolean banderaProductoExiste;
            	boolean banderaStockExiste;
            	boolean banderaCantidadValida;
            	List<Detalle> nuevoDetalles = new ArrayList<Detalle>();
            	do {
            		banderaProductoExiste=true;
            		System.out.print("Ingrese el código del producto: ");
            		int nuevoCodigo = scanner.nextInt();
            		System.out.println();
            		//Implementar try-catch para validación de datos.
            		Producto nuevoProducto = CollectionProducto.buscarProducto(nuevoCodigo); 
            		if(nuevoProducto!=null) {
            			Stock stockNuevoProducto = CollectionStock.buscarStock(nuevoProducto);
            			if(stockNuevoProducto!=null) {
            				System.out.print("Ingrese la cantidad: ");
            				int nuevaCantidad = scanner.nextInt();
            				if(stockNuevoProducto.getCantidad()>nuevaCantidad){
            					System.out.println("No existe suficiente stock.");
            				}
            			}
            			else {
            				banderaProductoExiste=false;
            				System.out.println("[!] No existe producto cuyo codigo coincida con el ingresado. Intente de vuelta.");
            			}
            		}
            		
            	}while(!banderaProductoExiste);
            	//FACTURA
            	
            	//CREDITO
            	break;
            case 2:
            	System.out.println("***CONSULTAR COMPRAS DE UN CLIENTE***");
            	clienteAuxiliar=ingresarDNICliente(scanner);
            	if(clienteAuxiliar==null)
            		break;
            	mostrarComprasCliente(clienteAuxiliar);
            	break;
            case 3:
            	System.out.println("***LISTA DE PRODUCTOS DISPONIBLES***");
            	//String[] = "1111";
				System.out.println();
            	for(Producto producto : CollectionProducto.productos) {
            		System.out.println(producto);
            	}
            	break;
            case 4:
            	System.out.println("******CONSULTA DE STOCK******");
            	
            	boolean ingresoOK=true;
            	long codigoProductoConsultar = 0;
            	
        		while(!ingresoOK){
        			try{
        				ingresoOK=true;
        				System.out.print("Ingrese el código del producto: ");
        				codigoProductoConsultar = scanner.nextLong();
        				System.out.println();
        			}
        			catch(InputMismatchException e){
        				System.out.println("[e] Entrada invalida.");
        				scanner.next();
        				ingresoOK=false;
        			}
        		}
        		
        		productoAuxiliar = CollectionProducto.buscarProducto(codigoProductoConsultar);
            	
        		if( productoAuxiliar != null) {
            		
            		System.out.println("******VERIFICANDO...******");
            		stockAuxiliar = CollectionStock.buscarStock(productoAuxiliar);
            		
            		System.out.print("Stock disponible de " + productoAuxiliar.getDescripcion() + ": " + stockAuxiliar.getCantidad());
            		}
        		else {
        			System.out.println("[!] No existe producto que coincida con el codigo ingresado.");
        		}

            	break;
            case 5:
            	System.out.println("******REVISION DE CREDITOS******");
            
        		clienteAuxiliar = ingresarDNICliente(scanner);
            	
        		tarjetaCreditoAuxiliar = CollectionTarjetaCredito.buscarTarjetaCreditoPorCliente(clienteAuxiliar);
        		
        		if( tarjetaCreditoAuxiliar != null) {
            		
            		System.out.println("******VERIFICANDO...******");
            		
            	
            		Credito creditoCliente = CollectionCredito.buscarCredito(tarjetaCreditoAuxiliar);
            		
            		creditoCliente.mostarCredito();
            		
        		}
        		else {
        			System.out.println("[!] No existe producto que coincida con el codigo ingresado.");
        		}
        		
            	break;
            case 6:
            	System.out.println("[*] Fin del Programa");
            }
        }while(opcion != 6);
        scanner.close();

	}
	
	static Cliente ingresarDNICliente(Scanner scanner) {
		long dni=0;
		boolean ingresoOK=true;
		while(!ingresoOK){
			try{
				ingresoOK=true;
				System.out.print("Ingrese el DNI del cliente: ");
				dni = scanner.nextLong();
				System.out.println();
			}
			catch(InputMismatchException e){
				System.out.println("[e] Entrada invalida.");
				scanner.next();
				ingresoOK=false;
			}
		}
		Cliente clienteEncontrado = CollectionCliente.buscarCliente(dni);
		if(clienteEncontrado == null){
			System.out.println("[!] No existe cliente que coincida con el DNI ingresado.");
		}
		return clienteEncontrado;
	}
	static void mostrarComprasCliente(Cliente cliente) {
		boolean compraEncontrada=false;
		for(Factura factura: CollectionFactura.facturas) {
    		if(factura.getCliente()==cliente) {
    			System.out.println(factura);
    			compraEncontrada=true;
    		};
    	};
    	if(!compraEncontrada) {
    		System.out.println("No se han encontrado compras bajo el nombre de este usuario.");
    	}
    	return;
	}
}
