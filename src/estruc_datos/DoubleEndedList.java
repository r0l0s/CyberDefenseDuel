package estruc_datos;

@SuppressWarnings("hiding")
public class DoubleEndedList<Object> extends List<Object> {

	public DoubleEndedList(Object data) {
		super(data);
	}
	
	//Get methods:
	@SuppressWarnings("unchecked")
	public Object get(int pos) {
		if (pos>this.size || pos<0) {
			throw new IndexOutOfBoundsException("Posición de la lista se sale de sus límites");
		}
		
		return (Object) super.getNode(pos).getData();
	}

	public int get(Object obj){
		int pos = 0;
		for(int i=0;i<getSize();i++){
			if (get(i) == obj){
				pos = i;
			}
		}
		return pos;
	}
	
	//Insert methods
	public void insert(Object data) {
		Node<Object> node = new Node<>(data);
		if (getSize()==0){
			this.first = node;
		}
		this.last.setNext(node);
		this.last = node;
		this.size++;
	}
	
	@SuppressWarnings({ "unchecked"})
	public void insert_at(int pos,Object data) {
		//This has a problem what happen when the @pos is 0
		//There´s a implementation but idk is correct or not
		if (pos>this.size || pos<0) {
			throw new IndexOutOfBoundsException("Posición de la lista se sale de sus límites");
		}
		
		Node<Object> node = null;
		//If the insertion is in the first position
		if (pos ==  0) {
			node = this.first;
		}else {
			node = this.getNode(pos-1);
		}
		
		Node<Object> next_node = node.getNext();
		Node<Object> new_node = new Node<>(data);
		
		new_node.setNext(next_node);
		node.setNext(new_node);
		this.size++;
		
	}
	
	//delete methods
	@SuppressWarnings("unchecked")
	public void delete(int pos){
		Node<Object> node = this.getNode(pos);
		Node<Object> last = this.getNode(pos-1);
		
		//If the delete is in the last position
		if (last == null) {
			this.size--;
			this.first = node.getNext();
			return;
		}
		
		//If the delete is in the first position
		if (node.getNext()==null) {
			this.last = last;
			this.last.setNext(null);
		}else {
			//If the delete is in any other place
			last.setNext(node.getNext());
		}
		
		this.size--;
	}
}
