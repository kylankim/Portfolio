package ExampleB;

//Iterator for the LibCollection
public interface Iterator<E> {
    E getNext();
    boolean hasNext();
    boolean isEmpty();
}
