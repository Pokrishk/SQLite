package com.example.hh;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

import io.paperdb.Paper;

public class AddBookActivity extends AppCompatActivity {
    private EditText editTextName, editTextAuthor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_book);

        Paper.init(this);

        editTextName = findViewById(R.id.editTextName);
        editTextAuthor = findViewById(R.id.editTextAuthor);
        Button addButton = findViewById(R.id.add);

        addButton.setOnClickListener(v -> addBookToDatabase());
    }

    private void addBookToDatabase() {
        String bookName = editTextName.getText().toString().trim();
        String bookAuthor = editTextAuthor.getText().toString().trim();

        if (bookName.isEmpty() || bookAuthor.isEmpty()) {
            Toast.makeText(this, "Заполните все поля", Toast.LENGTH_SHORT).show();
            return;
        }

        ArrayList<book> books = Paper.book().read("books", new ArrayList<>());
        int newId = books.size() > 0 ? books.get(books.size() - 1).getID_Book() + 1 : 1;
        books.add(new book(newId, bookName, bookAuthor));

        Paper.book().write("books", books);

        Toast.makeText(this, "Книга добавлена", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(AddBookActivity.this, MainActivity.class));
        finish();
    }
}
