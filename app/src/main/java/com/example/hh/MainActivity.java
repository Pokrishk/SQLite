package com.example.hh;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import io.paperdb.Paper;

public class MainActivity extends AppCompatActivity {
    private ArrayList<book> bookArrayList;
    private RecyclerViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Paper.init(this);

        bookArrayList = Paper.book().read("books", new ArrayList<>());

        RecyclerView recyclerView = findViewById(R.id.list_book);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new RecyclerViewAdapter(this, bookArrayList, bookId -> {
            Intent intent = new Intent(MainActivity.this, BookDetailActivity.class);
            intent.putExtra("BOOK_ID", bookId);
            startActivity(intent);
        });
        recyclerView.setAdapter(adapter);

        Button fab = findViewById(R.id.add_book);
        fab.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, AddBookActivity.class)));
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadBooks();
    }

    @SuppressLint("NotifyDataSetChanged")
    private void loadBooks() {
        bookArrayList.clear();
        bookArrayList.addAll(Paper.book().read("books", new ArrayList<>()));
        adapter.notifyDataSetChanged();
    }
}
