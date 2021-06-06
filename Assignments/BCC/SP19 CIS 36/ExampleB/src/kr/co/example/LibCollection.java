package kr.co.example;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;

public class  LibCollection<E> {

	private Collection<E> colection;
	
	public LibCollection(int i) {
		switch(i) {
			case 0 :
				colection = new ArrayList<E>();
			case 1 :
				colection = new HashSet<E>();
		}
		
	}
	boolean add(E o) {
		return colection.add(o);
	}
	
	boolean  addAll(LibCollection<?> c) {
		return colection.addAll((Collection<? extends E>) c);
	}
	  
	boolean remove (Object o) {
		return colection.remove(o);
	}

	
	void clear() {
		colection.clear();
	}
	
	boolean removeAll(LibCollection<? super E> c) {
		return colection.removeAll((Collection<?>) c);
	}
	
	boolean retainAll(LibCollection<?> c){
		return colection.retainAll((Collection<?>) c);
	}
	
	boolean contains(Object o) {
		return colection.contains(o);
	}
	boolean containsAll(LibCollection<?> c) {
		return colection.containsAll((Collection<?>) c);
	}
	boolean isEmpty() {
		return colection.isEmpty();
	}
	
	int size() {
		return colection.size();
	}
	@SuppressWarnings("unchecked")
	Iterator<E>  getIterator(){
		return (Iterator<E>) colection.iterator();
	}
	Object[] toArray() {
		return colection.toArray();
	}
	<T> T[] toArray(T[] a) {
		return colection.toArray(a);
	}
	 
}
