package estruc_datos;

public class testStrucs {
    public static void main(String[] args) {
        LinkedList<String> lst = new LinkedList<String>("a");
        lst.delete(0);
        lst.insert("xd");
        System.out.println(lst.getSize());
        System.out.println(lst.get(0));
    }
}
