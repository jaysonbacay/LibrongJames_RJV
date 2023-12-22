package com.jaysonbacay.lastna.ui.home;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.jaysonbacay.lastna.AddToLibrary;
import com.jaysonbacay.lastna.R;
import com.jaysonbacay.lastna.data.BookModel;
import com.jaysonbacay.lastna.ui.gallery.GalleryFragment;

import java.util.List;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder> {

    private List<BookModel> localDataSet;
    private List<BookModel> filteredData;
    private Context context;
    private final OnItemClickListener localListener;
    public interface OnItemClickListener {
        void onItemClick(BookModel item);
    }
    public static class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView bookName;
        private final TextView bookAuthor;
        private final TextView bookGenre;
        private final TextView bookPublishDate;
        private final ImageView image;
        private final CardView cardView;
        private final Button btnView;
        private final Button addToLibraryButton;

        public ViewHolder(View view){
            super(view);

            bookName = view.findViewById(R.id.bookName);
            bookAuthor = view.findViewById(R.id.bookAuthor);
            bookGenre = view.findViewById(R.id.bookGenre);
            bookPublishDate = view.findViewById(R.id.bookPublishDate);
            cardView = view.findViewById(R.id.cardView);
            image = view.findViewById(R.id.image);
            btnView = view.findViewById(R.id.btnView);
            addToLibraryButton = view.findViewById(R.id.addToLibraryButton);
        }
        public TextView getBookName() {
            return bookName;
        }
        public TextView getBookAuthor() {
            return bookAuthor;
        }
        public TextView getBookGenre() {
            return bookGenre;
        }
        public TextView getBookPublishDate() { return bookPublishDate; }
        public ImageView getImage() {
            return image;
        }
        public CardView getCardView() {
            return cardView;
        }
        public Button getBtnView() { return btnView; }
        public Button getAddToLibraryButton() { return addToLibraryButton; }

    }
    public void setData(List<BookModel> data) {
        localDataSet.clear();
        localDataSet.addAll(data);
        notifyDataSetChanged();
    }
    public CustomAdapter(List<BookModel> dataSet, OnItemClickListener listener, Context context) {

        localDataSet = dataSet;
        localListener = listener;
        this.context = context;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.text_row_item, viewGroup, false);

        return new ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        viewHolder.getBookName().setText(localDataSet.get(position).bookName);
        int img = context.getResources().getIdentifier(localDataSet.get(position).image, "drawable", "com.jaysonbacay.lastna");
        viewHolder.getImage().setImageResource(img);
        viewHolder.getBookAuthor().setText(localDataSet.get(position).bookAuthor);
        viewHolder.getBookGenre().setText(localDataSet.get(position).bookGenre);
        viewHolder.getBookPublishDate().setText(localDataSet.get(position).bookPublishDate);
       viewHolder.getBtnView().setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               localListener.onItemClick(localDataSet.get(position));
           }
       });
    }
        @Override
    public int getItemCount() {
        return localDataSet.size();
    }
}