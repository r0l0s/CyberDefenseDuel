package estruc_datos;

import java.util.Scanner;
import java.util.stream.*;

public class RendimientoEstructuraDatos {
    public static void main (String[]args){
        RendimientoEstructuraDatos.comparacion2(10);
        System.out.println("Fin de la ejecución");
    }

    public static void comparacion1(int n){
        String[] array = new String[n];
        LinkedList<String> linkedlst = new LinkedList<String>();
        DoubleLinkedList<String> dlinkedlst = new DoubleLinkedList<String>();      
        DoubleEndedList<String> dendedlst = new DoubleEndedList<String>();
        //CircleList <String> circlelst = new CircleList<String>();

        //Inserción al inicio:
        array[0] = "A";
        linkedlst.insert("A");
        dlinkedlst.insert("A");
        dendedlst.insert("A");
        //circlelst.insert("A");
        
        //Inserción al final
        array[array.length-1] = "Z";
        linkedlst.insert_at(linkedlst.getSize(), "Z");
        dlinkedlst.insert_at(dlinkedlst.getSize(), "Z");
        dendedlst.insert_at(dendedlst.getSize(), "Z");
        //circlelst.insert_at(circlelst.getSize(), "Z");

        //Inserción en posición intermedia
        array[(int) n/2] = "F";
        linkedlst.insert_at(1,"F");
        dlinkedlst.insert_at(1,"F");
        dendedlst.insert_at(1,"F");
        //circlelst.insert_at(1,"F");

        //Eliminación en una posición
        array[0] = "";
        linkedlst.delete(0);
        dlinkedlst.delete(0);
        dendedlst.delete(0);
        //circlelst.delete(0);

        
        // region Búsqueda
        String e = "Z";
        for(int i=0; i<array.length;i++){
            if (array[i] == e){
                System.out.println("Elemento encontrado en la posición: "+i);
                break;
            }
        }

        for(int i=0; i<linkedlst.getSize();i++){
            if (linkedlst.get(i)== e){
                System.out.println("Elemento encontrado en la posición: "+i);
                break;
            }
        }

        for(int i=0; i<dlinkedlst.getSize();i++){
            if (dlinkedlst.get(i)== e){
                System.out.println("Elemento encontrado en la posición: "+i);
                break;
            }
        }

        for(int i=0; i<dendedlst.getSize();i++){
            if (dendedlst.get(i)== e){
                System.out.println("Elemento encontrado en la posición: "+i);
                break;
            }
        }
        /* 
        for(int i=0; i<circlelst.getSize();i++){
            if (circlelst.get(i)== e){
                System.out.println("Elemento encontrado en la posición: "+i);
                break;
            }
        }*/
        //endregion
   
        //Acceso por índice
        System.out.println(array[0]);
        System.out.println(linkedlst.get(0));
        System.out.println(dlinkedlst.get(0));
        System.out.println(dendedlst.get(0));
        //System.out.println(circlelst.get(0));

        //Reemplazo de un dato
        String temp = "G";
        array[(int) n/2] = temp;
        linkedlst.delete(1);
        linkedlst.insert_at(1,temp);
        dlinkedlst.delete(1);
        dlinkedlst.insert_at(1,temp);
        dendedlst.delete(1);
        dendedlst.insert_at(1,temp);
        //circlelst.delete(1);
        //circlelst.insert_at(1,temp);
        

    }

    public static void comparacion2(int n){
        Queue qu = new Queue(n);
        QueueList quL = new QueueList<Object>();
        Stack st = new Stack(n);
        StackList stL = new StackList<Object>();
        Scanner input = new Scanner(System.in);
        System.out.println("Ingrese un número 1 para ejercicio 1, 2 para ejercicio 2, cualquier otro para salir");
        int option = input.nextInt();
        if (option == 1){
            System.out.println("Ingrese un número 1 para estructuras con array, 2 estructuras de nodos, cualquier otro para salir");
            option = input.nextInt();
            switch (option) {
                case 1:
                    ejercicio1(st, qu);
                    break;
                case 2:
                    ejercicio1(stL, quL);
                    break;
                default:
                    System.out.println("Ninguna opción elegida... Saliendo");
                    break;
            }
        }else if (option == 2){
            System.out.println("Ingrese un número 1 para estructuras con array, 2 estructuras de nodos, cualquier otro para salir");
            option = input.nextInt();
            switch (option) {
                case 1:
                    ejercicio2(st, qu);
                    break;
                case 2:
                    ejercicio2(stL, quL);
                    break;
                default:
                    System.out.println("Ninguna opción elegida... Saliendo");
                    break;
            }
        }else{
            System.out.println("Ninguna opción elegida... Saliendo");
        }

    }

    public static void ejercicio1(Stack st, Queue qu){

    }

    public static void ejercicio1(StackList st,QueueList qu){

    }

    public static void ejercicio2(Stack st, Queue ue){

    }

    public static void ejercicio2(StackList st,QueueList qu){

    }
}
