package estruc_datos;

import estruc_datos.CircleList;

public class testStrucs {
    public static void main(String[] args) {
        CircleList <String> ci = new CircleList <String>();
        ci.insert("A");
        ci.insert_at(ci.getSize(),"Z");
        
        //ci.insert("Z");
        ci.delete(1);
        //System.out.println(ci.getSize());
        ci.insert_at(1,"G");
        ci.printAll();
        /* 
        for(int i=0;i<ci.getSize();i++){
            System.out.println(ci.get(i));
        }
        System.out.println("-----------");*/
        /*
        //ci.printAll();
        ci.delete(1);
        ci.insert_at(1, "E");
        for(int i=0;i<ci.getSize();i++){
            System.out.println(ci.get(i));
        }/*

        */
    }
}
