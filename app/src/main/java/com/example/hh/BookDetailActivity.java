package com.example.hh;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

import io.paperdb.Paper;

public class BookDetailActivity extends AppCompatActivity {
    private EditText editTextName, editTextAuthor;
    private int bookId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_detail);

        Paper.init(this);

        editTextName = findViewById(R.id.editTextName);
        editTextAuthor = findViewById(R.id.editTextAuthor);
        Button updateButton = findViewById(R.id.update);
        Button deleteButton = findViewById(R.id.delete);

        bookId = getIntent().getIntExtra("BOOK_ID", -1);
        System.out.println("Полученный bookId: " + bookId);

        if (bookId == -1) {
            Toast.makeText(this, "Ошибка загрузки книги", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            loadBookDetails();
        }

        updateButton.setOnClickListener(v -> updateBookDetails());
        deleteButton.setOnClickListener(v -> deleteBook());
    }

    private void loadBookDetails() {
        ArrayList<book> books = Paper.book().read("books", new ArrayList<>());
        for (book b : books) {
            if (b.getID_Book() == bookId) {
                editTextName.setText(b.getBook_Name());
                editTextAuthor.setText(b.getBook_Author());
                break;
            }
        }
    }

    private void updateBookDetails() {
        String newName = editTextName.getText().toString().trim();
        String newAuthor = editTextAuthor.getText().toString().trim();

        if (newName.isEmpty() || newAuthor.isEmpty()) {
            Toast.makeText(this, "Заполните все поля", Toast.LENGTH_SHORT).show();
            return;
        }
        ArrayList<book> books = Paper.book().read("books", new ArrayList<>());
        boolean updated = false;
        for (book b : books) {
            if (b.getID_Book() == bookId) {
                b.setBook_Name(newName);
                b.setBook_Author(newAuthor);
                updated = true;
                break;
            }
        }
        if (updated) {
            Paper.book().write("books", books);

            for (book b : books) {
                System.out.println("Книга: " + b.getID_Book() + " - " + b.getBook_Name() + " - " + b.getBook_Author());
            }
            Toast.makeText(this, "Книга обновлена", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Книга не найдена", Toast.LENGTH_SHORT).show();
        }
        finish();
    }


    private void deleteBook() {
        ArrayList<book> books = Paper.book().read("books", new ArrayList<>());
        books.removeIf(b -> b.getID_Book() == bookId);
        Paper.book().write("books", books);

        Toast.makeText(this, "Книга удалена", Toast.LENGTH_SHORT).show();
        finish();
    }
}
