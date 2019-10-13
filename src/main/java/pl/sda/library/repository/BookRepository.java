package pl.sda.library.repository;

import org.springframework.stereotype.Repository;
import pl.sda.library.model.Book;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Repository
public class BookRepository {

    private Set<Book> books = initialize();

    private Set<Book> initialize() {
        return new HashSet<>(Arrays.asList(new Book(1, "Testy", "Kaczanowski"),
            new Book(2, "Dzuma", "Camus"),
            new Book(3, "W pustyni i w puszczy", "Sienkiewicz"),
            new Book(4, "Pan Tadeusz", "Mickiewicz"),
            new Book(5, "Dziady", "Mickiewicz"),
            new Book(6, "Anioly i demony", "Brown"),
            new Book(7, "Gra o tron", "Martin"),
            new Book(8, "Harry Potter", "Rowling"),
            new Book(9, "Testy", "Kaczanowski"),
            new Book(10, "Testy", "Kaczanowski")));
    }

    public Optional<Book> borrowBook(String title, LocalDate borrowedTill) {
//        for (Book book : books) {alternatywa
//            if (title.equals(book.getTitle())){
//                if (book.getBorrowedTill() == null) {
//                    book.setBorrowedTill(borrowedTill);
//                    return Optional.of(book);
//                }
//            }
//        }
        Optional<Book> any = books.stream()
            .filter(book -> title.equals(book.getTitle()))
            .filter(book -> book.getBorrowedTill() == null).findAny();
        any.ifPresent(book -> book.setBorrowedTill(borrowedTill));
        return any;
    }

    public void returnBook(int bookId) {
//        for (Book book : books) {alternatywa
//            if (book.getId() == bookId) {
//                book.setBorrowedTill(null);
//            }
//        }
//        throw new RuntimeException("Book not found");
        books.stream()
            .filter(book -> book.getId() == bookId)
            .findFirst()
            .orElseThrow(() -> new RuntimeException("Book not found"))
            .setBorrowedTill(null);
    }

    public Book addBook(Book bookToAdd) {
        bookToAdd.setId(generateNextId());
        books.add(bookToAdd);
        return bookToAdd;
    }

    public void removeBook(int id) {
        Book bookToRemove = books.stream()
            .filter(book -> id == book.getId())
            .findFirst()
            .orElseThrow(() -> new RuntimeException("Book not found"));
        books.remove(bookToRemove);
    }

    public Set<Book> getBooks() {
        return books;
    }

    private int generateNextId() {
//        int max = 0;
//        for(Book book : books) {
//            if (book.getId() > max) {
//                max = book.getId();
//            }
//        }
//        return max + 1;
        return books.stream()
            .mapToInt(Book::getId)
            .max().getAsInt() + 1;
    }
}














