/**
 * TableFilter to filter for entries equal to a given string.
 *
 * @author Matthew Owen
 */
public class EqualityFilter extends TableFilter {

    public EqualityFilter(Table input, String colName, String match) {
        super(input);
        this._colname = colName;
        this._match = match;
        // FIXME: Add your code here.

    }

    @Override
    protected boolean keep() {
        // FIXME: Replace this line with your code.
        return false;
    }

    private String _colname;
    private  String _match;
    // FIXME: Add instance variables?
}
