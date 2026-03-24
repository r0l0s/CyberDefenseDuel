package estruc_datos;

public class Queue {
	private int[] queue;
	private int peek;
	private int index=0;
	
	public Queue(int data,int size){
		this.peek = size;
		this.queue = new int[size];
		
		this.queue[0] = data;
	}
	
	public void enqueue(int data) {
		this.index++;
		if (this.index == this.peek) {
			throw new IndexOutOfBoundsException("Full queue, doesnt accept any other element");
		}
		this.queue[this.index] = data;
	}
	
	public void dequeue() {
		for (int i=0;i<this.peek-1;i++) {
			this.queue[i] = this.queue[i+1];
		}
		this.index--;
	}
	
	public int peek(){
		return this.queue[0];
	}
	
}
