/**
 * TableFilter to filter for entries whose two columns match.
 *
 * @author Matthew Owen
 */
public class ColumnMatchFilter extends TableFilter {

    public ColumnMatchFilter(Table joined, String s, String s1) {
        super(joined);
        this._s = s;
        this._s1 = s1;
        System.out.println(_s);
        System.out.println(_s1);
    }

    @Override
    protected boolean keep() {

        System.out.println(_next.toString());
        System.out.println();

        for (int i = 0; i < _next.size(); i++) {
            System.out.println();
            System.out.println(_next.toString());

            if (_next.getValue(i).equals(_s) && _next.getValue(i).equals(_s)) {
                return true;
            }
        }
        return false;
    }

    private String _s;
    private String _s1;
    // FIXME: Add instance variables?
}
