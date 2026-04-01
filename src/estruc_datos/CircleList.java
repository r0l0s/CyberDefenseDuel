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
		Node<Object> node = this.getNode(pos);
		Node<Object> last = this.getNode(pos-1);
		
		//If the delete is in the first position
		if (last == null) {
			this.size--;
			this.first = node.getNext();
			return;
		}
		
		//If the delete is in the last position
		if (node.getNext()==first) {
			this.last = last;
		}else {
			//If the delete is in any other place
			last.setNext(node.getNext());
		}
		
		this.last.setNext(this.first);
		this.size--;
	}
	
	//Quitar este método
	public void printAll() {
		for(int i=0;i<this.getSize();i++) {
			System.out.println(this.getNode(i).getNext().getData());
		}
	}
	
}
