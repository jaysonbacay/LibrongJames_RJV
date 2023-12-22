
package com.jaysonbacay.lastna.ui.library;

import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jaysonbacay.lastna.R;
import com.jaysonbacay.lastna.data.BookModel;
import com.jaysonbacay.lastna.data.DatabaseHelper;
import com.jaysonbacay.lastna.data.LibraryModel;
import com.jaysonbacay.lastna.databinding.FragmentLibraryBinding;

import java.util.List;

public class LibraryFragment extends Fragment {
    private FragmentLibraryBinding binding;
    private LibraryAdapter customAdapter;
    private RecyclerView rvLibrary;
    Button btDelete;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentLibraryBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        rvLibrary = root.findViewById(R.id.rvLibrary);

        DatabaseHelper db = new DatabaseHelper(requireContext());


        List<LibraryModel> bookList = db.getAllAddedBooks();
        customAdapter = new LibraryAdapter(bookList, requireContext());

        // Set the onDeleteClickListener for the adapter
        customAdapter.setOnDeleteClickListener(new LibraryAdapter.OnDeleteClickListener() {
            @Override
            public void onDeleteClick(int position) {
                // Get the LibraryModel at the clicked position
                LibraryModel libraryModel = customAdapter.getLocalDataSet().get(position);

                // Assuming you have a DatabaseHelper class, use it to delete the item from the database
                DatabaseHelper databaseHelper = new DatabaseHelper(requireContext());
                databaseHelper.deleteBookFromLibrary(libraryModel.libBookName);

                // Remove the item from the localDataSet and notify the adapter
                customAdapter.deleteFavorite(position);
                Toast.makeText(requireContext(), "Item deleted", Toast.LENGTH_SHORT).show();
            }
        });

        rvLibrary.setLayoutManager(new LinearLayoutManager(requireContext()));
        rvLibrary.setAdapter(customAdapter);

        return root;
    }
}