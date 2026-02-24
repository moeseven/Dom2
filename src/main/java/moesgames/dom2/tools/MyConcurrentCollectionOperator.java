package moesgames.dom2.tools;

import java.util.*;
/**
 * 
 * @author moritz.schick
 * 
 * this class can modify the original collection without a concurrent modification exception
 * (by copying the collection)
 *
 * @param <C> collection type
 * @param <O> element type of collection
 */
public abstract class MyConcurrentCollectionOperator<C extends Collection,O>{
	
	
	public MyConcurrentCollectionOperator() {
		
	}
	
	public MyConcurrentCollectionOperator(int counter) {
		super();
		this.counter = counter;
	}
	
	public MyConcurrentCollectionOperator(boolean truthValue) {
		super();
		this.truthValue = truthValue;
	}
	
	public boolean truthValue;
	public int counter;
	public abstract void operate(O operand);
	
	public void operateCollection(C collection) {
		if(collection.size()>0) {
			ArrayList<O> list = new ArrayList<>();
			list.addAll(collection);
			list.forEach(element -> operate(element));
			list = null; //mark for garbage collection
		}
		
	}
}
