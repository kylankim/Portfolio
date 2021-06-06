import java.util.Iterator;
import utils.Filter;

/** A kind of Filter that lets through every other VALUE element of
 *  its input sequence, starting with the first.
 *  @author You
 */
class AlternatingFilter<Value> extends Filter<Value> {

    /**
     * A filter of values from INPUT that lets through every other
     * value.
     */
    AlternatingFilter(Iterator<Value> input) {
        super(input);
    }

    boolean valid = false;

    @Override
    protected boolean keep() {
        if (!valid) {
            valid = true;
        } else if (valid) {
            valid = false;
        }
        return valid;
    }
}
