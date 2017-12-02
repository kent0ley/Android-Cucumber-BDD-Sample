package com.codemate.booklibrary.ui;

/**
 * Created by ktoley on 11/29/17.
 */

import com.codemate.booklibrary.data.Book;
import com.codemate.booklibrary.data.Library;
import com.codemate.booklibrary.data.RandomBookGenerator;
import com.greghaskins.spectrum.Spectrum;
import static com.greghaskins.spectrum.dsl.specification.Specification.beforeEach;
import static com.greghaskins.spectrum.dsl.specification.Specification.describe;
import static com.greghaskins.spectrum.dsl.specification.Specification.it;
import static com.google.common.truth.Truth.assertThat;
import static com.google.common.truth.Truth.assertWithMessage;
import org.junit.runner.RunWith;
import java.sql.Date;
import java.util.Arrays;
import java.util.List;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.mockito.Mock;
import org.mockito.MockitoAnnotations;


@RunWith(Spectrum.class)
public class SpectrumMainPresenterTest {
    private static final List<Book> DUMMY_BOOKS = Arrays.asList(
            new Book("How to be awesome", "Jane Smith", Date.valueOf("2016-05-16")),
            new Book("My life as an awesome girl", "Jane Smith", Date.valueOf("2016-07-27")),
            new Book("I think my teacher is cool", "John Doe", Date.valueOf("2010-01-01"))
    );
    private Library library = new Library();
    @Mock
    private MainView mainView;

    @Mock
    private RandomBookGenerator bookGenerator;

    private MainPresenter presenter;

    {

    describe("Book Search", () -> {

        beforeEach(() -> {
            MockitoAnnotations.initMocks(this);
            when(bookGenerator.generate(anyInt())).thenReturn(DUMMY_BOOKS);
            presenter = new MainPresenter(mainView, library, bookGenerator);
        });

        describe(" List all books", () -> {
            describe(" When the customer wants to know all books in the library", () -> {
                describe("Then 3 books should be found", () -> {
                    it("And the number of books found is 3", () -> {
                        presenter.fetchBooks();
                        verify(mainView).showBooks(DUMMY_BOOKS);
                        assertThat( library.getAllBooks().size()).isEqualTo(3);
                    });
                    it("And Book 1 should have the title \"How to be awesome\"", () -> {
                        assertThat(library.getAllBooks().get(0).getTitle()).isEqualTo("How to be awesome");
                    });
                    it("And Book 2 should have the title \"My life as an awesome girl\"", () -> {
                        assertThat(library.getAllBooks().get(1).getTitle()).isEqualTo("My life as an awesome girl");
                    });
                    it("And Book 3 should have the title \"I think my teacher is cool\"", () -> {
                        assertThat(library.getAllBooks().get(2).getTitle()).isEqualTo("I think my teacher is cool");
                    });
                });
            });

            describe("Search books by year", () -> {
                describe("When the customer searches for books published in year 2016", () -> {
                    describe("Then 2 books should be found", () -> {
                        it("And the number of books found is 2", () -> {
                            assertThat(library.search("2016").size()).isEqualTo(2);
                        });
                        it("And Book 1 should have the title \"How to be awesome\"", () -> {
                            assertThat(library.getAllBooks().get(0).getTitle()).isEqualTo("How to be awesome");
                        });
                        it("And Book 2 should have the title \"My life as an awesome girl\"", () -> {
                            assertThat(library.getAllBooks().get(1).getTitle()).isEqualTo("My life as an awesome girl");
                        });
                    });
                });
            });

            describe("Search books by author", () -> {
                describe("When the customer searches for books by author: ", () -> {
                    describe("Then 2 books should be found", () -> {
                        String[] arrayRefVar = {"Jane", "Smith", "Jane Smith"};
                        for (String author : arrayRefVar) {
                            it("And the number of books found is 2 with author: " + author, () -> {
                                assertThat(library.search(author).size()).isEqualTo(2);
                            });
                        }
                        it("And Book 1 should have the title \"How to be awesome\"", () -> {
                            assertThat(library.getAllBooks().get(0).getTitle()).isEqualTo("How to be awesome");
                        });

                        it("And Book 2 should have the title \"My life as an awesome girl\"", () -> {
                            assertThat(library.getAllBooks().get(1).getTitle()).isEqualTo("My life as an awesome girl");
                        });
                    });
                });
            });
        });
    });
}}
