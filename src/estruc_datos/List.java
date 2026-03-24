package estruc_datos;

abstract class List <T>{
	protected Node first;
	protected Node last;
	protected int size;
	
	List(T data){
		this.first = new Node<>(data);
		this.last = this.first;
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
	

	
	@SuppressWarnings("hiding")
	class Node<T>{
		private T data;
		private Node<?> next;
		
		Node(T data){
			this.next = null;
			this.data = data;
		}
		
		public Node getNext() {
			return this.next;
		}
		
		public void setNext(Node next) {
			this.next = next;
		}

		public T getData() {
			return data;
		}		
		
	}
}

