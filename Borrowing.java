import java.util.ArrayList;

public class Borrowing {
    private String borrower;
    private ArrayList<Book> books;

    public Borrowing(String borrower){
        this.borrower = borrower;
        this.books = new ArrayList<>(); ;
    }

    public String getBorrower() {
        return borrower;
    }

    public void setBorrower(String borrower) {
        this.borrower = borrower;
    }

    public ArrayList<Book> getBooks() {
        return books;
    }
}
