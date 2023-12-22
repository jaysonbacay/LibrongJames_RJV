package com.jaysonbacay.lastna.ui.gallery;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.jaysonbacay.lastna.AddToLibrary;
import com.jaysonbacay.lastna.R;
import com.jaysonbacay.lastna.databinding.FragmentGalleryBinding;

public class GalleryFragment extends Fragment {

    public GalleryFragment() {

    }
@Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
    return inflater.inflate(R.layout.fragment_gallery, container, false);
}
}