package estruc_datos;

@SuppressWarnings("hiding")
public class QueueList<Object> extends List<Object> {

	public QueueList(Object data) {
		super(data);
		// TODO Auto-generated constructor stub
	}

	public QueueList() {
		super();
		// TODO Auto-generated constructor stub
	}

	//Get methods:
		@SuppressWarnings("unchecked")
		public Object peek(){
			return (Object) this.first.getData();
		}
		
		//Insert methods:
		public void enqueue(Object data) {
			Node<Object> node = new Node<>(data);
			this.last.setNext(node);
			this.last = node;
			this.size++;
		}
		
		//Delete methods
		@SuppressWarnings("unchecked")
		public void dequeue() {
			Node<?> next = this.first.getNext();
			
			this.first = next;
			this.size--;
		}
}
