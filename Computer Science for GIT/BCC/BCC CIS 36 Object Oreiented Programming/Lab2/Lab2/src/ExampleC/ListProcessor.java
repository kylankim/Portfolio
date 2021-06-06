package ExampleC;

import java.util.ArrayList;
import java.util.List;

public class ListProcessor {
    static Object x = 0;
    static Object y = 0;

    public static<T> List<T>  filter(List<T> a ,Predicate<T> p) {
        List<T> list = new ArrayList<T>();
        for (T b : a) {
            p.test(b);
            list.add(b);
        }
        return list;

    }

    public static<T> void  forEach(List<T> a, Consumer<T> c) {
        for (T b : a) {
            c.accept(b);
        }
    }

    public static<T, R> List<R> map(List<T> list, Function <T, R> f) {
        return null;
    }
}
