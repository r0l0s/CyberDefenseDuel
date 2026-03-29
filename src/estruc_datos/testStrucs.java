package estruc_datos;

import estruc_datos.CircleList;

public class testStrucs {
    public static void main(String[] args) {
        CircleList <String> ci = new CircleList <String>();
        ci.insert("A");
        ci.insert_at(ci.getSize()-1,"Z");
        //ci.insert("Z");
        ci.insert_at(1,"F");
        System.out.println(ci.last.getNext().getData());
        /*
        for(int i=0;i<ci.getSize();i++){
            System.out.println(ci.get(i));
        }
        System.out.println("-----------");
        ci.delete(0);
        //ci.printAll();
        for(int i=0;i<ci.getSize();i++){
            System.out.println(ci.get(i));
        }

        */
    }
}
