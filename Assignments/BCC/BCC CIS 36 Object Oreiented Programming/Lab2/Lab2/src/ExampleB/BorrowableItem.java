package ExampleB;
import java.util.Date;

public class BorrowableItem implements Borrowable{
    private String id;
    private Date borrowDate;
    private Date returnDate;


    @Override
    public void setBorrowerID(String s) {
        this.id = s;
    }

    @Override
    public void setBorrowDate(Date d) {
        this.borrowDate = d;
    }

    @Override
    public void setReturnDate(Date d) {
        this.returnDate = d;
    }

}
