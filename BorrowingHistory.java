import java.util.ArrayList;
import java.util.List;

public class BorrowingHistory {

  private class Node {
    Borrowing borrowing;
    Node next;

    Node(){} 
  }

  private Node head;

  public BorrowingHistory(){}

  public Node getHead() {
      return head;
  }

  public void addBorrowing(String borrower, Book book){
    if(head == null){//le linkedlist est vide
      head = new Node();
      head.borrowing = new Borrowing(borrower);
      head.borrowing.getBooks().add(book);
    } else {
      Node current = head;
      while(current != null){
        if (current.borrowing.getBorrower().equals(borrower)) {
          current.borrowing.getBooks().add(book);
          return;//on sort de addBorrowing
        } 
        current = current.next; 
      }
      //borrower n'est pas déjà dans le linkedlist 
      Node newNode = new Node();
      newNode.borrowing = new Borrowing(borrower);
      newNode.borrowing.getBooks().add(book);
      newNode.next = head;
      head = newNode;
    }
  }

  public void removeBorrowing(String borrower, Book book){
    if(head != null){
      Node current = head;
      while (current != null){
        if (current.borrowing.getBorrower().equals(borrower) && current.borrowing.getBooks().contains(book)) {
          current.borrowing.getBooks().remove(book);
        }
        current = current.next;
      }
    } 
  }

  // test si emprunteur
  public boolean isBorrower(String borrower){
    boolean result = false;
    if(head != null){
      Node current = head;
      while (current != null) {
        if (current.borrowing.getBorrower().equals(borrower)) {
          result = true;
          break;
        }
        current = current.next;
      }
    }
    return result;
  }

  // test si livre déjà emprunté
  public boolean isBorrowed(Book book){
    boolean result = false;
    if(head != null){
      Node current = head;
      while (current != null) {
        if (current.borrowing.getBooks().contains(book)) {
          result = true;
          break;
        }
        current = current.next;
      }
    }
    return result;
  }

  public List<Borrowing> getAllBorrowings(){
    List<Borrowing> borrowings = new ArrayList<>();
    Node current = head;
    while (current != null){
      borrowings.add(current.borrowing);
      current = current.next;
    }
    return borrowings;
  }
}
