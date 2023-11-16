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
        char opcionSN = 'y';
        long codigoAuxiliar = 0;
        Cliente clienteAuxiliar=null;
        Producto productoAuxiliar=null;
        Stock stockAuxiliar=null;
        TarjetaCredito tarjetaCreditoAuxiliar=null;
        boolean banderaOpcionCorrecta=true;
        int nroFactura = 1;
        do {
        	System.out.println("\n====== Menu Principal =====");
            System.out.println("1- Realizar una venta");
            System.out.println("2- Revisar compras realizadas por el cliente (debe ingresar el DNI del cliente)");
            System.out.println("3- Mostrar lista de los electrodomésticos");
            System.out.println("4- Consultar stock");
            System.out.println("5- Revisar creditos de un cliente (debe ingresar el DNI del cliente)");
            System.out.println("6- Salir");
            
            do {
            	try {
            		System.out.println("Ingrese su opcion: ");
                    opcion = scanner.nextInt();
                    banderaOpcionCorrecta=true;
            	}catch(InputMismatchException e) {
            		scanner.next();
    				System.out.println("[e] Entrada invalida. Vuelva a intentarlo.\n");
    				banderaOpcionCorrecta=false;
            	}
            }while(!banderaOpcionCorrecta);
            System.out.println();
            switch(opcion) {
            case 1:
            	List<Detalle> carrito = new ArrayList<Detalle>();
            	do {
            		System.out.println("******REALIZAR UNA VENTA******");
            		System.out.println("1. Añadir productos al carrito.");
            		System.out.println("2. Ver productos en carrito.");
            		System.out.println("3. Borrar productos del carrito.");
            		System.out.println("4. Generar factura.");
            		System.out.println("5. Salir.");
            		do {
            			try{
            				System.out.println("Ingrese su opción: ");
            				opcion = scanner.nextInt();
            				banderaOpcionCorrecta=true;
            			}catch(InputMismatchException e) {
            				scanner.next();
            				System.out.println("[e] Entrada invalida. Vuelva a intentarlo.\n");
            				banderaOpcionCorrecta=false;
            			}
            		}while(!banderaOpcionCorrecta);
            		System.out.println();
            		switch(opcion) {
            		case 1:
            			mostrarProductos();
            			do {
            				do {
                				try{
                					System.out.println("Ingrese el cod. del producto que desea añadir al carrito: ");
                    				codigoAuxiliar = scanner.nextLong();
                    				banderaOpcionCorrecta=true;
                    			}catch(InputMismatchException e) {
                    				scanner.next();
                    				System.out.println("[e] Entrada invalida. Vuelva a intentarlo.\n");
                    				banderaOpcionCorrecta=false;
                    			}
                			}while(!banderaOpcionCorrecta);
            				productoAuxiliar = CollectionProducto.buscarProducto(codigoAuxiliar);
            				if(productoAuxiliar == null) {
            					System.out.println("El codigo ingresado no coincide con ningun producto de la lista. Desea intentar de vuelta? (s/n)");
            					opcionSN = scanner.next().charAt(0);
            					continue;
            				}
            				if(encontrarProductoCarrito(carrito,productoAuxiliar)) {
            					System.out.println("[!] Este producto ya esta en el carrito y no se puede volver a ingresar. Desea intentar de vuelta? (s/n)");
        						opcionSN = scanner.next().charAt(0);
        						continue;
            				}
            				stockAuxiliar = CollectionStock.buscarStock(productoAuxiliar);
            				System.out.println("----------------------------------------------------------------------------------------------------------");
            				System.out.printf("| %-4s | %-62s | %-9s | %-10s | %-5s | %n","COD","DESCRIPCIÓN","PRECIO U.","ORIGEN","STOCK");
            				System.out.printf("| %4d | %-62s | %9.2f | %-10s | %5d | %n",productoAuxiliar.getCodigo(),productoAuxiliar.getDescripcion(),productoAuxiliar.getPrecioUnitario(),productoAuxiliar.getOrigenFabricacion(),stockAuxiliar.getCantidad());
            				System.out.println("----------------------------------------------------------------------------------------------------------");
            				if(stockAuxiliar.getCantidad()==0) {
            					System.out.println("[!] Este producto no tiene stock disponible. Desea intentar de vuelta? (s/n)");
        						opcionSN = scanner.next().charAt(0);
        						continue;
            				}
            				int cantidadProducto=0;
            				do {
                				try{
                					System.out.println("Ingrese cantidad a ordenar: ");
                					cantidadProducto = scanner.nextInt();
                    				banderaOpcionCorrecta=true;
                    			}catch(InputMismatchException e) {
                    				scanner.next();
                    				System.out.println("[e] Entrada invalida. Vuelva a intentarlo.\n");
                    				banderaOpcionCorrecta=false;
                    			}
                				if(cantidadProducto<1) {
                					System.out.println("[!] Tiene que ingresar una cantidad mayor a 0. Intente de vuelta.");
                					banderaOpcionCorrecta=false;
                				}
                				if(cantidadProducto>stockAuxiliar.getCantidad()) {
                					System.out.println("[!] La cantidad no puede ser mayor al stock disponible. Intente de vuelta.");
                					banderaOpcionCorrecta=false;
                				}
                			}while(!banderaOpcionCorrecta);
            				Detalle nuevoDetalle = new Detalle(cantidadProducto,0,productoAuxiliar);
            				carrito.add(nuevoDetalle);
            				System.out.println("Producto añadido con exito! Mostrando detalles...");
            				System.out.println(nuevoDetalle);
            				System.out.println("¿Desea añadir otro producto al carrito? (s/n)");
    						opcionSN = scanner.next().charAt(0);
            			}while(opcionSN!='n');
            			break;
            		case 2:
            			if(carrito.isEmpty()) {
            				System.out.println("El carrito esta vacío");
            				break;
            			}
            			double totalCarrito = 0;
            			for(Detalle detalle : carrito) {
            				totalCarrito += detalle.getImporte();
            			}
            			System.out.println("------------------------------------------------------------------------------------------------------------");
            			System.out.printf("| %-4s | %-62s | %-9s | %-5s | %-12s | %n","COD","DESCRIPCION","PRECIO U.","CANT.","IMPORTE");
            			System.out.println("------------------------------------------------------------------------------------------------------------");
            			for(Detalle detalle : carrito) {
            				System.out.printf("| %-4d | %-62s | %9.2f | %5d | %12.2f | %n",detalle.getProducto().getCodigo(),detalle.getProducto().getDescripcion(),detalle.getProducto().getPrecioUnitario(),detalle.getCantidad(),detalle.getImporte());
            			}
            			System.out.println("------------------------------------------------------------------------------------------------------------");
            			System.out.printf("%83s | %5s | %12.2f | %n"," ","TOTAL",totalCarrito);
            			break;
            		case 3:
            			System.out.println("------------------------------------------------------------------------------------------------------------");
            			System.out.printf("| %-4s | %-62s | %-9s | %-5s | %-12s | %n","COD","DESCRIPCION","PRECIO U.","CANT.","IMPORTE");
            			System.out.println("------------------------------------------------------------------------------------------------------------");
            			for(Detalle detalle : carrito) {
            				System.out.printf("| %-4d | %-62s | %9.2f | %5d | %12.2f | %n",detalle.getProducto().getCodigo(),detalle.getProducto().getDescripcion(),detalle.getProducto().getPrecioUnitario(),detalle.getCantidad(),detalle.getImporte());
            			}
            			System.out.println("------------------------------------------------------------------------------------------------------------");
            			do {
            				try{
            					System.out.println("Ingrese cod. de producto a eliminar: ");
            					codigoAuxiliar = scanner.nextInt();
            					productoAuxiliar=CollectionProducto.buscarProducto(codigoAuxiliar);
                				banderaOpcionCorrecta=true;
                			}catch(InputMismatchException e) {
                				scanner.next();
                				System.out.println("[e] Entrada invalida. Vuelva a intentarlo.");
                				banderaOpcionCorrecta=false;
                			}
            				if(productoAuxiliar==null) {
            					System.out.println("[!] No existe dicho producto. Vuelva a intentarlo.");
            					banderaOpcionCorrecta=false;
            				}
            			}while(!banderaOpcionCorrecta);
            			boolean productoEncontrado=false;
            			for(int i=0;i<carrito.size();i++) {
            				if(carrito.get(i).getProducto()==productoAuxiliar) {
            					carrito.remove(i);
            					productoEncontrado=true;
            					System.out.println("Producto removido con exito.");
            					break;
            				}
            			}
            			if(!productoEncontrado) {
            				System.out.println("[!] No se ha encontrado el producto en el carrito");
            			}
            		}
            		System.out.println();
            	}while(opcion!=4);
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
            	mostrarProductos();
            	break;
            case 4:
            	System.out.println("******CONSULTA DE STOCK******");
        		do{
        			try{
        				banderaOpcionCorrecta=true;
        				System.out.print("Ingrese el código del producto: ");
        				codigoAuxiliar = scanner.nextLong();
        				System.out.println();
        			}
        			catch(InputMismatchException e){
        				System.out.println("[e] Entrada invalida.");
        				scanner.next();
        				banderaOpcionCorrecta=false;
        			}
        		}while(!banderaOpcionCorrecta);
        		productoAuxiliar = CollectionProducto.buscarProducto(codigoAuxiliar);
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
        			System.out.println("[!] No se encontraron los creditos correspondientess.");
        		}
        		
            	break;
            case 6:
            	System.out.println("[*] Fin del Programa");
            }
            System.out.println();
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
	static boolean encontrarProductoCarrito(List<Detalle> carrito,Producto productoAuxiliar) {
		for(Detalle detalle : carrito) {
			if(detalle.getProducto()==productoAuxiliar) {
				return true;
			}
		}
		return false;
	}
	static void mostrarProductos() {
 			System.out.println("--------------------------------------------------------------------------------------------------");
 			System.out.printf("| %-4s | %-62s | %-9s | %-10s | %n","COD","DESCRIPCIÓN","PRECIO U.","ORIGEN");
 			System.out.println("--------------------------------------------------------------------------------------------------");
 			for(Producto producto : CollectionProducto.productos) {
 				System.out.printf("| %4d | %-62s | %9.2f | %-10s | %n",producto.getCodigo(),producto.getDescripcion(),producto.getPrecioUnitario(),producto.getOrigenFabricacion());
 			}
 			System.out.println("--------------------------------------------------------------------------------------------------");
	}
}
