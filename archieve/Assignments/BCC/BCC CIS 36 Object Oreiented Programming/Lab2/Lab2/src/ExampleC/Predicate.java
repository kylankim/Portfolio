package ExampleC;

@FunctionalInterface
public interface Predicate<T> {
    boolean test(T t);
}
