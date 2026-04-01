package estruc_datos;

@SuppressWarnings("hiding")
public class CircleList<Object> extends DoubleEndedList<Object> {

	public CircleList(Object data){
		super(data);
	}

	public CircleList(){
		super();
	}

	//Insert methods
	public void insert(Object data) {
		super.insert(data);
		this.last.setNext(this.first);
	}
	
	public void insert_at(int pos,Object data) {
		super.insert_at(pos, data);
		this.last.setNext(first);
	}

	public void delete(int pos){
		//printAll();
		super.delete(pos);
		this.last.setNext(this.first);
	}
	
	//Quitar este método
	public void printAll() {
		for(int i=0;i<this.getSize();i++) {
			System.out.println(this.getNode(i).getNext().getData());
		}
	}
	
}
