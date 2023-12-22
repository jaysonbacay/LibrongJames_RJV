package com.jaysonbacay.lastna.ui.library;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.jaysonbacay.lastna.R;
import com.jaysonbacay.lastna.data.BookModel;
import com.jaysonbacay.lastna.data.DatabaseHelper;
import com.jaysonbacay.lastna.data.LibraryModel;

import java.util.List;

public class LibraryAdapter extends RecyclerView.Adapter<LibraryAdapter.ViewHolder> {

    private List<LibraryModel> localDataSet;
    private Context context;
    private OnDeleteClickListener onDeleteClickListener;

    public LibraryAdapter(List<LibraryModel> dataSet, Context context) {
        localDataSet = dataSet;
        this.context = context;
    }

    public interface OnDeleteClickListener {
        void onDeleteClick(int position);
    }

    public void setOnDeleteClickListener(OnDeleteClickListener listener) {
        this.onDeleteClickListener = listener;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvBookName;
        private final TextView tvBookAuthor;
        private final TextView tvBookGenre;
        private final TextView tvPublishDate;
        private final ImageView tvImage;
        private final ImageButton btDelete;
        private final CardView cardView;


        public ViewHolder(View view, OnDeleteClickListener onDeleteClickListener) {
            super(view);
            tvBookName= view.findViewById(R.id.tvBookName);
            tvBookGenre = view.findViewById(R.id.tvBookGenre);
            tvBookAuthor = view.findViewById(R.id.tvBookAuthor);
            btDelete= view.findViewById(R.id.btDelete);
            tvImage = view.findViewById(R.id.tvImage);
            cardView= view.findViewById(R.id.cardView);
            tvPublishDate = view.findViewById(R.id.tvBookPublishDate);

            btDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        if (onDeleteClickListener != null) {
                            onDeleteClickListener.onDeleteClick(position);
                        }
                    }
                }
            });
        }

        public TextView getTvBookName() {
            return tvBookName;
        }

        public TextView getTvBookAuthor() {
            return tvBookAuthor;
        }
        public TextView getTvBookGenre() {
            return tvBookGenre;
        }

        public TextView getTvPublishDate() {
            return tvPublishDate;
        }

        public ImageView getTvImage() {
            return tvImage;
        }

        public CardView getCardView() {
            return cardView;
        }

        public ImageView getBtDelete() {
            return btDelete;
        }

    }

    @Override
    public LibraryAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.library_cardview, viewGroup, false);
        return new ViewHolder(view, onDeleteClickListener);
    }

    @Override
    public void onBindViewHolder(LibraryAdapter.ViewHolder viewHolder, final int position) {
        viewHolder.getTvBookName().setText(localDataSet.get(position).libBookName);
        viewHolder.getTvBookGenre().setText(localDataSet.get(position).libBookGenre);
        viewHolder.getTvBookAuthor().setText(localDataSet.get(position).libBookAuthor);
        viewHolder.getTvPublishDate().setText(localDataSet.get(position).libBookPublishDate);

        // Update this part to set the image using resource ID directly
        int imgResource = getDrawableResourceId(localDataSet.get(position).libImage);
        viewHolder.getTvImage().setImageResource(imgResource);
    }
    private int getDrawableResourceId(String imageName) {
        return context.getResources().getIdentifier(imageName, "drawable", context.getPackageName());
    }

    @Override
    public int getItemCount() {
        return localDataSet.size();
    }


    public List<LibraryModel> getLocalDataSet() {
        return localDataSet;
    }

    public void deleteFavorite(int position) {
        localDataSet.remove(position);
        notifyItemRemoved(position);
    }

    public void onDeleteClick(int position) {
        // Get the LibraryModel at the clicked position
        LibraryModel libraryModel = localDataSet.get(position);

        // Assuming you have a DatabaseHelper class, use it to delete the item from the database
        DatabaseHelper databaseHelper = new DatabaseHelper(context);
        databaseHelper. deleteBookFromLibrary(libraryModel.getBookName());

        // Remove the item from the localDataSet and notify the adapter
        localDataSet.remove(position);
        notifyItemRemoved(position);
    }
}