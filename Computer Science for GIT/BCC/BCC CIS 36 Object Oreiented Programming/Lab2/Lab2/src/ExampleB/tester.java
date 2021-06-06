package ExampleB;


import java.util.Date;

public class tester{
    private static LibCollection<BorrowableItem> c,c_2;

    //Tests whether the class works or not
    public static void main(String args[]){

        c = new LibCollection<BorrowableItem>(0);
        c_2 = new LibCollection<BorrowableItem>(0);
        long monthTimeStamp = 1000 * 60 * 60 * 24 * 30;
        Date today = new Date();
        Date afterMonth = new Date();
        afterMonth.setTime(today.getTime() + monthTimeStamp);

        BorrowableItem book1 = new Book();
        book1.setBorrowerID("This is Book1");

        book1.setBorrowDate(today);
        book1.setReturnDate(afterMonth);

        BorrowableItem dvd1 = new DVD();
        dvd1.setBorrowerID("This is dvd1");
        dvd1.setBorrowDate(today);
        dvd1.setReturnDate(afterMonth);

        BorrowableItem book2 = new Book();
        book1.setBorrowerID("This is Book2");

        c.add(book1);
        System.out.println("Add book1 \nsize :"+c.size());
        c.add(dvd1);
        System.out.println("Add dvd1 \nsize :"+c.size());

        ckEmpty();

        c.clear();
        System.out.println("Clear");
        ckEmpty();

        c.add(book1);
        System.out.println("Add book1 \nsize :"+c.size());
        c.add(book2);
        System.out.println("Add book2 \nsize :"+c.size());

        if(c.contains(book1))System.out.println("have book1");
        else System.out.println("not have book1");

        c.remove(book1);
        System.out.println("Remove book1 \nsize :"+c.size());
        if(c.contains(book1))System.out.println("have book1");
        else System.out.println("not have book1");

        c_2.add(book2);
        c_2.add(dvd1);
        if(c.contains(c_2))System.out.println("haves dvd1,book2");
        else System.out.println("not haves dvd1 , book1");

        c.clear();
        ckEmpty();
    }

    //Checks whether the collection is empty or not
    public static void ckEmpty() {
        if(c.isEmpty())System.out.println("collection is empty");
        else System.out.println("collection is not empty");
    }

}
