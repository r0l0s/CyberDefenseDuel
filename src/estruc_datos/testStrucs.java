package estruc_datos;

import estruc_datos.CircleList;

public class testStrucs {
    public static void main(String[] args) {
        CircleList <String> ci = new CircleList <String>();
        ci.insert("A");
        ci.insert_at(ci.getSize(),"Z");
        
        //ci.insert("Z");
        ci.insert_at(1,"F");
        
        for(int i=0;i<ci.getSize();i++){
            System.out.println(ci.get(i));
        }
        System.out.println("-----------");/*
        */
        //ci.printAll();
        ci.delete(0);
        for(int i=0;i<ci.getSize();i++){
            System.out.println(ci.get(i));
        }/*

        */
    }
}
