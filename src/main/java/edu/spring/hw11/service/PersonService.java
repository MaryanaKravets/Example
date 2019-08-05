package edu.spring.hw11.service;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PersonService implements IPersonService {
    private List<Book> bookList = new ArrayList<>(Arrays.asList(
            new Book(1, "Философия Java", "Классика computer science", "ssd", 5),
            new Book(2, "Java Core", "sdf", "fff", 4),
            new Book(3, "ProGit", "sddd", "sdfff", 3),
            new Book(4, "Spring in action", "sddf", "sdff", 4),
            new Book(5, "sdddf", "sddf", "sdff", 3)
    ));
    private List<Author> authorList = new ArrayList<>(Arrays.asList(
            new Author(1, "Брюс", "Эккель",
                    bookList.get(0)),
            new Author(2, "Say", "Horstmann",
                    bookList.get(1)),
            new Author(3, "Scott", "Chacon",
                    bookList.get(2)),
            new Author(4, "Crag", "Walls",
                    bookList.get(3)),
            new Author(5, "ccv", "df",
                    bookList.get(4))
    ));

    public PersonService() {
    }


    @Override
    public List<Author> addAuthor(int id, String fName, String lName) {
        Author author = new Author(id, fName, lName);
        authorList.add(author);
        return authorList;
    }

    @Override
    public List<Author> deleteAuthor(int id) {
        authorList.removeIf(a -> a.getId() == id);
        return authorList;
    }

    @Override
    public List<Book> addBook(int id, String title, String genre, String description, int rate) {
        Book book = new Book(id, title, genre, description, rate);
        bookList.add(book);
        return bookList;
    }

    @Override
    public List<Book> deleteBook(int id) {
        bookList.removeIf(b -> b.getId() == id);
        return bookList;
    }

    @Override
    public void addBookForAuthor(int authorId, Book book) {
        authorList.stream()
                .filter(a -> a.getId() == authorId)
                .findAny()
                .orElseThrow(() -> new RuntimeException("Item not found"))
                .setBook(book);
    }

    @Override
    public List<Book> sortedBookByAuthor(int authorId) {
        return authorList.stream()
                .filter(a -> a.getId() == authorId)
                .map(Author::getBook)
                .collect(Collectors.toList());
        // .findAny()
        //  .orElseThrow( NotFoundExceptions::new);
    }

    @Override
    public List<Book> sortedBookByGenre(String genre) {
        return bookList.stream()
                .filter(b -> b.getGenre().equals(genre))
                .collect(Collectors.toList());
    }

    @Override
    public Book updateBook(int bookId, int updateBookId) {
        bookList.stream()
                .filter(b -> b.getId() == bookId)
                .forEach(b -> b.setId(updateBookId));
        return bookList.stream().filter(b -> b.getId() == updateBookId)
                .findAny().orElseThrow(NotFoundExceptions::new);
    }

    @Override
    public Author updateAuthor(int authorId, int updateAuthorId) {
        authorList.stream()
                .filter(a -> a.getId() == authorId)
                .forEach(b -> b.setId(updateAuthorId));
        return authorList.stream().filter(a -> a.getId() == updateAuthorId)
                .findAny().orElseThrow(NotFoundExceptions::new);
    }
}