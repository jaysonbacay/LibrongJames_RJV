package com.jaysonbacay.lastna.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jaysonbacay.lastna.AddToLibrary;
import com.jaysonbacay.lastna.R;
import com.jaysonbacay.lastna.data.BookModel;
import com.jaysonbacay.lastna.data.DatabaseHelper;
import com.jaysonbacay.lastna.databinding.FragmentHomeBinding;
import com.jaysonbacay.lastna.ui.library.LibraryFragment;

import java.util.List;

public class HomeFragment extends Fragment {
    private FragmentHomeBinding binding;
    private CustomAdapter customAdapter;
    private DatabaseHelper db;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final RecyclerView recyclerView = root.findViewById(R.id.recyclerView);
        db = new DatabaseHelper(root.getContext());

        List<BookModel> data = db.getAllBooks();
        customAdapter = new CustomAdapter(data,
                new CustomAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(BookModel item) {
                        // Handle item click (e.g., start BookingActivity)
                        Intent intent = new Intent(getActivity(),AddToLibrary.class);
                        intent.putExtra("bookName", item.bookName);
                        intent.putExtra("bookAuthor", item.bookAuthor);
                        intent.putExtra("image", item.image);
                        intent.putExtra("bookPublishDate", item.bookPublishDate);
                        intent.putExtra("synopsis", item.synopsis);
                        startActivity(intent);
                    }           },     root.getContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(root.getContext()));
        recyclerView.setAdapter(customAdapter);

        EditText searchBar = root.findViewById(R.id.searchBar);
        searchBar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                List<BookModel> filteredData = db.  searchBooks(charSequence.toString());
                customAdapter.setData(filteredData);
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
