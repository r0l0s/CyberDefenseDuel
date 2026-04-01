package estruc_datos;

public class RendimientoEstructuraDatos {
    public static void main (String[]args){
        RendimientoEstructuraDatos.comparacion1(10);
        System.out.println("Fin de la ejecución");
    }

    public static void comparacion1(int n){
        String[] array = new String[n];
        LinkedList<String> linkedlst = new LinkedList<String>();
        DoubleLinkedList<String> dlinkedlst = new DoubleLinkedList<String>();      
        DoubleEndedList<String> dendedlst = new DoubleEndedList<String>();
        CircleList <String> circlelst = new CircleList<String>();

        //Inserción al inicio:
        array[0] = "A";
        linkedlst.insert("A");
        dlinkedlst.insert("A");
        dendedlst.insert("A");
        circlelst.insert("A");
        
        //Inserción al final
        array[array.length-1] = "Z";
        linkedlst.insert_at(linkedlst.getSize()-1, "Z");
        dlinkedlst.insert_at(dlinkedlst.getSize()-1, "Z");
        dendedlst.insert_at(dendedlst.getSize()-1, "Z");
        circlelst.insert_at(circlelst.getSize(), "Z");

        //Inserción en posición intermedia
        array[(int) n/2] = "F";
        linkedlst.insert_at(1,"F");
        dlinkedlst.insert_at(1,"F");
        dendedlst.insert_at(1,"F");
        circlelst.insert_at(1,"F");

        //Eliminación en una posición
        array[0] = "";
        linkedlst.delete(0);
        dlinkedlst.delete(0);
        dendedlst.delete(0);
        circlelst.delete(0);

        
        /* region Búsqueda
        String e = "F";
        for(int i=0; i<array.length;i++){
            if (array[i] == e){
                System.out.println("Elemento encontrado en la posición: "+i);
            }
        }

        for(int i=0; i<linkedlst.getSize();i++){
            if (linkedlst.get(i)== e){
                System.out.println("Elemento encontrado en la posición: "+i);
            }
        }

        for(int i=0; i<linkedlst.getSize();i++){
            if (linkedlst.get(i)== e){
                System.out.println("Elemento encontrado en la posición: "+i);
            }
        }
        endregion */
   
        //Acceso por índice
        System.out.println(array[0]);
        System.out.println(linkedlst.get(0));
        System.out.println(dlinkedlst.get(0));
        System.out.println(dendedlst.get(0));
        System.out.println(circlelst.get(0));

        //Reemplazo de un dato
        String temp = "E";
        array[(int) n/2] = temp;
        linkedlst.delete(1);
        linkedlst.insert_at(1,temp);
        dlinkedlst.delete(1);
        dlinkedlst.insert_at(1,temp);
        dendedlst.delete(1);
        dendedlst.insert_at(1,temp);
        circlelst.delete(1);
        circlelst.insert_at(1,temp);

    }
}
