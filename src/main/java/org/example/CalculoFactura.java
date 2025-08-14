package org.example;

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
        //2A
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
        String tipoPago="";
        double recargo=0;
        do {
            System.out.println("Tipo de pago C-TC-TD");
            tipoPago= scanner.nextLine();
            if (tipoPago.equalsIgnoreCase("C")) {
                System.out.println("Eligió pagar al contado");
                factura.setTipoPago(tipoPago);
                break;
            } else if (tipoPago.equalsIgnoreCase("TC")) {
                System.out.println("Eligio pagar con targeta de credito");
                recargo=10;
                factura.setTipoPago(tipoPago);
                break;
            } else if (tipoPago.equalsIgnoreCase("TD")) {
                System.out.println("Eligio pagar con targeta de debito");
                recargo=5;
                factura.setTipoPago(tipoPago);
                break;
            } else {
                System.out.println("No es una opcion valida, intente de nuevo.");
                oPcorrecta = false;
            }
        } while (!oPcorrecta);
        //2B
        int cantItems;
        do {
            System.out.println("Indique la cantidad de articulos a facturar.");
            cantItems = Integer.parseInt(scanner.nextLine());
            if (cantItems <= 0) {
                System.out.println("El numero de items debe ser mayor a 0, intente de nuevo");
            }
        } while (cantItems <= 0);
        //2C
        // item | denominacion | precio unitario | cantidad | subtotal
        String[][] itemsFactura = new String[cantItems][5];
        factura.setItemsFactura(itemsFactura);

        //2D
        int articulosRestantes = cantItems;
        String[][] cargandoItems = factura.getItemsFactura();

        String cantidadXart = "";
        String precio = "";
        String subtotal = "";
        double sumaSubTotales = 0;
        double c, p, subT;
        int incrementar=1;
        while (articulosRestantes > 0) {
            System.out.println("Ingrese los articulos a facturar");
            System.out.println("CODIGO DEL ARTICULO del 100 al 111");
            System.out.println("Articulo n° " + incrementar);
            String codigo = scanner.nextLine();
            //3 busco el codigo ingresado
            //codigo es sobre filas col 0
            String[] artEncontrado = null;

            for (int i = 0; i < articulos.length; i++) {
                if (codigo.equalsIgnoreCase(articulos[i][0])) {
                    System.out.println("Se encontro el articulo: "+ articulos[i][1]);

                    //4 cant x items
                    while (true) {
                        System.out.println("Indique cantidad en: "+ articulos[i][3]);
                        cantidadXart = scanner.nextLine();//validacion del string
                        //validad unidad o kg
                        if (articulos[i][3].equals("U") && cantidadXart.contains(".")) {
                            System.out.println("Ingrese un numero entero para Unidades");
                            continue;
                        }
                        break;
                    }
                    artEncontrado = articulos[i];  //3.1 se encuentra y se guarda
                    break;
                }
            }
            if (artEncontrado == null) {
                System.out.println("No se encontro el articulo, intente de nuevo"); //3.2
                continue;
            }
            //3.1 aca asigno en itemsFactura de Factura
            for (int l = 0; l < cargandoItems.length; l++) {
                if (cargandoItems[l][0] == null) {
                    cargandoItems[l][0] = artEncontrado[0]; //ASIGNO EL CODIGO
                    cargandoItems[l][1] = artEncontrado[1]; //ASIGNO DENOMINACION
                    cargandoItems[l][2] = artEncontrado[2]; //ASIGNO PRECIO UNITARIO
                    //4 guardo
                    cargandoItems[l][3] = cantidadXart; //ASIGNO CANTIDAD POR ARTICULO
                    //5 subtotal
                    p = Double.parseDouble(cargandoItems[l][2]);
                    c = Double.parseDouble(cargandoItems[l][3]);
                    subT = c * p;
                    cargandoItems[l][4] = String.valueOf(subT);

                    //sumar subtotales 5e
                    sumaSubTotales += Double.parseDouble(cargandoItems[l][4]);
                    break;
                }
            }
            articulosRestantes--;
            incrementar++;
        }


        factura.setItemsFactura(cargandoItems);

        //5F RECARGO
        //5H MONTO FINAL
        double totalFinal=0;
        System.out.println("...........................................................");
        System.out.println("Cliente..............."+ factura.getRazonSocialCliente());
        System.out.println("Fecha................."+ factura.getFecha());
        System.out.println("Numero................"+ factura.getNroFactura());
        System.out.println("Tipo de pago.........."+ factura.getTipoPago());
        System.out.println("...........................................................");
        System.out.println("CODIGO | DENOMINACION | PRECIO | CANTIDAD | SUBTOTAL");
        for (int l = 0; l < cargandoItems.length; l++) {
            for (int h = 0; h < cargandoItems[l].length; h++) {
                System.out.print(cargandoItems[l][h] + "   | ");   //mostrar
            }
            System.out.println("");
        }
        System.out.println("...........................................................");
        System.out.println("TOTAL ITEMS: $" + sumaSubTotales);
        //5F RECARGO
        //5H MONTO FINAL
        double recargoFinal=(recargo * sumaSubTotales)/100;
        System.out.println("RECARGO: $"+ recargoFinal );
        System.out.println("MONTO FINAL: $" + (sumaSubTotales + recargoFinal));


    }

}


