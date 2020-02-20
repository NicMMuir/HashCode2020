import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

public class Main {

    public static int bookCount;
    public static Book[] books;

    public static void main(String[] args) throws Exception {
        String[] filenames = {
            "a_example.txt", "b_read_on.txt", "c_incunabula.txt", "e_so_many_books.txt", "f_libraries_of_the_world.txt"
        };

        for(int index = 0; index < filenames.length; index++) {
            String inputFile = filenames[index];

            BufferedReader reader = new BufferedReader(new FileReader(new File(inputFile)));
            BufferedWriter writer = new BufferedWriter(new FileWriter(new File(inputFile + ".out")));

            String[] data = reader.readLine().split(" ");
            bookCount = Integer.parseInt(data[0]);
            int libraryCount = Integer.parseInt(data[1]);
            int dayCount = Integer.parseInt(data[2]);

            data = reader.readLine().split(" ");
            books = new Book[data.length];

            for (int i = 0; i < data.length; i++) {
                books[i] = new Book(i, Integer.parseInt(data[i]));
            }

            Library[] libraries = new Library[libraryCount];
            for (int i = 0; i < libraryCount; i++) {
                data = reader.readLine().split(" ");

                int libraryBookCount = Integer.parseInt(data[0]);
                int signupDays = Integer.parseInt(data[1]);
                int booksPerDay = Integer.parseInt(data[2]);

                data = reader.readLine().split(" ");
                Book[] libBooks = new Book[libraryBookCount];

                for (int j = 0; j < data.length; j++) {
                    int bookId = Integer.parseInt(data[j]);
                    libBooks[j] = books[bookId];
                    books[bookId].libraryIds.add(i);
                }
                Arrays.sort(libBooks);

                libraries[i] = new Library(i, libBooks, signupDays, booksPerDay);
            }

            int signupDaysLeft = 0;
            ArrayList<Library> signedUpLibraries = new ArrayList<>(10000);

            for (int day = 0; day < dayCount; day++) {
                signupDaysLeft--;
                if (signupDaysLeft > 0) {
                    continue;
                }

                Library maxGainLibrary = null;

                for (int j = 0; j < libraries.length; j++) {
                    if (libraries[j].signedUp) {
                        continue;
                    }

                    libraries[j].calculateGain(dayCount - day);

                    if (maxGainLibrary == null || libraries[j].maxGain > maxGainLibrary.maxGain) {
                        maxGainLibrary = libraries[j];
                    }
                }

                if (maxGainLibrary == null) {
                    continue;
                }

                signupDaysLeft = maxGainLibrary.signupDays;
                maxGainLibrary.signUp(dayCount - day);
                signedUpLibraries.add(maxGainLibrary);
            }

            int usedLibraries = 0;
            for(Library lib : signedUpLibraries) {
                if(lib.scannedBooks > 0) {
                    usedLibraries++;
                }
            }

            writer.write("" + usedLibraries);
            writer.newLine();

            for (Library library : signedUpLibraries) {
                if (library.scannedBooks == 0) {
                    continue;
                }

                writer.write(library.id + " " + library.scannedBooks);
                writer.newLine();

                writer.write(library.bookString);
                writer.newLine();
            }

            writer.close();
            reader.close();

            System.out.println(inputFile + " finished!");
        }
    }

}
