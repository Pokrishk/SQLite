package com.example.hh;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class BookDetailActivity extends AppCompatActivity {
    private EditText editTextName, editTextAuthor;
    private Button updateButton, deleteButton;
    private DataBaseHelper dbHelper;
    private int bookId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_detail);

        editTextName = findViewById(R.id.editTextName);
        editTextAuthor = findViewById(R.id.editTextAuthor);
        updateButton = findViewById(R.id.update);
        deleteButton = findViewById(R.id.delete);
        dbHelper = new DataBaseHelper(this);

        Intent intent = getIntent();
        bookId = intent.getIntExtra("BOOK_ID", -1);
        if (bookId == -1) {
            Toast.makeText(this, "Ошибка загрузки книги", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            loadBookDetails(bookId);
        }

        updateButton.setOnClickListener(v -> updateBookDetails());
        deleteButton.setOnClickListener(v -> deleteBook());
    }

    private void loadBookDetails(int bookId) {
        Cursor cursor = dbHelper.getAllBooks();
        while (cursor.moveToNext()) {
            if (cursor.getInt(cursor.getColumnIndexOrThrow(DataBaseHelper.COLUMN_ID)) == bookId) {
                editTextName.setText(cursor.getString(cursor.getColumnIndexOrThrow(DataBaseHelper.COLUMN_NAME)));
                editTextAuthor.setText(cursor.getString(cursor.getColumnIndexOrThrow(DataBaseHelper.COLUMN_AUTHOR)));
                break;
            }
        }
        cursor.close();
    }

    private void updateBookDetails() {
        String newName = editTextName.getText().toString().trim();
        String newAuthor = editTextAuthor.getText().toString().trim();
        if (newName.isEmpty() || newAuthor.isEmpty()) {
            Toast.makeText(this, "Заполните все поля", Toast.LENGTH_SHORT).show();
            return;
        }
        ContentValues values = new ContentValues();
        values.put(DataBaseHelper.COLUMN_NAME, newName);
        values.put(DataBaseHelper.COLUMN_AUTHOR, newAuthor);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.update(DataBaseHelper.TABLE_NAME, values, DataBaseHelper.COLUMN_ID + " = ?", new String[]{String.valueOf(bookId)});
        db.close();
        Toast.makeText(this, "Книга обновлена", Toast.LENGTH_SHORT).show();
        finish();
    }

    private void deleteBook() {
        dbHelper.deleteBookById(bookId);
        Toast.makeText(this, "Книга удалена", Toast.LENGTH_SHORT).show();
        finish();
    }
}
