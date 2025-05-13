package com.example.codedex.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.codedex.R;

public class DescriptionFragment extends Fragment {

    private static final String ARG_TEXT = "text";

    public static DescriptionFragment newInstance(String text) {
        DescriptionFragment fragment = new DescriptionFragment();
        Bundle args = new Bundle();
        args.putString(ARG_TEXT, text);
        fragment.setArguments(args);        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_description, container, false);
        TextView textView = view.findViewById(R.id.textDescription);

        if (getArguments() != null) {
            textView.setText(getArguments().getString(ARG_TEXT, ""));
        }

        return view;
    }
}