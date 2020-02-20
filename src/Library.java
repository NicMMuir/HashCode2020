public class Library {

    public int id;
    public Book[] books;
    public int signupDays;
    public int booksPerDay;
    public boolean signedUp = false;

    public int scannedBooks = 0;
    public String bookString = "";

    public int maxGain = 0;

    public Library(int id, Book[] books, int signupDays, int booksPerDay) {
        this.id = id;
        this.books = books;
        this.signupDays = signupDays;
        this.booksPerDay = booksPerDay;
    }

    public void calculateGain(int daysLeft) {
        int booksToScan = (daysLeft - signupDays) * booksPerDay;
        int score = 0;

        for(Book book : books) {
            if(booksToScan <= 0) {
                break;
            }

            if(book.scanned) {
                continue;
            }

            score += book.score;
            booksToScan--;
        }

        maxGain = score;
    }

    public void signUp(int daysLeft) {
        int booksToScan = (daysLeft - signupDays) * booksPerDay;
        int scannedBooks = 0;
        StringBuilder scanBuilder = new StringBuilder();

        for(Book book : books) {
            if(booksToScan <= 0) {
                break;
            }

            if(book.scanned) {
                continue;
            }

            scannedBooks++;
            booksToScan--;
            scanBuilder.append(book.id).append(' ');
            book.scanned = true;
        }

        this.scannedBooks = scannedBooks;
        this.signedUp = true;
        bookString = scanBuilder.toString();
    }

    @Override
    public String toString() {
        return id + ": has " + books.length + " takes " + signupDays + " but gives " + maxGain + " from " + bookString;
    }

}
