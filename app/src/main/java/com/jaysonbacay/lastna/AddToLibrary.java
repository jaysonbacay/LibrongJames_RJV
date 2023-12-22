package com.jaysonbacay.lastna;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.jaysonbacay.lastna.data.BookModel;
import com.jaysonbacay.lastna.data.DatabaseHelper;
import com.jaysonbacay.lastna.ui.library.LibraryFragment;

public class AddToLibrary extends AppCompatActivity {
    private SharedPreferences sharedPreferences;
    private static final String STATUS_KEY = "added_status";
    private TextView tvbookName;
    private TextView tvbookAuthor;
    private TextView tvbookGenre;
    private TextView tvbookPublishDate;
    private TextView tvsynopsis;
    private ImageView tvimage;
    private boolean isAdded = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_to_library);

        Intent intent = getIntent();
        if (intent != null) {
            String bookName = intent.getStringExtra("bookName");
            String bookAuthor = intent.getStringExtra("bookAuthor");
            String image = intent.getStringExtra("image");
            String bookGenre = intent.getStringExtra("bookGenre");
            String bookPublishDate = intent.getStringExtra("bookPublishDate");
            String synopsis = intent.getStringExtra("synopsis");

            tvimage = findViewById(R.id.tvimage);
            tvbookName = findViewById(R.id.tvbookName);
            tvbookAuthor = findViewById(R.id.tvbookAuthor);
            tvbookGenre = findViewById(R.id.tvbookGenre);
            tvbookPublishDate = findViewById(R.id.tvbookPublishDate);
            tvsynopsis = findViewById(R.id.tvSynopsis);

            int imageResourceId = getResources().getIdentifier(image, "drawable", getPackageName());
            if (imageResourceId != 0) {
                tvimage.setImageResource(imageResourceId);
            } else {
                tvimage.setImageResource(R.drawable.remember);
            }
            tvbookName.setText(bookName);
            tvbookAuthor.setText(bookAuthor);
            tvbookGenre.setText(bookGenre);
            tvbookPublishDate.setText(bookPublishDate);
            tvsynopsis.setText(synopsis);

            Button btnAddToLibrary = findViewById(R.id.btnAddToLibraryButton);
            btnAddToLibrary.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (!isAdded()) { // Check if not added
                        loadLibraryFragment();
                        // Set text to "Added" and update status
                        btnAddToLibrary.setText("Added");
                        AddStatus(true);
                    }
                }
            });
        }
    }

    private void loadLibraryFragment() {
        Intent intent = getIntent();
        if (intent != null) {
            String bookName = intent.getStringExtra("bookName");
            String bookAuthor = intent.getStringExtra("bookAuthor");
            String image = intent.getStringExtra("image");
            String bookGenre = intent.getStringExtra("bookGenre");
            String bookPublishDate = intent.getStringExtra("bookPublishDate");
            String synopsis = intent.getStringExtra("synopsis");

            // Create BookModel instance using the regular constructor
            BookModel book = new BookModel(0, bookName, bookAuthor, bookGenre,bookPublishDate, synopsis, image);

            DatabaseHelper db = new DatabaseHelper(this);
            db.addBookFromHome(book);
        }

    }
    public boolean isAdded(){
        return isAdded;
    }

    public void AddStatus(boolean isAdded){
        this.isAdded = isAdded;
    }
}