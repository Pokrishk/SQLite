package com.example.hh;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    private Context context;
    private ArrayList<book> bookArrayList;
    private OnBookClickListener listener;

    public RecyclerViewAdapter(Context context, ArrayList<book> bookArrayList, OnBookClickListener listener){
        this.bookArrayList=bookArrayList;
        this.context=context;
        this.listener = listener;
    }
    public interface OnBookClickListener {
        void onBookClick(int bookId);
    }

    @NonNull
    @Override
    public RecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.book_cart,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapter.ViewHolder holder, int position) {
        book book = bookArrayList.get(position);
        holder.bookname.setText(book.getBook_Name());
        holder.bookauthor.setText(book.getBook_Author());
        holder.itemView.setOnClickListener(v -> listener.onBookClick(book.getID_Book()));
    }

    @Override
    public int getItemCount() {
        return bookArrayList.size();
    }

    public static class ViewHolder extends  RecyclerView.ViewHolder {
        TextView bookname;
        TextView bookauthor;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            bookname=itemView.findViewById(R.id.b_name);
            bookauthor=itemView.findViewById(R.id.b_author);
        }
    }
}
