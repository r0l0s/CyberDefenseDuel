package estruc_datos;

public class Stack {
	private int[] stack;
	private int top;
	private int index=0;
	
	public Stack(int data,int size){
		this.top = size;
		this.stack = new int[size];
		
		this.stack[0] = data;
		index++;
	}
	
	public Stack(int size){
		this.top = size;
		this.stack = new int[size];
	}

	public void push(int data) {
		this.index++;
		if (this.index == this.top) {
			throw new IndexOutOfBoundsException("Full stack, doesnt accept any other element");
		}
		this.stack[this.index] = data;
	}
	
	public void pop() {
		this.index--;
	}
	
	public int top(){
		return this.stack[this.index];
	}
	
	
}
