package estruc_datos;

public class DoubleLinkedList<Object>{
    protected Node first;
	protected int size;
	
    public DoubleLinkedList() {
		this.first = new Node<>(null,null);
		this.size = 1;
	}
	
	//Setters and getters
	public int getSize() {
		return this.size;
	}
	
	protected Node getNode(int pos) {
		Node<?> node = this.first;
		for (int i=0;i<this.size;i++) {
			if(pos == i) {
				break;
			}
			node = node.getNext();
		}
		return node;
		
	}

    //Get methods:
	@SuppressWarnings("unchecked")
	public Object get(int pos) {
		if (pos>this.size || pos<0) {
			throw new IndexOutOfBoundsException("Posición de la lista se sale de sus límites");
		}
		
		return (Object) getNode(pos).getData();
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
		Node<Object> node = new Node<>(data,getNode(getSize()-1));
		if (getSize()==0){
			this.first = node;
		}
		getNode(getSize()-1).setNext(node);
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
		Node<Object> new_node = new Node<>(data,node);
		
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
		if (node.getNext()!=null) {
			//If the delete is in any other place
			last.setNext(node.getNext());
		}
		
		this.size--;
	}
	
	class Node<T>{
		private T data;
		private Node<?> next;
        private Node<?> last; 
		
		Node(T data, Node last){
			this.next = null;
            this.last = last;
			this.data = data;
		}
		
		public Node getNext() {
			return this.next;
		}

        public Node getLast(){
            return this.last;
        }
		
		public void setNext(Node next) {
			this.next = next;
		}

		public T getData() {
			return data;
		}		
		
	}
	
}
