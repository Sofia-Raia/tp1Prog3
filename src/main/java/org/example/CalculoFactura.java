package org.example;

import java.sql.SQLOutput;
import java.util.Scanner;

public class CalculoFactura {
    static String [][] articulos = {

    //codigo | denominacion | precio | unidad
        {"100","Azucar", "20", "U"},
        {"101","Leche" , "30", "U"} ,
        {"102","Aceite", "50" , "U"} ,
        {"103","Sal", "45" , "U"} ,
        {"104","Fideos", "15" , "U"} ,
        {"105","Arroz", "28" , "U"} ,
        {"106","Galletas", "26" , "U"} ,
        {"107","Carne Molida", "220" , "Kg"} ,
        {"108","Shampoo", "42" , "U"} ,
        {"109","Queso Mantecoso", "178" , "Kg"} ,
        {"110","Jamon Cocido", "320" , "Kg"} ,
        {"111","Naranjas","80" , "Kg"}
    };
    public static void main(String[] args) {
        //A
        Factura factura =new Factura();
        Scanner scanner = new Scanner(System.in);

        System.out.println("Por favor, cargue los dastos de la FACTURA");
        System.out.println("FECHA");
        String fecha = scanner.nextLine();
        System.out.println("NUMERO DE FACTURA");
        Long numero = Long.parseLong(scanner.nextLine());
//DATOS DEL CLIENTE
        System.out.println("Razon social del CLIENTE");
        String razonSocialCliente= scanner.nextLine();
        System.out.println("CUIT del CLIENTE");
        long cuitCliente= Long.parseLong(scanner.nextLine());
        boolean oPcorrecta=true;
        do{
            System.out.println("Tipo de pago C-TC-TD");
            String tipoPago= scanner.nextLine();
            if (tipoPago.equalsIgnoreCase("C")){
                System.out.println("Eligió pagar al contado");
                break;
            }else if (tipoPago.equalsIgnoreCase("TC")){
                System.out.println("Eligio pagar con targeta de credito");
                break;
            }else if (tipoPago.equalsIgnoreCase("TD")){
                System.out.println("Eligio pagar con targeta de debito");
                break;
            }else {
                System.out.println("No es una opcion valida, intente de nuevo.");
                oPcorrecta=false;
            }
        }while(!oPcorrecta);
        //B
        int cantItems;
        do{
            System.out.println("Indique la cantidad de articulos a facturar.");
           cantItems= Integer.parseInt(scanner.nextLine());
           if (cantItems <= 0){
               System.out.println("El numero de items debe ser mayor a 0, intente de nuevo");
           }
        }while(cantItems <= 0);
        //C
        // item | denominacion | precio unitario | cantidad | subtotal
        String [][] tamItemsFactura = new String [cantItems][4];
        factura.setItemsFactura(tamItemsFactura);
        //D
        System.out.println("-----ARTICULOS A FACTURAR----- ");
        System.out.println("Ingrese el codigo del articulo, del 100 al 111: ");
//me muevo en la col O sobre filas
        String codigo = scanner.nextLine();

        for (int i = 0; i < articulos.length; i++) {
            if (articulos[i][0].equalsIgnoreCase(codigo)){
                System.out.println("Se encontro el articulo");
                //factura.setItemsFactura(articulos[i][0]); punto 3.1
            }
        }
    }


}
