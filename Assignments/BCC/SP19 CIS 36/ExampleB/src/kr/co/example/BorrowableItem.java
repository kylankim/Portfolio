package kr.co.example;

import java.util.Date;

public class BorrowableItem implements Borrowable{
	private String id;
	private Date borrowDate;
	private Date returnDate;
	@Override
	public void setBorrowerID(String s) {
		// TODO Auto-generated method stub
		this.id = s;
	}

	@Override
	public void setBorrowDate(Date d) {
		// TODO Auto-generated method stub
		this.borrowDate = d;
	}

	@Override
	public void setReturnDate(Date d) {
		// TODO Auto-generated method stub
		this.returnDate = d;
	}

}
