

public class Book {
  private String title;
  private String author;
  private String isbn;
  private int pub_year;
  private String genre;

  public Book(){};
  public Book(String title, String author, String isbn, int pub_year, String genre) {
    this.title = title;
    this.author = author;
    this.isbn = isbn;
    this.pub_year = pub_year;
    this.genre = genre;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getAuthor() {
    return author;
  }

  public void setAuthor(String author) {
    this.author = author;
  }

  public String getIsbn() {
    return isbn;
  }

  public void setIsbn(String isbn) {
    this.isbn = isbn;
  }

  public int getPub_year() {
    return pub_year;
  }

  public void setPub_year(int pub_year) {
    if (isValidYear(pub_year)) {
        this.pub_year = pub_year;
    } else {
        throw new IllegalArgumentException("Année de publication invalide : " + pub_year);
    }
}

private boolean isValidYear(int year) {
    return year >= 0 && String.valueOf(year).length() == 4;
}

  public String getGenre() {
    return genre;
  }

  public void setGenre(String genre) {
    this.genre = genre;
  }

}
