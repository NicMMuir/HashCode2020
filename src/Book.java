import java.util.ArrayList;

public class Book implements Comparable<Book> {

    public int id;
    public int score;
    public boolean scanned = false;
    public ArrayList<Integer> libraryIds;

    public Book(int id, int score) {
        this.id = id;
        this.score = score;

        this.libraryIds = new ArrayList<>(1024);
    }

    @Override
    public int compareTo(Book o) {
        if(this.score > o.score) {
            return -1;
        } else if (this.score == o.score) {
            return 0;
        }
        return 1;
    }

    @Override
    public String toString() {
        return id + ": " + score;
    }
}
