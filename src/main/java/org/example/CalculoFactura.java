package org.example;

import java.sql.SQLOutput;
import java.util.Scanner;

public class CalculoFactura {
    static String[][] articulos = {

            //codigo | denominacion | precio | unidad
            {"100", "Azucar", "20", "U"},
            {"101", "Leche", "30", "U"},
            {"102", "Aceite", "50", "U"},
            {"103", "Sal", "45", "U"},
            {"104", "Fideos", "15", "U"},
            {"105", "Arroz", "28", "U"},
            {"106", "Galletas", "26", "U"},
            {"107", "Carne Molida", "220", "Kg"},
            {"108", "Shampoo", "42", "U"},
            {"109", "Queso Mantecoso", "178", "Kg"},
            {"110", "Jamon Cocido", "320", "Kg"},
            {"111", "Naranjas", "80", "Kg"}
    };

    public static void main(String[] args) {
        //A
        Factura factura = new Factura();
        Scanner scanner = new Scanner(System.in);
        System.out.println("");
        System.out.println("------------------------------------------------");
        System.out.println("Por favor, cargue los dastos de la FACTURA");
        System.out.println("FECHA");
        String fecha = scanner.nextLine();
        factura.setFecha(fecha);
        System.out.println("NUMERO DE FACTURA");
        Long numero = Long.parseLong(scanner.nextLine());
        factura.setNroFactura(numero);
//DATOS DEL CLIENTE
        System.out.println("Razon social del CLIENTE");
        String razonSocialCliente = scanner.nextLine();
        factura.setRazonSocialCliente(razonSocialCliente);

        System.out.println("CUIT del CLIENTE");
        long cuitCliente = Long.parseLong(scanner.nextLine());
        factura.setCuitCliente(cuitCliente);

        boolean oPcorrecta = true;
        do {
            System.out.println("Tipo de pago C-TC-TD");
            String tipoPago = scanner.nextLine();
            if (tipoPago.equalsIgnoreCase("C")) {
                System.out.println("Eligió pagar al contado");
                break;
            } else if (tipoPago.equalsIgnoreCase("TC")) {
                System.out.println("Eligio pagar con targeta de credito");
                break;
            } else if (tipoPago.equalsIgnoreCase("TD")) {
                System.out.println("Eligio pagar con targeta de debito");
                break;
            } else {
                System.out.println("No es una opcion valida, intente de nuevo.");
                oPcorrecta = false;
            }
        } while (!oPcorrecta);
        //B
        int cantItems;
        do {
            System.out.println("Indique la cantidad de articulos a facturar.");
            cantItems = Integer.parseInt(scanner.nextLine());
            if (cantItems <= 0) {
                System.out.println("El numero de items debe ser mayor a 0, intente de nuevo");
            }
        } while (cantItems <= 0);
        //C
        // item | denominacion | precio unitario | cantidad | subtotal
        String[][] itemsFactura = new String[cantItems][4];
        factura.setItemsFactura(itemsFactura);

        // d) Cargar artículos
        double montoTotalItems = 0;

        for (int itemsCargados = 0; itemsCargados < cantItems; ) {
            System.out.println("-----ARTÍCULOS A FACTURAR-----");
            System.out.println("Ingrese el código del artículo (100 al 111):");
            String codigo = scanner.nextLine().trim();

            // Buscar artículo
            boolean encontrado = false;
            for (int i = 0; i < articulos.length; i++) {
                if (codigo.equalsIgnoreCase(articulos[i][0])) {
                    encontrado = true;

                    // Copiar datos básicos
                    itemsFactura[itemsCargados][0] = articulos[i][0]; // Código
                    itemsFactura[itemsCargados][1] = articulos[i][1]; // Denominación
                    itemsFactura[itemsCargados][2] = articulos[i][2]; // Precio unitario
                    String unidad = articulos[i][3];                  // Unidad

                    // Pedir cantidad según unidad
                    double cantidad = 0;
                    boolean cantidadValida = false;

                    do {
                        System.out.println("Ingrese la cantidad para " + articulos[i][1] + " (Unidad: " + unidad + "):");
                        String inputCantidad = scanner.nextLine().trim();

                        try {
                            if (unidad.equalsIgnoreCase("U")) {
                                // Valida que sea entero sin punto
                                if (inputCantidad.contains(".")) {
                                    System.out.println("Cantidad inválida para unidades. Ingrese un número entero.");
                                } else {
                                    int cantInt = Integer.parseInt(inputCantidad);
                                    if (cantInt <= 0) {
                                        System.out.println("La cantidad debe ser mayor a cero.");
                                    } else {
                                        cantidad = cantInt;
                                        cantidadValida = true;
                                    }
                                }
                            } else if (unidad.equalsIgnoreCase("Kg")) {
                                // Puede ser double
                                cantidad = Double.parseDouble(inputCantidad);
                                if (cantidad <= 0) {
                                    System.out.println("La cantidad debe ser mayor a cero.");
                                } else {
                                    cantidadValida = true;
                                }
                            } else {
                                System.out.println("Unidad desconocida, intente de nuevo.");
                            }
                        } catch (NumberFormatException e) {
                            System.out.println("Entrada inválida. Ingrese un número válido.");
                        }
                    } while (!cantidadValida);


                    itemsFactura[itemsCargados][3] = String.valueOf(cantidad);

                    // Calcular subtotal
                    double precioUnitario = Double.parseDouble(articulos[i][2]);
                    double subtotal = precioUnitario * cantidad;
                    itemsFactura[itemsCargados][4] = String.format("%.2f", subtotal);

                    montoTotalItems += subtotal;
                    itemsCargados++; // Solo incremento si se cargó un artículo válido
                    break;
                }
            }

            if (!encontrado) {
                System.out.println("El código ingresado es incorrecto. Intente de nuevo.");
            }
        }

        // e) Calcular monto total items
        factura.setMontoTotalItems(montoTotalItems);

        // f) Calcular recargo según tipo de pago
        double recargo = 0;
        switch (factura.getTipoPago()) {
            case "C": // Contado
                recargo = 0;
                break;
            case "TD": // Tarjeta Debito 5%
                recargo = montoTotalItems * 0.05;
                break;
            case "TC": // Tarjeta Credito 10%
                recargo = montoTotalItems * 0.10;
                break;
        }
        factura.setRecargo(recargo);

        // g) Calcular monto final
        double montoFinal = montoTotalItems + recargo;
        factura.setMontoFinal(montoFinal);

        // h) Mostrar ticket
        System.out.println("\nEl ticket a pagar es:");
        System.out.printf("Monto total items: $%.2f%n", montoTotalItems);
        System.out.printf("Recargo (%s): $%.2f%n", factura.getTipoPago(), recargo);
        System.out.printf("Monto final a pagar: $%.2f%n", montoFinal);

        // Opcional: mostrar detalle de items
        System.out.println("\nDetalle de artículos facturados:");
        System.out.println("Código | Denominación | Precio Unit. | Cantidad | Subtotal");
        for (int i = 0; i < cantItems; i++) {
            System.out.printf("%6s | %-12s | %12s | %8s | %8s%n",
                    itemsFactura[i][0], itemsFactura[i][1], itemsFactura[i][2], itemsFactura[i][3], itemsFactura[i][4]);
        }
    }
}