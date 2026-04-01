package estruc_datos;

@SuppressWarnings("hiding")
public class StackList<Object> extends List<Object> {
	
	public StackList(Object data) {
		super(data);
		// TODO Auto-generated constructor stub
	}

	public StackList() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	//Get methods:
	@SuppressWarnings("unchecked")
	public Object top(){
		/*
		Node previous = this.getNode(this.size-2);
		Node top  = this.last;
		previous.setNext(null);
		this.last = previous;
		this.size--;
		
		return top.getData();
		*/
		return (Object) this.last.getData();
	}
	
	//Insert methods:
	public void push(Object data) {
		Node<Object> node = new Node<>(data);
		this.last.setNext(node);
		this.last = node;
		this.size++;
	}
	
	//Delete methods
	@SuppressWarnings("unchecked")
	public void pop() {
		Node<Object> previous = this.getNode(this.size-2);
		previous.setNext(null);
		this.last = previous;
		this.size--;
	}

}
