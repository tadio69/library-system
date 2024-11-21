import java.util.Arrays;
import java.util.Collections;
import java.util.InputMismatchException;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

public class MaClassePrincipale {
  public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);
    String continuer = "o";
    Book[] books = new Book[0];
    ActivityStack recentActivities = new ActivityStack();
    BorrowingHistory borrowingHistory = new BorrowingHistory();
    boolean testTriParTitre = false;// égal à true si les livres sont triés suivant le titre
    while (continuer.equals("o")) {
      clearConsole();
      afficherMenuGeneral();
      int menuGeneral = lireNombre(1, 10, scanner);
      switch (menuGeneral) {
        case 1: //Ajout d'un livre
              books = ajouterLivre(books, scanner);
              testTriParTitre = false;// chaque fois qu'on ajoute un nouveau livre books devient non trié
              recentActivities.push("Ajout d'un livre.");
              break;
        case 2: //Modification d'un livre
              if(books.length > 0){
                String str = lireNom(scanner, "titre complet");
                int idx = linearSearch(books, str);
                if (idx != -1) {
                  System.out.print("Voulez-vous vraiment modifier le livre de titre "+ '"'+ str +'"'+" (o/n)? ");
                  String reponse = scanner.nextLine();
                  while (!(reponse.equals("o") || reponse.equals("n"))) {
                    System.out.println("Mauvaise réponse: Veuillez entrer o ou n svp.");
                    System.out.print("Voulez-vous vraiment modifier le livre de titre "+ '"'+ str +'"'+" (o/n)? ");
                    reponse = scanner.nextLine();
                  }
                  if(reponse.equals("o")){
                    books = updateBook(books, idx, scanner);
                    recentActivities.push("Modification d'un livre.");
                  } 
                }else{
                  System.out.println("Aucun livre ne correspond au titre : " +'"'+ str +'"');
                }
              }else{
                System.out.println("Il n'y a aucun livre enregisté. Veuillez d'abord ajouter des livres.");
              }     
              break;
        case 3: //Suppression d'un livre
              if(books.length > 0){
                String str = lireNom(scanner, "titre complet");
                int idx = linearSearch(books, str);
                if (idx != -1) {
                  System.out.print("Voulez-vous vraiment supprimer le livre de titre "+ '"'+ str +'"'+" (o/n)? ");
                  String reponse = scanner.nextLine();
                  while (!(reponse.equals("o") || reponse.equals("n"))) {
                    System.out.println("Mauvaise réponse: Veuillez entrer o ou n svp.");
                    System.out.print("Voulez-vous vraiment supprimer le livre de titre "+ '"'+ str +'"'+" (o/n)? ");
                    reponse = scanner.nextLine();
                  }
                  if(reponse.equals("o")){
                    books = deleteBook(books, idx);
                    recentActivities.push("Suppression d'un livre.");
                  } 
                }else{
                  System.out.println("Aucun livre ne correspond au titre : " +'"'+ str +'"');
                }               
              }else{
                System.out.println("Il n'y a aucun livre enregisté. Veuillez d'abord ajouter des livres.");
              } 
              break;
        case 4: //Affichage de la liste des livres
              if(books.length > 0){
                afficherTousLivres(books);
                recentActivities.push("Affichage de la liste des livres.");
              }else{
                System.out.println("Il n'y a aucun livre enregisté. Veuillez d'abord ajouter des livres.");
              }           
              break;
        case 5: 
              if(books.length > 0){
                String borrowerName = lireNom(scanner, "nom de l'emprunteur");
                String titre = lireNom(scanner, "titre complet");
                int idx = linearSearch(books, titre);
                if (idx != -1){
                  borrowingHistory.addBorrowing(borrowerName, books[idx]);
                  recentActivities.push("Emprunt d'un livre.");
                  System.out.println("Emprunt enregistré avec succès.");
                }else{
                  System.out.println("Aucun livre ne correspond au titre : " +'"'+ titre +'"');
                } 
              }else{
                System.out.println("Il n'y a aucun livre enregisté. Veuillez d'abord ajouter des livres.");
              } 
              break;
        case 6: //Suppression emprunt
              if(books.length > 0){
                String borrowerName = lireNom(scanner, "nom de l'emprunteur");
                String titre = lireNom(scanner, "titre complet");
                int idx = linearSearch(books, titre);
                if (idx != -1){
                  if (borrowingHistory.isBorrower(borrowerName) && borrowingHistory.isBorrowed(books[idx])) {
                    borrowingHistory.removeBorrowing(borrowerName, books[idx]);
                    recentActivities.push("Suppression d'un emprunt.");
                    System.out.println("Emprunt supprimé avec succès.");
                  }else{                  
                    System.out.println("Désolé. Livre de titre "+'"'+titre+'"'+" et/ou emprunteur de nom "+'"'+borrowerName+'"'+" inconnus.");
                  }                
                }else{
                  System.out.println("Aucun livre ne correspond au titre : " +'"'+ titre +'"');
                } 
              }else{
                System.out.println("Il n'y a aucun livre enregisté. Veuillez d'abord ajouter des livres.");
              } 
              break;
        case 7: // Historique des emprunts
              if(books.length > 0){
                if (borrowingHistory.getHead() != null) {
                  afficherHistoriqueEmprunts(borrowingHistory);
                  recentActivities.push("Affichage de l'historique des emprunts.");
                }else{
                  System.out.println("Il n'y a aucun emprunt enregisté. Veuillez d'abord ajouter des emprunts.");
                }
              }else{
                System.out.println("Il n'y a aucun livre enregisté. Veuillez d'abord ajouter des livres.");
              } 
              break;
        case 8: // Tri des livres
              if(books.length > 0){
                System.out.println("Choisissez un algorithme de tri");
                afficherMenuAlgo_tri();
                int menuAlgo_tri = lireNombre(1, 3, scanner);
                System.out.println("Choisissez un critère de tri");
                afficherMenuCritere();
                int menuCritere = lireNombre(1, 3, scanner);
                switch (menuAlgo_tri) {
                  case 1:
                      books = bubbleSort(books, menuCritere);
                      if (menuCritere == 1)
                        testTriParTitre = true;// tri fait sur le titre du livre
                      recentActivities.push("Tri des livres suivant le Bubble Sort.");
                      break;
                  case 2:
                      books = selectionSort(books, menuCritere);
                      if (menuCritere == 1)
                        testTriParTitre = true;// tri fait sur le titre du livre
                      recentActivities.push("Tri des livres suivant le Selection Sort.");
                      break;
                  case 3:
                      books = quickSort(books, 0, books.length - 1, menuCritere);
                      System.out.println("Les livres ont été triés correctement avec le Quick Sort.");
                      if (menuCritere == 1)
                        testTriParTitre = true;// tri fait sur le titre du livre
                      recentActivities.push("Tri des livres suivant le Quick Sort.");
                      break;
                }
              }else{
                System.out.println("Il n'y a aucun livre enregisté. Veuillez d'abord ajouter des livres.");
              }           
              break;
        case 9: // Recherche de livre
              if(books.length > 0){
                System.out.println("Choisissez un algorithme de recherche");
                afficherMenuAlgo_recherche();
                int menuAlgo_recherche = lireNombre(1, 3, scanner);
                int idx = 0;
                switch (menuAlgo_recherche) {
                  case 1:
                      String critere = lireNom(scanner, "titre complet");
                      idx = linearSearch(books, critere);
                      recentActivities.push("Recherche de livre via Linear Search.");
                      if (idx != -1) {
                        System.out.println("Livre trouvé après Linear Search :");
                        System.out.println("-------------------------------------------------");
                        afficherLivre(books[idx]);
                      } else {
                        System.out.println("Aucun livre ne correspond au titre : " +'"'+ critere+'"');
                      }
                      break;
                  case 2:
                      if (!testTriParTitre) {
                        System.out.println(
                            "Les livres ne sont pas triés sur le titre. Veuillez le faire avant d'effectuer la recherche binaire.");
                      } else {
                        critere = lireNom(scanner, "titre complet");
                        idx = binarySearch(books, critere);
                        recentActivities.push("Recherche de livre via Binary Search.");
                        if (idx != -1) {
                          System.out.println("Livre trouvé après Binary Search :");
                          System.out.println("-------------------------------------------------");
                          afficherLivre(books[idx]);
                        } else {
                          System.out.println("Aucun livre ne correspond au titre : " +'"'+ critere+'"');
                        }
                      }
                      break;
                }
              }else{
                System.out.println("Il n'y a aucun livre enregisté. Veuillez d'abord ajouter des livres.");
              }          
              break;
        case 10: // Affichage des récentes activités
              displayRecentActivities(recentActivities);
              break;
      }

      System.out.print("Voulez-vous effectuer un autre choix (o/n)? ");

      continuer = scanner.nextLine();
      while (!(continuer.equals("o") || continuer.equals("n"))) {
        System.out.println("Mauvaise réponse: Veuillez entrer o ou n svp.");
        System.out.print("Voulez-vous effectuer un autre menu (o/n)? ");
        continuer = scanner.nextLine();
      }

    }
    scanner.close();
  }

  // Méthode permettant de lire l'année de publication entre 1900 et max à la
  // console
  public static int lireAnnee(int max, Scanner scan) {
    int n = 0;
    while (n < 1900 || n > max) {
      try {
        n = scan.nextInt();
        scan.nextLine(); // Consume newline after reading integer
      } catch (InputMismatchException e) {
        scan.nextLine(); // Consomme l'entrée invalide
      }
      if (n < 1900 || n > max) {
        System.out.println("Valeur rejetée. Vous devez saisir un nombre entre " + 1900 + " et " + max + ".");
        System.out.print("Année de publication: ");
      }
    }

    return n;
  }

  // Méthode permettant de lire un nombre entre 2 bornes à la console
  public static int lireNombre(int min, int max, Scanner scan) {
    int n = 0;
    while (n < min || n > max) {
      System.out.print("Quel est votre choix: ");
      try {
        n = scan.nextInt();
        scan.nextLine(); // Consume newline after reading integer
      } catch (InputMismatchException e) {
        scan.nextLine(); // Consomme l'entrée invalide
      }
      if (n < min || n > max) {
        System.out.println("Valeur rejetée. Vous devez saisir un nombre entre " + min + " et " + max + ".");
      }
    }

    return n;
  }

  // Méthode permettant de lire le titre ou le nom de l'emprunteur d'un livre à la console
  public static String lireNom(Scanner scan, String str) {
    System.out.println("Donnez le "+str+" du livre :");
    String ligne = scan.nextLine();
    while (ligne == null || ligne.length() < 2) {
      System.out.println("Le "+str+" du livre doit être fourni et contenir au moins 2 caractères.");
      System.out.println("Donnez le "+str+" du livre :");
      ligne = scan.nextLine();
    }
    return ligne;
  }

  // Méthode qui lit les données d'un livre à la console
  public static Book readBook(Scanner scan) {
    Book book = new Book();
    System.out.println("Titre du livre :");
    if (scan.hasNextLine()) {
      book.setTitle(scan.nextLine());
    }

    System.out.println("Auteur du livre :");
    if (scan.hasNextLine()) {
      book.setAuthor(scan.nextLine());
    }

    System.out.println("ISBN du livre :");
    if (scan.hasNextLine()) {
      book.setIsbn(scan.nextLine());
    }

    System.out.println("Année de publication: ");
    if (scan.hasNextLine()) {
      book.setPub_year(lireAnnee(2024, scan));
    }

    System.out.println("Genre du livre: ");
    if (scan.hasNextLine()) {
      book.setGenre(scan.nextLine());
    }

    return book;
  }

  // Méthode permettant d'ajouter un livre dans l'array des livres
  public static Book[] ajouterLivre(Book[] books, Scanner scan) {
    System.out.println("Entrer un livre");
    System.out.println("----------------------------------------------------");
    books = Arrays.copyOf(books, books.length + 1);// Modifie la taille du tableau books
    books[books.length - 1] = readBook(scan);// ajoute le livre lu à la console
    System.out.println("Un livre a été ajouté avec succès.");
    return books;
  }

  // Méthode permettant d'afficher un livre à la console
  public static void afficherLivre(Book book) {
    System.out.println("Titre: " + book.getTitle());
    System.out.println("Auteur: " + book.getAuthor());
    System.out.println("Isbn: " + book.getIsbn());
    System.out.println("Année de publication: " + book.getPub_year());
    System.out.println("Genre: " + book.getGenre());
  }

  //Méthode permettant de supprimer un livre
  public static Book[] deleteBook(Book[] books, int idx){
    Book[] copyBooks = new Book[books.length - 1];
    int i = 0;
    for (Book book2 : books) {
      if (!(book2.getTitle().equals(books[idx].getTitle()))) {
        copyBooks[i++] = book2; 
      }
    }
    System.out.println("Un livre a été supprimé avec succès.");
    return copyBooks;
  }

  //Méthode permettant de modifier un livre
  public static Book[] updateBook(Book[] books, int idx, Scanner scan){
    System.out.println("Anciennes données du livre à modifier :");
    System.out.println("----------------------------------------------");
    afficherLivre(books[idx]);
    Book newBook = readBook(scan);
    books[idx] = newBook;
    System.out.println("Un livre a été modifié avec succès.");
    return books;
  }

  // Méthode permettant d'afficher tous les livres à la console
  public static void afficherTousLivres(Book[] books) {
    int n = books.length;
    if (n == 0) {
      System.out.println("La liste des livres est vide.");
    } else {
      System.out.println("*************************************************************");
      System.out.println("********************* Liste des " + n + " livres ********************");
      System.out.println("*************************************************************");
      int i = 1;
      for (Book book : books) {
        System.out.println("Livre n°" + (i++));
        System.out.println("----------------------------------------------------");
        afficherLivre(book);
        System.out.println("\n");
      }
    }
  }

  // Méthode permettant d'afficher tous les emprunts
  public static void afficherHistoriqueEmprunts(BorrowingHistory borrowingHistory){
    List<Borrowing> borrowings = borrowingHistory.getAllBorrowings();
    System.out.println("**************************************************");
    System.out.println("************ Historique des emprunts *************");
    System.out.println("**************************************************");
    Iterator<Borrowing> iterator = borrowings.iterator();
    while (iterator.hasNext()) {
      Borrowing borrowing = iterator.next();
      System.out.println("Emprunt de "+borrowing.getBorrower());
      System.err.println("------------------------------------------------------------");
      int i = 1;
      for (Book book : borrowing.getBooks()) {
        System.out.println(i+"- "+book.getTitle());
        i++;
      }
    }   
  }

  // effacer la console
  public static void clearConsole() {
    System.out.print("\033[H\033[2J");
    System.out.flush();
  }

  // afficher le menu
  public static void afficherMenuGeneral() {
    System.out.println("**************************************************");
    System.out.println("*************          MENU          *************");
    System.out.println("**************************************************");
    String[] menu = {
        "1-  Ajouter un livre",
        "2-  Modifier un livre",
        "3-  Supprimer un livre",
        "4-  Afficher la liste des livres",
        "5-  Emprunter un livre",
        "6-  Supprimer un emprunt",
        "7-  Afficher l'historique des emprunts",
        "8-  Trier les livres par ordre croissant",
        "9-  Rechercher un livre",
        "10- Afficher la liste des récentes activités"
    };
    for (String str : menu) {
      System.out.println("         " + str + "          ");
    }
  }

  // afficher algorithmes de tri
  // si menu=8
  public static void afficherMenuAlgo_tri() {
    String[] algo = {
        "1- Bubble sort",
        "2- Selection sort",
        "3- quicksort"
    };
    for (String str : algo) {
      System.out.println("         " + str + "          ");
    }
  }

  // afficher le critère de tri ou de recherche
  // si menu=8 ou 9
  public static void afficherMenuCritere() {
    String[] critere = {
        "1- Par titre de livre",
        "2- Par isbn",
        "3- Par auteur"
    };
    for (String str : critere) {
      System.out.println("         " + str + "          ");
    }
  }

  // afficher les algorithmes de recherche
  // si menu=9
  public static void afficherMenuAlgo_recherche() {
    String[] algo = {
        "1- Linear search",
        "2- Binear search"
    };
    for (String str : algo) {
      System.out.println("         " + str + "          ");
    }
  }

  // Méthode permettant de trier les livres suivant le Bubble Sort
  public static Book[] bubbleSort(Book[] books, int choix) {
    int n = books.length;
    if (choix == 1) {// tri bulle suivant le titre du livre
      for (int i = 0; i < n - 1; i++) {
        for (int j = 0; j < n - i - 1; j++) {
          if (books[j].getTitle().compareTo(books[j + 1].getTitle()) > 0) {
            // Échanger les éléments
            Book temp = books[j];
            books[j] = books[j + 1];
            books[j + 1] = temp;
          }
        }
      }
    } else if (choix == 2) {// tri bulle suivant l'isbn du livre
      for (int i = 0; i < n - 1; i++) {
        for (int j = 0; j < n - i - 1; j++) {
          if (books[j].getIsbn().compareTo(books[j + 1].getIsbn()) > 0) {
            // Échanger les éléments
            Book temp = books[j];
            books[j] = books[j + 1];
            books[j + 1] = temp;
          }
        }
      }
    } else {// tri bulle suivant l'auteur du livre
      for (int i = 0; i < n - 1; i++) {
        for (int j = 0; j < n - i - 1; j++) {
          if (books[j].getAuthor().compareTo(books[j + 1].getAuthor()) > 0) {
            // Échanger les éléments
            Book temp = books[j];
            books[j] = books[j + 1];
            books[j + 1] = temp;
          }
        }
      }
    }
    System.out.println("Les livres ont été triés correctement avec le Bubble Sort.");
    return books;
  }

  // Méthode permettant de trier les livres suivant le Selection Sort
  public static Book[] selectionSort(Book[] books, int choix) {
    int n = books.length;

    if (choix == 1) {// tri par sélection suivant le titre du livre

      // Parcourt toutes les positions à trier
      for (int i = 0; i < n - 1; i++) {
        int min_idx = i;

        // Trouve l'index du minimum dans le sous-tableau non trié
        for (int j = i + 1; j < n; j++)
          if (books[j].getTitle().compareTo(books[min_idx].getTitle()) < 0)
            min_idx = j;

        // Échange le minimum trouvé avec le premier élément
        Book temp = books[min_idx];
        books[min_idx] = books[i];
        books[i] = temp;
      }
    } else if (choix == 2) {// tri par sélection suivant l'isbn du livre

      // Parcourt toutes les positions à trier
      for (int i = 0; i < n - 1; i++) {
        int min_idx = i;

        // Trouve l'index du minimum dans le sous-tableau non trié
        for (int j = i + 1; j < n; j++)
          if (books[j].getIsbn().compareTo(books[min_idx].getIsbn()) < 0)
            min_idx = j;

        // Échange le minimum trouvé avec le premier élément
        Book temp = books[min_idx];
        books[min_idx] = books[i];
        books[i] = temp;
      }
    } else {// tri par sélection suivant l'auteur du livre

      // Parcourt toutes les positions à trier
      for (int i = 0; i < n - 1; i++) {
        int min_idx = i;

        // Trouve l'index du minimum dans le sous-tableau non trié
        for (int j = i + 1; j < n; j++)
          if (books[j].getAuthor().compareTo(books[min_idx].getAuthor()) < 0)
            min_idx = j;

        // Échange le minimum trouvé avec le premier élément
        Book temp = books[min_idx];
        books[min_idx] = books[i];
        books[i] = temp;
      }
    }
    System.out.println("Les livres ont été triés correctement avec le Selection Sort.");
    return books;
  }

  // Méthode permettant de trier les livres suivant le Quick Sort
  public static Book[] quickSort(Book[] books, int debut, int fin, int choix) {
    if (debut < fin) {
      int pivotIndex = partition(books, debut, fin, choix);
      books = quickSort(books, debut, pivotIndex - 1, choix);
      books = quickSort(books, pivotIndex + 1, fin, choix);
    }
    return books;
  }

  public static int partition(Book[] books, int debut, int fin, int choix) {
    Book pivot = books[fin];
    int i = debut - 1;

    if (choix == 1) {// cas choix=titre du livre
      for (int j = debut; j < fin; j++) {
        if (books[j].getTitle().compareTo(pivot.getTitle()) <= 0) {
          i++;
          Book temp = books[i];
          books[i] = books[j];
          books[j] = temp;
        }
      }
    } else if (choix == 2) {// cas choix=isbn du livre
      for (int j = debut; j < fin; j++) {
        if (books[j].getIsbn().compareTo(pivot.getIsbn()) <= 0) {
          i++;
          Book temp = books[i];
          books[i] = books[j];
          books[j] = temp;
        }
      }
    } else {// cas choix=auteur du livre
      for (int j = debut; j < fin; j++) {
        if (books[j].getAuthor().compareTo(pivot.getAuthor()) <= 0) {
          i++;
          Book temp = books[i];
          books[i] = books[j];
          books[j] = temp;
        }
      }
    }

    Book temp = books[i + 1];
    books[i + 1] = books[fin];
    books[fin] = temp;
    return i + 1;
  }

  // Méthode d'affichage des récentes activités
  public static void displayRecentActivities(ActivityStack recentActivities) {
    int n = recentActivities.getStack().size();
    if (n == 0) {
      System.out.println("La liste des récentes activités est vide.");
    } else {
      System.out.println("**************************************************");
      System.out.println("********** Liste des récentes activités **********");
      System.out.println("**************************************************");
      Collections.reverse(recentActivities.getStack());// inverse l'ordre d'entrée des activités
      int i = 1;
      while (!recentActivities.getStack().isEmpty()) {
        System.out.println(i + " : " + recentActivities.pop());
        i++;
      }
    }
  }

  // recherche linéaire de livre suivant le titre du livre
  public static int linearSearch(Book[] books, String critere) {
    int n = books.length;
    
    for (int i = 0; i < n; i++) {
      if (books[i].getTitle().equals(critere)) {
        return i; 
      }
    }
    return -1; 
  }

  // recherche binaire de livre suivant le titre du livre
  public static int binarySearch(Book[] books, String critere) {
    int left = 0, right = books.length - 1;

    while (left <= right) {
      int mid = left + (right - left) / 2;

      int cmp = books[mid].getTitle().compareTo(critere);

      if (cmp == 0) {
        return mid; // le livre est trouvé
      } else if (cmp < 0) {
        left = mid + 1; // le livre recherché est dans le sous-tableau droit
      } else {
        right = mid - 1; // le livre recherché est dans le sous-tableau gauche
      }
    }

    return -1; // livre non trouvé 
  }

}