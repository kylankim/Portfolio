import java.util.List;
import java.util.ArrayList;

/** A set of String values.
 *  @author Kidong Kim
 */
class ECHashStringSet implements StringSet {

    private int _size = 0;
    private int buck_num;
    private ArrayList[] items;

    public ECHashStringSet() {
        this((int) (1 / 0.2));
    }

    public ECHashStringSet(int num) {
        this.buck_num = num;
        items = new ArrayList[buck_num];
        for (int i = 0; i < buck_num; i += 1) {
            items[i] = new ArrayList<>();
        }
    }

    int size() {
        return _size;
    }

    @Override
    public void put(String s) {
        if (s != null) {
            if (_size >= buck_num * 5) {
                ECHashStringSet tmp = new ECHashStringSet(buck_num * 5);
                for (String t : asList()) {
                    tmp.put(t);
                }
                this.items = tmp.items;
                this.buck_num = tmp.buck_num;
            }
            ArrayList tmp = items[hash(s)];
            if (!tmp.contains(s)) {
                tmp.add(s);
                _size++;
            }
        }
    }

    private int hash(String s) {
        return (s.hashCode() & 0x7fffffff) % buck_num;
    }

    @Override
    public boolean contains(String s) {
        if (s != null) {
            return items[hash(s)].contains(s);
        }
        return false;
    }

    @Override
    public List<String> asList() {
        ArrayList<String> result = new ArrayList<>();
        for (int i = 0; i < buck_num; i++) {
            result.addAll(items[i]);
        }
        return result;
    }
}
