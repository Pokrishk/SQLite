package com.example.hh;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private DataBaseHelper dbHelper;
    private ArrayList<book> bookArrayList;
    private RecyclerViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dbHelper = new DataBaseHelper(this);
        bookArrayList = new ArrayList<>();
        RecyclerView recyclerView = findViewById(R.id.list_book);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new RecyclerViewAdapter(this, bookArrayList, bookId -> {
            Intent intent = new Intent(MainActivity.this, BookDetailActivity.class);
            intent.putExtra("BOOK_ID", bookId);
            startActivity(intent);
        });
        recyclerView.setAdapter(adapter);
        Button fab = findViewById(R.id.add_book);
        fab.setOnClickListener(v->startActivity(new Intent(MainActivity.this, AddBookActivity.class)));
        loadBooks();
    }
    private void loadBooks(){
        bookArrayList.clear();
        Cursor cursor = dbHelper.getAllBooks();
        if(cursor.moveToFirst()){
            do{
                int id = cursor.getInt(cursor.getColumnIndexOrThrow(DataBaseHelper.COLUMN_ID));
                String name = cursor.getString(cursor.getColumnIndexOrThrow(DataBaseHelper.COLUMN_NAME));
                String author = cursor.getString(cursor.getColumnIndexOrThrow(DataBaseHelper.COLUMN_AUTHOR));
                bookArrayList.add(new book(id, author, name));
            }while (cursor.moveToNext());
        }
        cursor.close();
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadBooks();
    }
}