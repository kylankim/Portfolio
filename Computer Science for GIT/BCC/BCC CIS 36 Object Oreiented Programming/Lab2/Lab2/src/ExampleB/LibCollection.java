package ExampleB;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

public class  LibCollection<E> {

    //Where the data of the objects are stored
    private Collection<E> colection;

    //Defining the data structure according to the case when the collection is constructed
    public LibCollection(int i) {
        switch(i) {
            case 0 :
                colection = new ArrayList<E>();
            case 1 :
                colection = new HashSet<E>();
        }

    }

    //Adding an item into the collection
    boolean add(E o) {
        return colection.add(o);
    }

    //Adding multiple items into the collection
    boolean  addAll(LibCollection<?> c) {
        return colection.addAll((Collection<? extends E>) c);
    }

    //Remove an item from the collection
    boolean remove (Object o) {
        return colection.remove(o);
    }

    //Clearing all items from the collection
    void clear() {
        colection.clear();
    }

    //Remove all Items from the collection where data has been transferred
    boolean removeAll(LibCollection<? super E> c) {
        return colection.removeAll((Collection<?>) c);
    }

    //Check all items from the collection provided are in both collections
    boolean retainAll(LibCollection<?> c){
        return colection.retainAll((Collection<?>) c);
    }

    //Checks whether the item is in the collection
    boolean contains(Object o) {
        return colection.contains(o);
    }

    //Checks whether all items are in the collection
    boolean containsAll(LibCollection<?> c) {
        return colection.containsAll((Collection<?>) c);
    }

    //Checks whether the collection is empty
    boolean isEmpty() {
        return colection.isEmpty();
    }

    //Returns the size of the collection
    int size() {
        return colection.size();
    }

    //Returns an iterator
    Iterator<E>  getIterator(){
        return (Iterator<E>) colection.iterator();
    }

    //Returns data of the collection into array type into object type.
    Object[] toArray() {
        return colection.toArray();
    }

    //Returns data of the collection into array type into provided object type.
    <T> T[] toArray(T[] a) {
        return colection.toArray(a);
    }

}
